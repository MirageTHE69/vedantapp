package com.jams.vedantattendancesystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jams.vedantattendancesystem.adpater.AdminPunchInAdapter
import com.jams.vedantattendancesystem.adpater.PunchInAdpater
import com.jams.vedantattendancesystem.model.punchInModel
import java.util.*


class AdminDashboard : Fragment() {

    lateinit var adapter : AdminPunchInAdapter
    lateinit var AdminRecyclerView : RecyclerView
    lateinit var calendar : CalendarView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)
        // Inflate the layout for this fragment
        AdminRecyclerView = view.findViewById(R.id.AdminRcView)
        calendar = view.findViewById(R.id.calendarview)

        calendar.setOnDateChangeListener(CalendarView.OnDateChangeListener({
            _,Year,Month,DayofTheMonth ->
            setUpRecyclerViewAdmin(date = Date("$Year-$Month-$DayofTheMonth"))
        }))

        return view
    }

    fun setUpRecyclerViewAdmin(date: Date) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid

        val query: Query = FirebaseFirestore.getInstance().collection("Punch_table").whereEqualTo("timestamp",date)
        val options = FirestoreRecyclerOptions.Builder<punchInModel>()
            .setQuery(query, punchInModel::class.java)
            .build();



        adapter = AdminPunchInAdapter(options)
        AdminRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        AdminRecyclerView.adapter = adapter;
    }
}