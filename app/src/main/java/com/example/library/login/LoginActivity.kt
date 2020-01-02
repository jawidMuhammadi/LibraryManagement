package com.example.library.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.media.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.example.library.main.MainActivity
import com.example.library.R
import com.example.library.utils.LoginResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val factory = LoginViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            viewModel.onLoginButtonClick(ed_name.text.toString(), ed_password.text.toString())
        }

        btn_singup.setOnClickListener {
            viewModel.onSignUpButtonClick(ed_name.text.toString(), ed_password.text.toString())
        }


        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.onLoginButtonClick.observe(this, Observer {
            it.getContentIfNotHandled()?.let { loginResult ->
                if (loginResult == LoginResult.Success) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    ed_name.error = getString(R.string.wrong_name)
                    ed_password.error = getString(R.string.wrong_password)
                }
            }
        })

        viewModel.onSingUpButtonClick.observe(this, Observer {
            it.getContentIfNotHandled()?.let { loginResult ->
                if (loginResult == LoginResult.Success) {
                    Snackbar.make(
                        findViewById(R.id.root_layout),
                        getString(R.string.sing_up_succes),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
