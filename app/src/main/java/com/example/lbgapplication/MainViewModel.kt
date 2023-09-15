package com.example.lbgapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lbgapplication.ui.CatsDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {


    companion object {
        val TAG: String = MainViewModel::class.java.canonicalName
    }

    private var job: Job? = null

    private val _catsData = MutableLiveData<List<CatsDataModel>>()
    val catsData: LiveData<List<CatsDataModel>>
        get() = _catsData

    //UI
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val retrofitInstance = NetworkRetrofit.getRetrofitInstance()

    fun getCatsData() {
        _isLoading.postValue(true)
        job = viewModelScope.launch(Dispatchers.IO) {
            with(retrofitInstance.fetCatsImages(25)) {
                if (isSuccessful) {
                    _catsData.postValue(this.body())
                    _isLoading.postValue(false)
                } else {
                    onError("Error : ${this.message() ?: "Error occurred"}")
                }
            }
        }
    }

    private fun onError(message: String) {
        _error.postValue(message)
        _isLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}