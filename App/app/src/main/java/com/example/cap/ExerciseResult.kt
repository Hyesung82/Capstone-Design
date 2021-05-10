package com.example.cap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dinuscxj.progressbar.CircleProgressBar
import com.dinuscxj.progressbar.CircleProgressBar.ProgressFormatter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ExerciseResult : AppCompatActivity() ,ProgressFormatter {

    val feedbackImages = arrayOf(R.drawable.jb_result1, R.drawable.jb_result2, R.drawable.fake_result)
    val feedbackText = arrayOf("팔을 더 굽히세요", "팔을 더 굽히세요\n팔뚝에 힘을 더 주세요", "자세가 완벽해요\n팔뚝에 힘을 더 주세요")


    inner class MyGalleryAdapter(con: Context): BaseAdapter() {
        var context: Context = con

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val imageView: ImageView = ImageView(context)
            imageView.layoutParams = Gallery.LayoutParams(200, 300)
//            imageView.scaleType(ImageView.ScaleType.FIT_XY)
            imageView.setPadding(5, 5, 5, 5)
            imageView.setImageResource(feedbackImages[p0])
            return imageView
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return feedbackImages.size
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_result)

        var circleProgressBar: CircleProgressBar? = null
        circleProgressBar = findViewById(R.id.cpb_circlebar)
        circleProgressBar.setProgress(70)


        val button : ImageButton = findViewById(R.id.button)
        val weight : TextView = findViewById(R.id.resultweight)
        val tvTimes : TextView = findViewById(R.id.tvTimes)
        val tvSet : TextView = findViewById(R.id.tvSet)

        val ivFeedback: ImageView = findViewById(R.id.ivFeedback)
        ivFeedback.scaleType = ImageView.ScaleType.FIT_XY
        ivFeedback.setImageResource(feedbackImages[0])
        val tvFeedback: TextView = findViewById(R.id.tvFeedback)
        tvFeedback.text = feedbackText[0]
        val gExerResult: Gallery = findViewById(R.id.gExerResult)
        val galAdapter = MyGalleryAdapter(this)
        gExerResult.adapter = galAdapter


        val weight2 = intent.extras!!.getInt("resultweight")
        val numTimes = intent.extras!!.getInt("resultTimes")
        val numSet = intent.extras!!.getInt("resultSet")
        //if(intent.hasExtra("resultweight")){
           // weight.text = intent.getStringExtra("resultweight")
        //}
        weight.text = "${weight2}"
        tvTimes.text = "${numTimes}"
        tvSet.text = "${numSet}"

        // TODO: server 주소
//        networking("server address")

        gExerResult.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            ivFeedback.scaleType = ImageView.ScaleType.FIT_XY
            ivFeedback.setImageResource(feedbackImages[i])

            tvFeedback.text = feedbackText[i]
        }

        button.setOnClickListener {
            val nextIntent = Intent(this, ExerciseSelection::class.java)
            nextIntent.putExtra("activate", 0)
            startActivity(nextIntent)
        }
    }

    companion object {
        private const val DEFAULT_PATTERN = "%d%%"
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }


    override fun format(progress: Int, max: Int): CharSequence {
        return String.format(
            ExerciseResult.Companion.DEFAULT_PATTERN,
            (progress.toFloat() / max.toFloat() * 100).toInt()
        )
    }

    fun networking(urlString: String) {
        thread(start=true) {
            try {
                val url = URL(urlString)

                // 서버와의 연결 생성
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"

                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    // 데이터 읽기
                    val streamReader = InputStreamReader(urlConnection.inputStream)
                    val buffered = BufferedReader(streamReader)

                    val content = StringBuilder()
                    while(true) {
                        val line = buffered.readLine() ?: break
                        content.append(line)
                    }
                    // 스트림과 커넥션 해제
                    buffered.close()
                    urlConnection.disconnect()
                    runOnUiThread {
                        Log.i("ExerciseResult", "센서로부터 받은 데이터: $content")
                        val tvMuscle = findViewById<TextView>(R.id.tvMuscle)
                        tvMuscle.text = content
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
