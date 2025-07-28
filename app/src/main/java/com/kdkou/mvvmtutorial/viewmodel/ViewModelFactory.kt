package com.kdkou.mvvmtutorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kdkou.mvvmtutorial.repository.ContactRepository

class ViewModelFactory (private val repository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactVM::class.java)) {
            return ContactVM(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}