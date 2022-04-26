package com.example.myrecoder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myrecoder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val sharePreference by lazy {
        getSharedPreferences("record", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val model = MainViewModel()
        model.getCount().observe(this) { it ->
            binding.textViewCount.text = it.toString()
        }

        val lastFood = sharePreference.getString("food", null)
        val lastTime = sharePreference.getString("time", null)
        if (lastFood != null && lastTime != null)
            displayRecord(lastFood, lastTime)
        binding.buttonRecord.setOnClickListener { (onSave()) }
        binding.buttonAddCount.setOnClickListener {
            model.addCount()
        }
    }

    private fun displayRecord(lastFood: String, lastTime: String) {
        val lastTime = LocalDateTime.parse(lastTime)
        val now = LocalDateTime.now()
        // 상단 정보 출력
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm", Locale.KOREA)
        binding.textViewRecord.text = "${lastTime.format(formatter)} - $lastFood"
        //하단 경과 시간 출력
        val hours = ChronoUnit.HOURS.between(lastTime, now)
        var minutes = ChronoUnit.MINUTES.between(lastTime, now)
        minutes -= 60 * hours
        binding.textViewElapsed.text =
            String.format(Locale.KOREA, "%02d 시간 %02d분 경과", hours, minutes)

    }

    private fun onSave() {
        with(sharePreference.edit()) {
            val food = binding.editTextFoodName.text.toString()
            if (food.isNotEmpty()) {
                this.putString("food", food)
                this.putString("time", LocalDateTime.now().toString())
                this.apply()
            }
        }
    }

//    fun main(){
//        CoroutineScope(Dispatchers.Default).launch {
//            println("Default1")
//        launch {
//            delay(100L)
//            println("Default2")
//        }
//            println("Default3")
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            println("I01")
//            launch {
//                delay(100L)
//                println("I02")
//            }
//        }
//    }
}
