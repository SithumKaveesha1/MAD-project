package com.example.quickloan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PayAdapter(private  val payList: ArrayList<PaymentModel>):
    RecyclerView.Adapter<PayAdapter.ViewHolder>() {

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener:onItemClickListener){
        mListener=clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayAdapter.ViewHolder{
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.nv_paymentlist,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: PayAdapter.ViewHolder, position: Int) {
        val currentPayment =payList[position]
        holder.tvPayDetails.text=currentPayment.Date
    }

    override fun getItemCount(): Int {
        return payList.size
    }
    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        val tvPayDetails :TextView=itemView.findViewById(R.id.tvPaymentDetails)

        init{
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}