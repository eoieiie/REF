package com.fsof.project.view.activity

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
//import android.util.Log

class CameraActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater, null, false) }
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
                val resultStr =
                    String.format(Locale.ENGLISH, "%s", output.first)
//                Log.d("prob", String.format(Locale.ENGLISH, "%.2f%%",output.second * 100))
                binding.run {
                    textResult.text = resultStr
                    imagePhoto.setImageBitmap(bitmap)
                }
            }
        }
    private val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initClassifier()
        binding.run {
            // camera launch
//            btnTakePhoto.setOnClickListener {
                getTmpFileUri().let { uri ->
                    imageUri = uri
                    cameraResult.launch(uri)
                }
//            }

            // date picker
            editRegistrationDate.text = dateFormat.format(Calendar.getInstance().time)
            editExpirationDate.text = dateFormat.format(Calendar.getInstance().time)
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
                val selectedDate = dateFormat.format(calendar.time)
                Log.d("calendar", selectedDate)
                binding.editExpirationDate.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}