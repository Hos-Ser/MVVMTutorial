package com.kdkou.mvvmtutorial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdkou.mvvmtutorial.databinding.ActivityMainBinding
import com.kdkou.mvvmtutorial.repository.ContactRepository
import com.kdkou.mvvmtutorial.room.Contact
import com.kdkou.mvvmtutorial.room.ContactDB
import com.kdkou.mvvmtutorial.view.MyRecyclerViewAdapter
import com.kdkou.mvvmtutorial.viewmodel.ContactVM
import com.kdkou.mvvmtutorial.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var contactVM: ContactVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ContactDB.getInstance(applicationContext).contactDAO
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        contactVM = ViewModelProvider(this, factory).get(ContactVM::class.java)

        binding.contactViewModel = contactVM
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
    }
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayUsersList()
    }

    private fun displayUsersList() {
        contactVM.contacts.observe(this, {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it, {
                selectedItems: Contact -> listItemClicked(selectedItems)
            }
            )
        })
    }
    private fun listItemClicked(selectedItems: Contact) {
        Toast.makeText(this,
            "Name: ${selectedItems.name}, Email: ${selectedItems.email}",
            Toast.LENGTH_SHORT).show()

        contactVM.initUpdateAndDelete(selectedItems)
    }
}