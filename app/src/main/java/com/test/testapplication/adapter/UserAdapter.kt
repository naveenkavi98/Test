package com.test.testapplication.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.testapplication.R
import com.test.testapplication.model.UserResponse
import com.test.testapplication.utils.CommonUtils
import java.util.*


class UserAdapter(val data: UserResponse) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onClicked: OnClicked?= null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_user_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            itemView.setOnClickListener {
                onClicked?.clicked(data[position].id.toString())
            }

            txtName.text = CommonUtils.toCamelCaseSentence(data[position].name)
            txtEmail.text = data[position].email.toLowerCase()

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return data.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtEmail: TextView = itemView.findViewById(R.id.txt_email)
    }

    fun setOnClicked (listener: OnClicked){
        onClicked = listener
    }

    interface OnClicked {
        fun clicked(id : String)
    }

}