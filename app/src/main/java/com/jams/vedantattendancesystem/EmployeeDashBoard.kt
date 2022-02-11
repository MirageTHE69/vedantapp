package com.jams.vedantattendancesystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jams.vedantattendancesystem.adpater.PunchInAdpater
import com.jams.vedantattendancesystem.model.punchInModel


class EmployeeDashBoard : Fragment() {

    lateinit var  rcview : RecyclerView
    lateinit var  adpater : PunchInAdpater
    lateinit var punchInBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_employee_dash_board, container, false)
        rcview =view.findViewById(R.id.rcViewPunch)
        punchInBtn  = view.findViewById(R.id.PunchInBtn)



        punchInBtn.setOnClickListener {

            findNavController().navigate(R.id.punchInFragment)

        }



        return view
    }


    fun setUpRecyclerView() {
val id = FirebaseAuth.getInstance().currentUser!!.uid

        val query: Query = FirebaseFirestore.getInstance().collection("Punch_table").whereEqualTo("user_id",id)
        val options = FirestoreRecyclerOptions.Builder<punchInModel>()
            .setQuery(query, punchInModel::class.java)
            .build();

        adpater = PunchInAdpater(options)
        rcview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        rcview.adapter = adpater;



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

    }


}