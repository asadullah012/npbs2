package com.galib.natorepbs2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.models.ComplainCentre
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ComplainCentreViewModel(private val mRepository: NPBS2Repository) : ViewModel() {

    private val _allComplainCenterList = MutableStateFlow(emptyList<ComplainCentre>())
    val allComplainCentre = _allComplainCenterList.asStateFlow()

    fun getAllComplainCenter() {
        viewModelScope.launch(IO) {
            mRepository.allComplainCentre.collectLatest {
                _allComplainCenterList.tryEmit(it)
            }
        }
    }

    fun getAllCCByAccount(account : String): LiveData<List<String>>{
        return mRepository.getCCByAccount(account).asLiveData()
    }

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        fetchDataBasedOnSearchText()
    }

    private val _filteredComplainCenter = MutableStateFlow<List<ComplainCentre>>(emptyList())
    val filteredComplainCenter: StateFlow<List<ComplainCentre>> = _filteredComplainCenter
    fun fetchDataBasedOnSearchText() {
        LogUtils.d("ComplainCenterViewModel", "fetchDataBasedOnSearchText: search text ${searchText.value}")
        val data = getAllCCByAccount(searchText.value)
        viewModelScope.launch(IO) {
            mRepository.getCCByAccount(searchText.value).collectLatest {
                Log.d("TAG", "fetchDataBasedOnSearchText: $it")
                val filteredList = ArrayList<ComplainCentre>()
                _allComplainCenterList.value.forEach { cc ->
                    if(it.contains(cc.name)){
                        filteredList.add(cc)
                    }
                }
                _filteredComplainCenter.tryEmit(filteredList)
            }
        }
    }
    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun disableSearch(){
        _isSearching.value = false
        _filteredComplainCenter.tryEmit(emptyList())
    }
}

class ComplainCentreViewModelFactory(private val repository: NPBS2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComplainCentreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComplainCentreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}