package com.jams.vedantattendancesystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController




class LoginFragment : Fragment() {
    lateinit var EmployeeBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_login, container, false)
        // Inflate the layout for this fragment

       EmployeeBtn  = view.findViewById(R.id.SignInButtonEmployee)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        EmployeeBtn.setOnClickListener(View.OnClickListener {
            view.findNavController().navigate(R.id.employeeLogin)

        })
    }
}