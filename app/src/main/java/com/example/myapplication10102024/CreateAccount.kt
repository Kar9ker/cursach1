package com.example.myapplication10102024

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication10102024.RetrofitClient.apiService
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_account)

        val edCreateName = findViewById<EditText>(R.id.edittext_create_name)
        val edCreatePass = findViewById<EditText>(R.id.edittext_create_pass)
        val edPassConfirm = findViewById<EditText>(R.id.edittext_confirm_pass)

        val back = findViewById<TextView>(R.id.text_view_create_back)
        back.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        val register = findViewById<MaterialButton>(R.id.button_register)
        register.setOnClickListener{
            val name = edCreateName.text.toString()
            val pass = edCreatePass.text.toString()
            val confirm = edPassConfirm.text.toString()

            if (pass != confirm) {
                Toast.makeText(this,
                    "Пароли должны совпадать",
                    Toast.LENGTH_SHORT).show()
            }else {
                lifecycleScope.launch {
                    val users = fetchAllObjects()
                    if (users != null) {
                        var userNames: MutableList<String> = mutableListOf()
                        for (user in users) {
                            userNames.add(user.name)
                        }

                        if (name in userNames) {
                            Toast.makeText(
                                this@CreateAccount,
                                "Пользователь с таким именем уже существует",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            createUser(name, pass)
                            withContext(Dispatchers.Main) {
                                val createAccIntent = Intent(
                                    this@CreateAccount,
                                    HomeActivity::class.java
                                )
                                startActivity(createAccIntent)
                                finish()
                            }
                        }

                    } else {
                        Toast.makeText(
                            this@CreateAccount,
                            "Ошибка сервера",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private suspend fun createUser(name: String, password: String) {
        try {
            val newUser = UserObject(name, password)
            apiService.createObject(newUser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchAllObjects(): List<UserObject>? {
        return try {
            val response = apiService.getAllObjects()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}