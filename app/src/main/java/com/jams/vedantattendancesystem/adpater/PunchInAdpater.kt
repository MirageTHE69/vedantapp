package com.jams.vedantattendancesystem.adpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.type.Date

import com.jams.vedantattendancesystem.R
import com.jams.vedantattendancesystem.model.punchInModel
import java.text.SimpleDateFormat
import java.util.*

class PunchInAdpater (options: FirestoreRecyclerOptions<punchInModel>):
    FirestoreRecyclerAdapter<punchInModel, PunchInAdpater.ViewHolder>(options)
{
    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView){

        var time = itemView.findViewById<TextView>(R.id.punchtime)
        var punchIn = itemView.findViewById<TextView>(R.id.punchInTxt)
        var punchLocation = itemView.findViewById<TextView>(R.id.LocationCard)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.punchincard, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: punchInModel) {

        val timestamp2 =  Date(model.time?.toDate().toString())
        holder.time.text = timestamp2.toString()
        holder.punchIn.text  = "Punch: ${model.punchType}"
        holder.punchLocation.text = model.location

    }
}