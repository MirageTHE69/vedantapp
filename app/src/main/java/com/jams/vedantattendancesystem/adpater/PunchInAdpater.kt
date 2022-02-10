package com.jams.vedantattendancesystem.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.core.view.View
import com.jams.vedantattendancesystem.R
import com.jams.vedantattendancesystem.model.punchInModel

class PunchInAdpater (options: FirestoreRecyclerOptions<punchInModel>):
    FirestoreRecyclerAdapter<punchInModel, PunchInAdpater.ViewHolder>(options)
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var time = itemView.findViewById<TextView>(R.id.EventNameTextView)
        var punchIn = itemView.findViewById<TextView>(R.id.EventDateTextView)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.eventitem, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: punchInModel) {

        holder.time.text = model.punchIn
        holder.punchIn  = model.time

    }
}