package com.jams.vedantattendancesystem.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.jams.vedantattendancesystem.R
import com.jams.vedantattendancesystem.model.punchInModel

class PunchInAdpater (options: FirestoreRecyclerOptions<punchInModel>):
    FirestoreRecyclerAdapter<punchInModel, PunchInAdpater.ViewHolder>(options)
{
    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView){

        var time = itemView.findViewById<TextView>(R.id.punchtime)
        var punchIn = itemView.findViewById<TextView>(R.id.punchInTxt)
        var punchOut = itemView.findViewById<TextView>(R.id.punchOutTxt)
        var punchLocation = itemView.findViewById<TextView>(R.id.LocationCard)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.punchincard, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: punchInModel) {

        holder.time.text = model.time.toString()
        holder.punchIn.text  = model.punchType
        holder.punchOut.text = model.punchType
        holder.punchLocation.text = model.location

    }
}