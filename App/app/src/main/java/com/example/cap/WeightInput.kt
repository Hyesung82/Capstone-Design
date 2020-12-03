package com.example.cap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cap.conn.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import kotlin.properties.Delegates

class WeightInput : AppCompatActivity() {

    internal lateinit var retrofit: Retrofit
    internal lateinit var apiService: ApiService
    internal lateinit var comment: Call<ResponseBody>
    internal lateinit var result: String

    var intWeight by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weight_input)

        retrofit = Retrofit.Builder().baseUrl(ApiService.API_URL).build()
        apiService = retrofit.create(ApiService::class.java)
//        comment = apiService.patch_Test(1, "json", "weight")
//        comment.enqueue(object : Callback<ResponseBody> {
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                Log.e("D_Test", "2차")
//                try {
//                    Log.e("D_Test", "post submitted to API. " + response.body().toString())
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                result = "error!!"
//                Log.e("D_Test", "페일!")
//            }
//        })

        val tvWeightInput: TextView = findViewById(R.id.tvWeightInput)
        val curActivity = intent.extras?.getString("activity")
        when(curActivity) {
            "rm" -> tvWeightInput.text = "충분히 무겁다고 생각하는 무게를 입력해주세요."
            "exercise" -> tvWeightInput.text = "운동할 무게를 입력해주세요."
        }

        val etRmWeight: EditText = findViewById(R.id.etRmWeight)
        val bRmWeight: Button = findViewById(R.id.bRmWeight)
        bRmWeight.setOnClickListener{
            val strWeight: String = etRmWeight.text.toString()
            sendData(strWeight)
            intWeight = strWeight.toInt()

            when(curActivity) {
                "rm" -> {
                    val nextIntent = Intent(this, Exercise::class.java)
                    nextIntent.putExtra("activity", "rm")
                    nextIntent.putExtra("weight", intWeight)
                    startActivity(nextIntent)
                }
                "exercise" -> {
                    val nextIntent = Intent(this, Exercise::class.java)
                    nextIntent.putExtra("activity", "exercise")
                    nextIntent.putExtra("weight", intWeight)
                    startActivity(nextIntent)
                }
            }
        }
    }

    fun sendData(weight: String) {
        comment = apiService.patch_Test(1, "json", weight)
        comment.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("D_Test", "2차")
                try {
                    Log.e("D_Test", "post submitted to API. " + response.body().toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                result = "error!!"
                Log.e("D_Test", "페일!")
            }
        })
    }
}



