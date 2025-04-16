package com.example.myassssmentapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvResult = findViewById(R.id.tvResult)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                tvResult.text = "Please fill all fields"
                return@setOnClickListener
            }

            val request = LoginRequest(username, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val keypass = response.body()?.keypass
                        tvResult.text = "Login success! keypass: $keypass"
                        // TODO: Start Dashboard Activity and pass keypass
                    } else {
                        tvResult.text = "Login failed. Check credentials."
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    tvResult.text = "Network error: ${t.message}"
                }
            })
        }
    }
}
