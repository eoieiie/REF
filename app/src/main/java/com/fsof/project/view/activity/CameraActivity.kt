package com.fsof.project.view.activity

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
import com.fsof.project.controller.Classifier
import com.fsof.project.databinding.ActivityCameraBinding
import com.fsof.project.utils.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.database.sqlite.SQLiteConstraintException
import android.util.Log

import com.fsof.project.R
import com.fsof.project.controller.NutrientController
import com.fsof.project.controller.client.NutrientClient
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.input.Input
import com.fsof.project.model.room.IngredientDatabase

class CameraActivity : AppCompatActivity() {
  
    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) } // private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater, null, false) }
    private lateinit var classifier: Classifier
    private var imageUri: Uri? = null
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess: Boolean ->
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
    private val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())

    var name: String = ""
    private var weight: String = "1개"
    private var isFreezed: Boolean = false
    private var up: String = dateFormat.format(Calendar.getInstance().time)
    private var expiration: String = dateFormat.format(Calendar.getInstance().time)

    private lateinit var nutrientController: NutrientController

    private lateinit var ingredientsDB: IngredientDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initClassifier()
        binding.run {
            btnSave.setOnClickListener {
                saveDataAndReturn()
            }

            // camera launch
//            btnTakePhoto.setOnClickListener {
                getTmpFileUri().let { uri ->
                    imageUri = uri
                    cameraResult.launch(uri)
                }
//            }

            // Weight

            // isFreezed
            radioGroup.setOnCheckedChangeListener { _, radioButtonID ->
                when (radioButtonID) {
                    R.id.rightRadioButton -> {
                        isFreezed = true
                    }
                    R.id.rightRadioButton -> {
                        isFreezed = false
                    }
                }
//                Log.d("Radio", "$isFreezed")
            }

            // up
            editRegistrationDate.text = up

            // expiration
            editExpirationDate.text = expiration
            editExpirationDate.setOnClickListener {
                showDatePickerDialog()
            }

            nutrientController = NutrientController(NutrientClient.nutrientService)
        }
    }

    override fun onDestroy() {
        classifier.finish()
        super.onDestroy()
    }

    private fun initClassifier() {
        classifier = Classifier(this, Classifier.IMAGENET_CLASSIFY_MODEL)
        try {
            classifier.init()
        } catch (exception: IOException) {
            Toast.makeText(this, "Can not init Classifier!!", Toast.LENGTH_SHORT).show()
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
//                Log.d("calendar", dateFormat.format(calendar.time))
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
        Log.d("API", "${Input(name = name, weight = weight, isFreezed = false, up_date = up, expiration_date = expiration)}")

        // API Networking & Data Storing
        createNutrients(Input(name = name, weight = weight, isFreezed = false, up_date = up, expiration_date = expiration))

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun createNutrients(input: Input) {
        nutrientController.createNutrients(input) { nutrients, throwable ->
            runOnUiThread {
                if (throwable != null) {
                    Log.d("API", "Error: ${throwable.message}")
                    Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                } else if (nutrients != null) {
                    insertData(nutrients)
                } else {
                    Log.d("API", "Unknown error occurred")
                    Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show()
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
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, "중복된 식품을 등록하였습니다. 다른 식품으로 다시 등록해주세요.", Toast.LENGTH_SHORT).show()
            Log.e("DB", "UNIQUE constraint failed: ${e.message}")
        }
    }
}