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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

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

        //coroutine with flow
        producerFlow()
        consumerOfFlow()//cold flow
        producerByChannel()//hot observable
        consumerOfChannel()
        producerOfSharedFlow()
        consumerOfSharedFlow() //hot nature flow
        producerOfStateFlow()
        consumerOfStateFlow()//hot flow
        apiCallWithFlow()
        //shared flow ->multiple consumers support, not have state to store current val
        //state flow->multiple consumers support, have start so last join consumer can still get recent value no lost

//        funExtens
        funForExtensionFun()//overriding
        funDefaultParameter()//overloading
        defaultParameter()

    }

    //############################# default & Named Parameter ##########
    private fun defaultParameter(str:String="default",keyValue:Int=8) {
         Log.d("222","~~str~~~"+str+keyValue)
    }

    private fun funDefaultParameter() {
        defaultParameter()
        defaultParameter("First parameter send")
//        defaultParameter(5)
        //Named argument
        student("22",roll_no=2,standard="standard")//if want to jump parameter send thing then need to specify arg name
        student("22",roll_no=2)//even sequence doesn't matter if use Named argument

    }

    fun student( name: String="Praveen", standard: String="IX" , roll_no: Int=11 ) {
        Log.d("222","Name of the student is: $name")
        Log.d("222","Standard of the student is: $standard")
        Log.d("222","Roll no of the student is: $roll_no")
    }

    //############################# default & Named Parameter ##########

    private fun funForExtensionFun() {

        //for custom
        class Draw(var radius:Int){
            fun area():Double{
            return Math.PI*radius*radius
            }

        }

        //fun extensibility
       fun Draw.perimeter():Double{
           return 2*Math.PI*radius
        }

       var draw= Draw(3);

        Log.d("222","~~funForExtensionFun~~"+draw.area())
        Log.d("222","~~funForExtensionFun~~"+draw.perimeter())



        //for library class
        fun  Int.abs( no:Int):Int{
            Log.d("222","~~funForExtensionFun~~"+this)
            return if(no<0) this else -no

    }
//        var i=int()
        var i:Int=9

        Log.d("222","~~funForExtensionFun~~library"+i.abs(4))

    }

    private fun apiCallWithFlow() {



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

    //coroutine with flow
    private fun consumerOfStateFlow() {
        var job1 = GlobalScope.launch {
            val data: Flow<Int> = producerOfStateFlow()
            delay(6000)
            data.collect {
                Log.d("222", "~~consumerOfStateFlow~+" + it)

            }

        }
    }
    private fun producerOfStateFlow() : StateFlow<Int> {
        val mutableStateFlow = MutableStateFlow<Int>(10)
//        var list = listOf<Int>(1, 2, 3, 4)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }

    private fun producerOfSharedFlow() : Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(1)
        var list = listOf<Int>(1, 2, 3, 4)
        GlobalScope.launch {
            list.forEach {
//
                mutableSharedFlow.emit(it)
                delay(1000)
            }
        }
        return mutableSharedFlow
    }
    private fun consumerOfSharedFlow() {
        var job1=  GlobalScope.launch {
            val data: Flow<Int> =producerOfSharedFlow()
            delay(1000)
            data.collect {
                Log.d("222","~~consumerOfSharedFlow~first+"+it)

            }

            var job1=  GlobalScope.launch {
                val data: Flow<Int> =producerOfSharedFlow()
                delay(8000)
                data.collect {
                    Log.d("222","~~consumerOfSharedFlow~second+"+it)

                }
            }}}


    private fun consumerOfChannel() {
        GlobalScope.launch(Dispatchers.IO) {

//           channel.receive()
//           channel.receive()
            Log.d("222","~~consumerOfChannel~+"+channel.receive().toString())
            Log.d("222","~~consumerOfChannel~+"+channel.receive().toString())
        }
    }

    val channel= Channel<Int>()
    private fun producerByChannel() {
        GlobalScope.launch(Dispatchers.IO) {
            channel.send(1)
            channel.send(2)
        }
    }

    //with events
    private fun consumerOfFlow() {
        //if not collect then producer not produce until consumer not there, cancel by default
        var job=  GlobalScope.launch {
            val data: Flow<Int> =producerFlow()// direct method

            //########### with filter ######## flowOn to indicste thread above
            try{
                producerFlow().map { it*2 }
                    .filter { it>0 }.flowOn(Dispatchers.IO).collect{

                    }
            }catch (E:Exception){

            }
//######################### with events   start ###########################
//            producerFlow().
//            onStart {
//                emit(-1)
//                 Log.d("222","~~consumerOfFlow~onStart+")
//
//             }.
//            onCompletion {
//                 Log.d("222","~~consumerOfFlow~onCompletion+")
//             emit(5)
//             }
//                    .onEach {
//                 Log.d("222","~~consumerOfFlow~onEach+"+it)
//             }.collect {
//                 Log.d("222","~~consumerOfFlow~first+"+it)
//
//             }


//######################### with events  end  ###########################
            data .collect {
                Log.d("222","~~consumerOfFlow~first+"+it)

            }
        }

//multiple consumer
        var job1=  GlobalScope.launch {
            val data: Flow<Int> =producerFlow()
            delay(1000)
            data.collect {
                Log.d("222","~~consumerOfFlow~second+"+it)

            }
        }

        //for cancel
//        GlobalScope.launch {
//            delay(3000)
//            job.cancel();
//        }

    }

    fun producerFlow()=flow<Int> {
        var list= listOf<Int>(1,2,3,4)
        list.forEach {
            delay(1000)
            emit(it)
        }

    }





}

//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050
//https://amitshekhar.me/blog/retrofit-with-kotlin-flow