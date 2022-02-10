package com.jams.vedantattendancesystem

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class EmployeeLogin : Fragment() {
    lateinit var Email:EditText
    lateinit var Password:EditText
    lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_employee_login, container, false)
         Email= view.findViewById(R.id.EmployeeEmailEditText)
         Password = view.findViewById(R.id.EmployeePasswordEditText)
        val LoginBtn: Button = view.findViewById<Button>(R.id.LoginEmployeeButton)
         auth = FirebaseAuth.getInstance()
        val SignUpTextView: TextView = view.findViewById<TextView>(R.id.SignUpTextView)

        SignUpTextView.setOnClickListener {
            view.findNavController().navigate(R.id.registerFragment)
        }
        LoginBtn.setOnClickListener {
            LoginUser()

        }


        return view
    }

    private fun LoginUser() {

        val email: String = Email.getText().toString().trim { it <= ' ' }
        val password: String = Password.getText().toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            Email.error = "Cannot Be Empty"
            Email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.error = "Enter Correct Email"
            Email.requestFocus()
        }
        if (password.isEmpty() || password.length < 6) {
            Password.error = "Password cannot be neither Empty nor less than 6 characters!"
            Password.requestFocus()
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        val LoginUser: FirebaseUser? = auth.getCurrentUser()
                        view?.findNavController()?.navigate(R.id.employeeDashBoard)
                    } else {

                    }
                })
    }


}