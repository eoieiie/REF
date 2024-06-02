package com.fsof.ref_client.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.fsof.ref_client.controller.camera.Classifier
import com.fsof.ref_client.databinding.ActivityCameraBinding
import com.fsof.ref_client.utils.BuildConfig
import com.fsof.ref_client.controller.NutrientController
import com.fsof.ref_client.controller.client.NutrientClient
import com.fsof.ref_client.model.entity.Ingredients
import com.fsof.ref_client.model.entity.IngredientInfo
import com.fsof.ref_client.model.datasource.IngredientDatabase
import com.fsof.ref_client.model.repository.NutrientRepository
import com.fsof.ref_client.utils.TFLite
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.activity.result.ActivityResultLauncher

import com.fsof.ref_client.R

class CameraActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater, null, false) }

    private lateinit var classifier: Classifier
    private var imageUri: Uri? = null
    private lateinit var cameraResult: ActivityResultLauncher<Uri>

    private lateinit var ingredientsDB: IngredientDatabase
    private lateinit var nutrientController: NutrientController
    private lateinit var nutrientRepository: NutrientRepository

    private val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())

    private lateinit var name: String
    private lateinit var weight: String
    private var isFreezed: Boolean = false
    private var up: String = dateFormat.format(Calendar.getInstance().time)
    private var expiration: String = dateFormat.format(Calendar.getInstance().time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        nutrientRepository = NutrientRepository(NutrientClient.nutrientService)
        nutrientController = NutrientController(nutrientRepository)
        initClassifier()
        initCameraResult()
        save()
        launchCamera()
        setFreezed()
        initInput()
    }

    override fun onDestroy() {
        classifier.finish()
        super.onDestroy()
    }

    private fun save() {
        binding.btnSave.setOnClickListener {
            name = binding.editIngredientName.text.toString()
            weight = "${binding.editStock.text}${binding.spinnerUnit.selectedItem}"

            saveDataAndReturn()
        }
    }

    private fun launchCamera() {
        getTmpFileUri().let { uri ->
            imageUri = uri
            cameraResult.launch(uri)
        }
    }

    private fun setFreezed() {
        binding.radioGroup.setOnCheckedChangeListener { _, radioButtonID ->
            when (radioButtonID) {
                R.id.rightRadioButton -> {
                    isFreezed = true
                }
                R.id.leftRadioButton -> {
                    isFreezed = false
                }
            }
        }
    }

    private fun initInput() {
        binding.editRegistrationDate.text = up
        binding.editExpirationDate.text = expiration
        binding.editExpirationDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun initClassifier() {
        classifier = Classifier(this, TFLite.IMAGENET_CLASSIFY_MODEL)
        try {
            classifier.init()
        } catch (exception: IOException) {
            Toast.makeText(this, "Can not init Classifier!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initCameraResult() {
        cameraResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess: Boolean ->
            if (isSuccess.not()) return@registerForActivityResult
            val selectedImage = imageUri ?: return@registerForActivityResult
            var bitmap: Bitmap? = null
            try {
                bitmap = if (Build.VERSION.SDK_INT >= 28) {
                    val src = ImageDecoder.createSource(contentResolver, selectedImage)
                    ImageDecoder.decodeBitmap(src)
                } else {
                    MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                }
            } catch (exception: IOException) {
                Toast.makeText(this, "Can not load image!!", Toast.LENGTH_SHORT).show()
            }
            bitmap?.let {
                val output = classifier.classify(bitmap)
                val resultStr = String.format(Locale.ENGLISH, "%s", output.first)
                binding.run {
                    imageView.setImageBitmap(bitmap)
                    editIngredientName.setText(resultStr)
                    name = resultStr
                    Log.d("API", name)
                }
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                Log.d("calendar", dateFormat.format(calendar.time))
                binding.editExpirationDate.text = dateFormat.format(calendar.time)
                expiration = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun saveDataAndReturn() {
        createNutrients(IngredientInfo(name = name, weight = weight, isFreezed = isFreezed, up_date = up, expiration_date = expiration))
    }

    private fun createNutrients(input: IngredientInfo) {
        nutrientController.createNutrients(input) { nutrients, throwable ->
            runOnUiThread {
                if (throwable != null) {
                    Log.d("API", "Error: ${throwable.message}")
                    Toast.makeText(this, "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                } else if (nutrients != null) {
                    Log.d("API", "Nutrients: $nutrients")
                    insertData(nutrients)
                } else {
                    Log.d("API", "Unknown error occurred")
                    Toast.makeText(this, "영양소 생성 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun insertData(ingredient: Ingredients) {
        ingredientsDB = IngredientDatabase.getInstance(this)
        try {
            // if (ingredientsDB != null)
            ingredientsDB.ingredientsDao().insertData(ingredient)
            Log.d("DB", "저장 완료")
            Log.d("DB", "Ingredients List: ${ingredientsDB.ingredientsDao().selectAll()}")
            Toast.makeText(this, "${ingredient.name} 추가 성공!.", Toast.LENGTH_SHORT).show()
            goToMainView()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, "냉장고 등록 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
            Log.e("DB", "UNIQUE constraint failed: ${e.message}")
        } catch (e: Exception) {
            Toast.makeText(this, "냉장고 등록 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
            Log.e("DB_ERROR", "Exception: ${e.message}")
        }
    }

    private fun goToMainView() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}