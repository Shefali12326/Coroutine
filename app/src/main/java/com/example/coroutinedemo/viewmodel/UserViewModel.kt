package com.example.coroutinedemo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.coroutinedemo.base.BaseViewModel
import com.example.coroutinedemo.model.QuoteList
import com.example.coroutinedemo.repository.RepositoryDemo
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserViewModel : BaseViewModel() {


    var userData=MutableLiveData<JsonElement>()
    var repositoryDemo:RepositoryDemo= RepositoryDemo()


    fun callApi(){
        
        CoroutineScope(Dispatchers.IO).launch {

//            var job=async{
                val responde=repositoryDemo.getProfileList()
                Log.d("222","~~~retrofitApiCall~44~~09"+Gson().toJson(responde.body()))


//            }
//            job.await()


        }


    }

}