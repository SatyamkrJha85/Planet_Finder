package com.theapplication.planetfinder.Mvvm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapplication.planetfinder.Mvvm.Api.PlanetInstance
import com.theapplication.planetfinder.Mvvm.Api.PlanetResponse
import com.theapplication.planetfinder.Mvvm.Model.PlanetData
import kotlinx.coroutines.launch

class PlanetViewModel:ViewModel() {
    private val planetApi = PlanetInstance.planetapi
    private val _planerResult = MutableLiveData<PlanetResponse<PlanetData>>()
    val palnetResult:LiveData<PlanetResponse<PlanetData>> = _planerResult

    fun getdata(name:String){
        _planerResult.value = PlanetResponse.loading
        viewModelScope.launch {
            try {
                val response = planetApi.getplanet(name)
                if (response.isSuccessful){
                    response.body()?.let {
                        _planerResult.value=PlanetResponse.Sucess(it)
                    }
                }
                else{
                    _planerResult.value=PlanetResponse.Errors("Something went wrong")
                }
            }
            catch (e:Exception){
                _planerResult.value=PlanetResponse.Errors("Something went wrong")
            }
        }
    }
}