package com.jams.vedantattendancesystem

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.jams.vedantattendancesystem.model.userModel


class RegisterFragment : Fragment() {

    lateinit var Name: EditText
    lateinit  var Password:EditText
    lateinit var Email:EditText
    lateinit var Designation:EditText
    lateinit var SignUp: Button
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_register, container, false)

        Name = view.findViewById<EditText>(R.id.NameEditText)
        Password = view.findViewById<EditText>(R.id.PasswordEditText)
        Email = view.findViewById<EditText>(R.id.EmailEditText)
        Designation = view.findViewById<EditText>(R.id.DesignationEditText)
        SignUp = view.findViewById<Button>(R.id.SignUpButton)

        auth = FirebaseAuth.getInstance()

        SignUp.setOnClickListener { 
           NewRegisterUser() 
        }


        // Inflate the layout for this fragment
        return view
    }

    private fun NewRegisterUser() {

        val email = Email.text.toString().trim { it <= ' ' }
        val password = Password.text.toString().trim { it <= ' ' }
        val name = Name.text.toString().trim { it <= ' ' }
        val designation = Designation.text.toString().trim { it <= ' ' }

        if (name.isEmpty()) {
            Name.error = "Full Name Required"
            Name.requestFocus()
            return
        }
        if (email.isEmpty()) {
            Email.error = "Email Required"
            Email.requestFocus()
            return
        }
        if (password.isEmpty()) {
            Password.error = "Password Required"
            Password.requestFocus()
            return
        }
        if (password.length < 6) {
            Password.error = "Password Should Be Greater Than 6 Characters!."
            Password.requestFocus()
        }
        if (designation.isEmpty()) {
            Designation.error = "Designation Required"
            Designation.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.error = "Provide Valid Email"
            Email.requestFocus()
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = userModel(userName = name, email = email, desigenation =  designation)
                FirebaseFirestore.getInstance().collection("Users")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .set(user).addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "NewRegisterUser: ${task.isComplete}")
                            view?.findNavController()?.navigate(R.id.employeeDashBoard)

                        } else {
                            Log.d(TAG, "NewRegisterUser: ${task.exception}")
                        }
                    })
            }else{
                Log.d(TAG, "NewRegisterUser: ${task.exception}")
            }
        }


    }


}