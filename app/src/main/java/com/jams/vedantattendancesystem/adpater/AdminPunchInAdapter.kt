package com.jams.vedantattendancesystem.adpater

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

import com.jams.vedantattendancesystem.R
import com.jams.vedantattendancesystem.model.punchInModel
import java.util.*

class AdminPunchInAdapter (options: FirestoreRecyclerOptions<punchInModel>):
    FirestoreRecyclerAdapter<punchInModel, AdminPunchInAdapter.ViewHolder>(options)
{
    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView){

        var time = itemView.findViewById<TextView>(R.id.punchtime)
        var punchIn = itemView.findViewById<TextView>(R.id.punchInTxt)
        var punchLocation = itemView.findViewById<TextView>(R.id.LocationCard)
        var UsernameText = itemView.findViewById<TextView>(R.id.UserNameTextView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adminpunchdetailcard, parent, false))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: punchInModel) {

        var timestamp2 =  Date(model.time!!.toDate().toString())
        holder.time.text = timestamp2.toString()
        holder.punchIn.text  = "Punch: ${model.punchType}"
        holder.punchLocation.text = model.location
        val user = FirebaseFirestore.getInstance().collection("Users").document(model.user_id)
        user.get().addOnSuccessListener {
            if (it.get("email").toString().isNotBlank()) {
                holder.UsernameText.text = " "+it.get("email")
            }

        }





    }
    }