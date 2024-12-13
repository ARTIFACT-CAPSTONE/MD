package com.example.artifact.ml.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.artifact.R
import com.example.artifact.data.utils.reduceFileImage
import com.example.artifact.data.utils.uriToFile
import com.example.artifact.databinding.ActivityUploadBinding
import com.example.artifact.di.Injection
import com.example.artifact.data.utils.getImageUri
import com.example.artifact.view.ui.MainActivity
import com.example.artifact.view.ui.history.HistoryViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val uploadViewModel: UploadViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    private var currentImageUri: Uri? = null

    private val CAMERA_REQUEST_CODE = 100

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUploadGallery.setOnClickListener { startGallery() }
        binding.btnUploadCamera.setOnClickListener { checkCameraPermissionAndStartCamera() }
        binding.btnUploadUpload.setOnClickListener { analyzeImage() }

        uploadViewModel.resultAddStory.observe(this) {
            if (it.analysisResults!!.category == "AI Detected" || it.analysisResults.category == "Human Detected") {
                binding.tvResultSimilarity.text = it.analysisResults.category.toString()
                binding.ivResultImageResult.setImageURI(myuri)
                binding.tvResultFilename.text = binding.edUploadInputname.text.toString()
                binding.layoutResult.visibility = View.VISIBLE
                binding.layoutUpload.visibility = View.GONE
                uploadImage()
            }
            else {
                showToast(it.message.toString())
            }
        }

        binding.backBase.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        uploadViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
    }

    private fun checkCameraPermissionAndStartCamera() {
        val permission = Manifest.permission.CAMERA

        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), CAMERA_REQUEST_CODE)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivResultImage.setImageURI(it)
        }
    }

    var myuri: Uri? = null
    var multipartBody: MultipartBody.Part? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun analyzeImage() {
        currentImageUri?.let { uri ->
            myuri = uri
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val fileName = binding.edUploadInputname.text.toString()

            if (fileName.isEmpty()) {
                showToast(getString(R.string.empty_description_warning))
                return
            }

            showLoading(true)

            val requestBody = fileName.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            multipartBody = MultipartBody.Part.createFormData(
                "image", imageFile.name, requestImageFile
            )

            uploadViewModel.analyzeImage(multipartBody!!)
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            myuri = uri
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val fileName = binding.edUploadInputname.text.toString()

            if (fileName.isEmpty()) {
                showToast(getString(R.string.empty_description_warning))
                return
            }

            showLoading(true)

            val requestBodyName = fileName.toRequestBody("text/plain".toMediaType())
            val requestBody = binding.edUploadInputname.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            multipartBody = MultipartBody.Part.createFormData(
                "image", imageFile.name, requestImageFile
            )

            uploadViewModel.uploadImage(multipartBody!!, requestBody, requestBodyName)
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarUpload.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}