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
import android.util.Log

import com.fsof.project.R
import com.fsof.project.model.nutrients.Nutrients
import com.fsof.project.model.entity.Ingredients
import com.fsof.project.model.room.IngredientsDatabase

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
                }
            }
        }
    private val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    private var isFreezed: Boolean = false
    private var up: String = dateFormat.format(Calendar.getInstance().time)
    private var expiration: String = dateFormat.format(Calendar.getInstance().time)

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

            // name

            // Weight

            // isFreezed
            radioGroup.setOnCheckedChangeListener { radioGroup, radioButtonID ->
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
        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show() // testDB()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun testDB() {
        val ingredientsDB = IngredientsDatabase.getInstance(this)

//        if (ingredientsDB != null) {

            // 유저 추가
            val apple = Ingredients(name = "사과", weight = "두봉지", isFreezed = false, upDate = "24-05-30", expirationDate = "24-06-30", nutrients = Nutrients(calories = 20, carbohydrates = 20, protein = 20, fat = 20))
            ingredientsDB.ingredientsDao().insertData(apple)

//            // 유저 수정
//            ingredientsDB.userDao().updateNameByUserId(1, "드즈")

            // 유저 출력
            val ingredientsList = ingredientsDB.ingredientsDao().selectAll()
            Log.d("DataBase", "ADD: $ingredientsList")

            // 유저 삭제
            ingredientsDB.ingredientsDao().deleteData(apple)
            val ingredientsLists = ingredientsDB.ingredientsDao().selectAll()
            Log.d("DataBase", "Delete: $ingredientsLists")

//            // 유저 선택
//            val user2 = roomDb.userDao().selectByUserId(2)
//            Log.d("DB", "User List: $user2")

//        }
    }
}