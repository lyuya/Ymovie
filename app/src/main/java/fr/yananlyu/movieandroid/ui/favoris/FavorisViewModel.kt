package fr.yananlyu.movieandroid.ui.favoris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavorisViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favoris Fragment"
    }
    val text: LiveData<String> = _text
}