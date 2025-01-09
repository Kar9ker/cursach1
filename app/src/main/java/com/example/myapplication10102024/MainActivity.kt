package com.example.myapplication10102024

import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.Intents.UI
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication10102024.RetrofitClient.apiService
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edLogin = findViewById<EditText>(R.id.edittext_login)
        val edPassword = findViewById<EditText>(R.id.edittext_password)

        val createAcc = findViewById<TextView>(R.id.text_view_create_acc)
        createAcc.setOnClickListener {
            val createAccIntent = Intent(this, CreateAccount::class.java)
            startActivity(createAccIntent)
        }

        val loginBtn = findViewById<MaterialButton>(R.id.button_sign_in)
        loginBtn.setOnClickListener {

            lifecycleScope.launch {
                val users = fetchAllObjects()
                if (users != null) {
                    val login = edLogin.text.toString()
                    val password = edPassword.text.toString()
                    var flag = true
                    for (user in users) {
                        if (login == user.name && password == user.password) {
                            withContext(Dispatchers.Main) {
                                val createAccIntent = Intent(this@MainActivity,
                                    HomeActivity::class.java).apply {
                                    putExtra("USERNAME", login)
                                }
                                startActivity(createAccIntent)
                                flag = false
                                finish()
                            }
                        }
                    }
                    if (flag) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Неверные данные", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity,
                            "Ошибка сервера",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private suspend fun fetchAllObjects(): List<UserObject>? {
        return try {
            val response = apiService.getAllObjects()
            response // Возвращаем список users
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}