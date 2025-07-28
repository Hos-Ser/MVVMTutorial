package com.kdkou.mvvmtutorial.repository

import com.kdkou.mvvmtutorial.room.Contact
import com.kdkou.mvvmtutorial.room.ContactDAO

class ContactRepository(private val contactDAO: ContactDAO) {
    val contacts = contactDAO.getAllContacts()
    suspend fun insert(contact: Contact): Long {
        return contactDAO.insertContact(contact)
    }
    suspend fun delete(contact: Contact) {
        return contactDAO.deleteContact(contact)
    }
    suspend fun update(contact: Contact) {
        return contactDAO.updateContact(contact)
    }
    suspend fun deleteAll() {
        return contactDAO.deleteAll()
    }
}