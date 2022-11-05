package com.example.coroutinedemo.view

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.coroutinedemo.R
import com.example.coroutinedemo.viewmodel.UserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    lateinit var tvChangeText:TextView
    var userViewModel=UserViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        initateCoroutine()
        callParallelFun()
        waitForSpecificJob()
        retrofitApiCall()
        attachObserver()
    }

    private fun attachObserver() {
        userViewModel.userData.observe(this, Observer {
            Log.d("222","~~~userData~~~"+Gson().toJson(it))
        })

        userViewModel.isLoading.observe(this, Observer {
            Log.d("222","~~~isLoading~~~"+it)
        })
        userViewModel.apiError.observe(this, Observer {
            Log.d("222","~~~apiError~~~"+it)
        })

    }

    private fun retrofitApiCall() {
        Log.d("222","~~~retrofitApiCall~~~")
        userViewModel.callApi()
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

//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050