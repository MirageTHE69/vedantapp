package com.jams.vedantattendancesystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdminLoginFragment : Fragment() {
    lateinit var auth : FirebaseAuth
    lateinit var    EmailEditext : EditText
    lateinit var  PasswordEditText:EditText
    lateinit var SubmitButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_admin_login, container, false)
        // Inflate the layout for this fragment
         EmailEditext  = view.findViewById(R.id.AdminEmailEditText)
        PasswordEditText  = view.findViewById(R.id.AdminPasswordEditText)
         SubmitButton  = view.findViewById(R.id.AdminEmployeeButton)



        auth = FirebaseAuth.getInstance()


        return view
    }

    override fun onStart() {
        super.onStart()

        SubmitButton.setOnClickListener {
            val Email : String = EmailEditext.text.toString()
            val Pass : String = PasswordEditText.text.toString()
            if(Email == "admin@attendancesystem.com"){
                auth.signInWithEmailAndPassword(Email, Pass)
                    .addOnCompleteListener(requireActivity(),
                        OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                val LoginUser: FirebaseUser? = auth.getCurrentUser()
                                it.findNavController().navigate(R.id.adminDashboard)
                            } else {
                                Toast.makeText(requireContext(),"Incorrect User ID",Toast.LENGTH_LONG).show()
                            }
                        })
            }




        }
    }


}