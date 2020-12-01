package com.example.cap
import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.hardware.Camera
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.material.datepicker.MaterialTextInputPicker
import java.io.*
import java.net.URL
import android.content.Intent as Intent1

class SetupActivity : AppCompatActivity() {
    var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    lateinit var ivPicture1: ImageView
    lateinit var ivPicture2: ImageView
    lateinit var ivPicture3: ImageView

    private lateinit var ivPictures: Array<ImageView>

    lateinit var curFileName: String

    var pictureNum = 0

    lateinit var mContext: Context

    val mPicture = Camera.PictureCallback { data, _ ->
        val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
            Log.d("TAG", ("Error creating media file, check storage permissions"))
            return@PictureCallback
        }


        // 사진 회전
        var storedBitmap = BitmapFactory.decodeByteArray(data, 0, data.size, null)
        var mat = Matrix()
        mat.postRotate(90F)
        storedBitmap = Bitmap.createBitmap(storedBitmap, 0, 0, storedBitmap.width, storedBitmap.height, mat, true)
        var bos = ByteArrayOutputStream()
        storedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        var bitmapData = bos.toByteArray()
        storedBitmap.recycle()


        try {
            val fos = FileOutputStream(pictureFile)
//            fos.write(data)
            fos.write(bitmapData)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file: ${e.message}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3)

        // 테스트용 스킵 버튼
        val skipButton: Button = findViewById(R.id.button_skip)
        skipButton.setOnClickListener{
            startActivity(Intent1(this, OKActivity::class.java))
        }

        mContext = this.applicationContext

        if(!checkCameraHardware(this.applicationContext)) {
            Log.d("TAG", "No CameraHardware")
        }
        if (!checkPersmission()) requestPermission()

        ivPicture1 = findViewById(R.id.iv_picture1)
        ivPicture2 = findViewById(R.id.iv_picture2)
        ivPicture3 = findViewById(R.id.iv_picture3)

        ivPictures = arrayOf(ivPicture1, ivPicture2, ivPicture3)


        val captureButton: Button = findViewById(R.id.button_capture)

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


        captureButton.setOnClickListener {
            // 지정해준 이름과 실제로 저장되는 이름이 가끔씩 다름(1초 차이) -> timestamp X,
            // 이름이 같더라도 사진이 저장되는데 시간이 걸려서 사진을 못 찾음 -> handler

            for (index in 0..2) {
                shootAndStore(index)
            }

            // AsyncTask에서는 view를 변경할 수 없음 ㅠㅠ -> 사용 X
//            TaskTakePicture1().execute()
        }
    }


    private val REQUEST_IMAGE_CAPTURE = 1
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
            Log.d("TAG", "Permission: " + permissions[0] + " was " + grantResults[0])
            Log.d("권한 요청 결과", "권한 얻음")
        }else{
            Log.d("TAG","Permission: " + permissions[0] + "was " + grantResults[0])
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
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                curFileName = "${mediaStorageDir.path}${File.separator}IMG_setup$pictureNum.jpg"
//                curFileName = Environment.getExternalStorageDirectory().getPath() + "/Pictures/capstone/IMG_setup$pictureNum.jpg"

                File(curFileName)
            }
//            MEDIA_TYPE_VIDEO -> {
//                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
//            }
            else -> null
        }
    }


    private fun shootAndStore(num: Int) {
        var handler1 = Handler()
        var handler2 = Handler()
        val millis = num.toLong()

        handler1.postDelayed({
            pictureNum++
            // 3초 후 다시 찰칵
            Toast.makeText(mContext,"setup${num+1}: 지금 찰칵!", Toast.LENGTH_SHORT).show()

            // get an image from the camera
            mCamera?.takePicture(null, null, mPicture)

        },3000 + millis * 3000)

        handler2.postDelayed({
            // 태블릿에서만 파일이 저장됨ㅠㅠ
            // 사진 촬영 후 프리뷰가 멈추는 이유는 아래 작업들 때문!
            // 하지만 아래 작업들은 프리뷰 호출과 관련이 없음
            // -> getOutputMediaFileUri를 확인해볼 것
//            var uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
//            Log.d("mediafile uri", uri.toString())

            // curFileName has not been initialized
            Log.d("current file name", curFileName)
            ivPictures[num].setImageURI(curFileName.toUri())

            mPreview = null

            // Create an instance of Camera
            mCamera = getCameraInstance()

            mPreview = mCamera?.let {
                // Create our Preview view
                CameraPreview(this, it)
            }

            // Set the Preview view as the content of our activity.
            mPreview?.also {
                val preview1: FrameLayout = findViewById(R.id.camera_preview)
                preview1.addView(it)
            }
        },4000 + millis * 3000)
    }


    inner class TaskTakePicture1: AsyncTask<URL, Integer, Long>() {
        override fun doInBackground(vararg p0: URL?): Long? {
            lateinit var uri: Uri

            pictureNum++
            // 3초 후 다시 찰칵
//            Toast.makeText(mContext,"setup1: 지금 찰칵!", Toast.LENGTH_SHORT).show()

            // get an image from the camera
            mCamera?.takePicture(null, null, mPicture)

            uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
            Log.d("mediafile uri", uri.toString())
            Log.d("current file name", curFileName)

            ivPictures[0].setImageURI(uri)

            return null
        }

        override fun onPostExecute(result: Long?) {
            TaskTakePicture2().execute()
        }
    }

    inner class TaskTakePicture2: AsyncTask<URL, Integer, Long>() {
        override fun doInBackground(vararg p0: URL?): Long? {
            lateinit var uri: Uri

                pictureNum++
                // 3초 후 다시 찰칵
//                Toast.makeText(mContext,"setup2: 지금 찰칵!", Toast.LENGTH_SHORT).show()

                // get an image from the camera
                mCamera?.takePicture(null, null, mPicture)


                uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                Log.d("mediafile uri", uri.toString())
                Log.d("current file name", curFileName)

                ivPictures[1].setImageURI(uri)

            return null
        }


        override fun onPostExecute(result: Long?) {
            TaskTakePicture3().execute()
        }
    }

    inner class TaskTakePicture3: AsyncTask<URL, Integer, Long>() {
        override fun doInBackground(vararg p0: URL?): Long? {
            lateinit var uri: Uri

                pictureNum++
                // 3초 후 다시 찰칵
//                Toast.makeText(mContext,"setup3: 지금 찰칵!", Toast.LENGTH_SHORT).show()

                // get an image from the camera
                mCamera?.takePicture(null, null, mPicture)


                uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                Log.d("mediafile uri", uri.toString())
                Log.d("current file name", curFileName)

                ivPictures[2].setImageURI(uri)

            return null
        }

        override fun onPostExecute(result: Long?) {
            startActivity(Intent1(mContext, OKActivity::class.java))
        }
    }
}

