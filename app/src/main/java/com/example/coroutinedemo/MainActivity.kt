package com.example.coroutinedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var tvChangeText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        initateCoroutine()
        callParallelFun()
        waitForSpecificJob()
        retrofitApiCall()
    }

    private fun retrofitApiCall() {

    }

    private fun waitForSpecificJob() {
        GlobalScope.launch {
           delay(2000)
          var job2= async {
               delay(8000)
              Log.d("222","job2.join()~~~~")
           }
            Log.d("222","job2.join()")

            job2.join()
            var job3= async {
                delay(5000)
                Log.d("222","job3.join()")
            }

            Log.d("222","job3.join()000")

        }
    }

    private fun callParallelFun() {
        GlobalScope.launch (Dispatchers.IO){
           var job1= async {
                delay(4000)
               return@async "first api call"
            }

            var job2=async { delay(6000)
                return@async "second api call"
            }
            Log.d("222","~~job1.await()~~"+job1.await()+"~~~"+job2.await())

        }
    }

    private fun initialize() {
        tvChangeText=findViewById<TextView>(R.id.tvChangeText)
    }

    private fun initateCoroutine() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("222","~~~enter in coroutine~~~")


            delay(8000)
            withContext(Dispatchers.Main){
                tvChangeText.text="final"
            }
            Log.d("222","~~~end in coroutine~~~")

        }
        Log.d("222","~~~outside coroutine~~~")

    }
}