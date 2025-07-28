package com.kdkou.mvvmtutorial.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdkou.mvvmtutorial.repository.ContactRepository
import com.kdkou.mvvmtutorial.room.Contact
import kotlinx.coroutines.launch

class ContactVM(private val repository: ContactRepository): ViewModel(), Observable {
    val contacts = repository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }
    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)

        // resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)

        // resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun saveOrUpdate() {
        if (isUpdateOrDelete){
            contactToUpdateOrDelete.name = inputName.value!!
            contactToUpdateOrDelete.email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(Contact(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }
    }
    fun clearAllOrDelete() {
        if (isUpdateOrDelete){
            delete(contactToUpdateOrDelete)
        }else{
            clearAll()
        }
    }
    fun initUpdateAndDelete(contact: Contact) {
        inputName.value = contact.name
        inputEmail.value = contact.email
        contactToUpdateOrDelete = contact
        isUpdateOrDelete = true
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}