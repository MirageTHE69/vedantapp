package com.jams.vedantattendancesystem

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext
import com.jams.vedantattendancesystem.adpater.AdminPunchInAdapter
import com.jams.vedantattendancesystem.model.punchInModel
import java.util.*


class AdminDashboard : Fragment() {

    lateinit var adapter : AdminPunchInAdapter
    lateinit var AdminRecyclerView : RecyclerView
    lateinit var calendar : CalendarView
    lateinit var logoutBtn : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)
        // Inflate the layout for this fragment
        AdminRecyclerView = view.findViewById(R.id.AdminRcView)
        calendar = view.findViewById(R.id.calendarview)
        var start = true;

        if(start){


            val query: Query = FirebaseFirestore.getInstance().collection("Punch_table")
                .orderBy("time",Query.Direction.ASCENDING)
            val options = FirestoreRecyclerOptions.Builder<punchInModel>()
                .setQuery(query, punchInModel::class.java)
                .build();

            adapter = AdminPunchInAdapter(options)
            AdminRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            AdminRecyclerView.adapter = adapter;
            start=false
        }

        calendar.setOnDateChangeListener(CalendarView.OnDateChangeListener { _, Year, Month, DayofTheMonth ->

            Log.d(TAG, "CalendarView Clicked: $Year-$Month-$DayofTheMonth")

            setUpRecyclerViewAdmin(date = (Date(Year,Month,DayofTheMonth)))

        })

        logoutBtn = view.findViewById(R.id.LogoutButton)
        logoutBtn.setOnClickListener{

            Firebase.auth.signOut()

            view.findNavController().navigate(R.id.adminLoginFragment)

        }

        return view
    }


    fun setUpRecyclerViewAdmin(date: Date) {
        /*Log.d(TAG,"Date :- ${date}")

        Log.d(TAG, "setUpRecyclerViewAdmin: ${date.time}")


        val query: Query = FirebaseFirestore.getInstance().collection("Punch_table")
            .orderBy("time",Query.Direction.ASCENDING)




        val options = FirestoreRecyclerOptions.Builder<punchInModel>()
            .setQuery(query, punchInModel::class.java)
            .build();

        adapter = AdminPunchInAdapter(options)
        AdminRecyclerView.adapter = adapter;
        adapter.notifyDataSetChanged()*/

        val query: Query = FirebaseFirestore.getInstance().collection("Punch_table")
            .orderBy("time",Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<punchInModel>()
            .setQuery(query, punchInModel::class.java)
            .build();

        adapter = AdminPunchInAdapter(options)
        AdminRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        AdminRecyclerView.adapter = adapter;
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
   
}