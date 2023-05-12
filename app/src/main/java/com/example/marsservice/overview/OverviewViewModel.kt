package com.example.marsservice.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsservice.network.MarsApi
import com.example.marsservice.network.MarsApiFilter
import com.example.marsservice.network.MarsProperty
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: MutableLiveData<MarsProperty?>
        get() = _navigateToSelectedProperty

    // The internal MutableLiveData String that stores the most recent response
    private val _properties = MutableLiveData<List<MarsProperty>>()

    val property: MutableLiveData<List<MarsProperty>>
        get() = _properties

    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _response = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the response String
    val response: MutableLiveData<MarsApiStatus>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getProperties(filter.value)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}