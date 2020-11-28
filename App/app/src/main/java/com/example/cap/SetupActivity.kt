package com.example.cap
import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent as Intent1

class SetupActivity : AppCompatActivity() {
    var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    lateinit var ivPicture: ImageView

    lateinit var curFileName: String

    val mPicture = Camera.PictureCallback { data, _ ->
        val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
            Log.d("TAG", ("Error creating media file, check storage permissions"))
            return@PictureCallback
        }

        try {
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file: ${e.message}")
        }
    }

    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3)

        if (!checkPersmission()) requestPermission()

        ivPicture = findViewById(R.id.iv_picture)

        if(!checkCameraHardware(this.applicationContext)) {
            Log.d("TAG", "No CameraHardware")
        }

        val captureButton: Button = findViewById(R.id.button_capture)
        captureButton.setOnClickListener {
            // 지정해준 이름과 실제로 저장되는 이름이 가끔씩 다름(1초 차이) -> timestamp X,
            // 이름이 같더라도 사진이 저장되는데 시간이 걸려서 사진을 못 찾음 -> handler

            // get an image from the camera
            mCamera?.takePicture(null, null, mPicture)

            lateinit var uri: Uri
            handler= Handler()
            handler.postDelayed({
                uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                Log.d("mediafile uri", uri.toString())
                Log.d("current file name", curFileName)

                ivPicture.setImageURI(uri)
            },1000)



//            val path = "file:///storage/emulated/0/Pictures/MyCameraApp/IMG_20201128_132455.jpg"
//            ivPicture.setImageURI(Uri.parse(path))
        }

//        handler= Handler()
//        handler.postDelayed({
//            doSomething()
//        },2000)

        // Create an instance of Camera
        mCamera = getCameraInstance()

        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
        mPreview?.also {
            val preview: FrameLayout = findViewById(R.id.camera_preview)
            preview.addView(it)
        }
    }


    private fun doSomething() {
        startActivity(Intent1(this, OKActivity::class.java))
        //Toast.makeText(this,"Hi! I am Toast Message",Toast.LENGTH_SHORT).show()
    }


    val REQUEST_IMAGE_CAPTURE = 1
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA), REQUEST_IMAGE_CAPTURE)
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^")
        }else{
            Log.d("TAG","카메라 허가 못받음 ㅠ 젠장!!")
        }
    }

    /** Check if this device has a camera */
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }


    private fun getCameraInstance(): Camera? {
        return try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera() // release the camera immediately on pause event
    }

    private fun releaseCamera() {
        mCamera?.release() // release the camera for other applications
        mCamera = null
    }


    /** Create a file Uri for saving an image or video */
    private fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    /** Create a File for saving an image or video */
    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyCameraApp"
        )
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory")
                    return null
                }
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                curFileName = "${mediaStorageDir.path}${File.separator}IMG_setup.jpg"
                File(curFileName)
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }
}

