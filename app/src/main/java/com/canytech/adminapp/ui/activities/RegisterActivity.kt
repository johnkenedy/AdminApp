package com.canytech.adminapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register_sing_up.setOnClickListener {
            registerUser()
        }

    }

    //Validates if all fields have been filled
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(edit_text_register_name.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_name), true)
                false
            }

            TextUtils.isEmpty(edit_text_register_email.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(edit_text_register_password.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(
                edit_text_register_confirm_password.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_confirm_password), true)
                false
            }

            else -> {
                // showErrorSnackBar(resources.getString(R.string.register_completed), false)
                true
            }
        }
    }

    private fun registerUser() {
        //check if the entries are valid or not
        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = edit_text_register_email.text.toString().trim { it <= ' ' }
            val password: String = edit_text_register_password.text.toString().trim { it <= ' ' }

            //Create a register a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        //Registration is successfully
                        if (task.isSuccessful) {

                            //Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                edit_text_register_name.text.toString().trim { it <= ' ' },
                                edit_text_register_email.text.toString().trim { it <= ' ' },
                            )

                            FireStoreClass().registerUser(this@RegisterActivity, user)

                           // FirebaseAuth.getInstance().signOut()
                           // finish()

                        } else {
                            hideProgressDialog()
                            //If the registering is not successful -> show error message
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    fun userRegistrationSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()
    }
}