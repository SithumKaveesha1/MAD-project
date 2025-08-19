package com.example.quickloan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class nv_payments : AppCompatActivity() {

    private lateinit var payRecyclerView: RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var paymentList: ArrayList<PaymentModel>
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_payments)

        payRecyclerView=findViewById(R.id.recyclerView)
        payRecyclerView.layoutManager=LinearLayoutManager(this)
        payRecyclerView.setHasFixedSize(true)
        tvLoadingData=findViewById(R.id.tvloadingdata)

        paymentList= arrayListOf<PaymentModel>()

        getPaymentsData()
    }
    private fun getPaymentsData(){
        payRecyclerView.visibility= View.GONE
        tvLoadingData.visibility=View.VISIBLE



            dbRef = FirebaseDatabase.getInstance().getReference("PaymentHistory")
            dbRef.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                paymentList.clear()
                if(snapshot.exists()){
                    for(paySnap in snapshot.children){
                        val payData =paySnap.getValue(PaymentModel::class.java)
                        paymentList.add(payData!!)
                    }
                    val mAdapter=PayAdapter(paymentList)
                    payRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object :PayAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@nv_payments,nv_paymentsDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("loanId",paymentList[position].LoanID)
                            intent.putExtra("date",paymentList[position].Date)
                            intent.putExtra("name",paymentList[position].CardHolderName)
                            intent.putExtra("amount",paymentList[position].RepaymentAmount)
                            intent.putExtra("postalCode",paymentList[position].PostalCode)
                            intent.putExtra("mobileNo",paymentList[position].MobileNO)
                            startActivity(intent)



                        }

                    })

                    payRecyclerView.visibility=View.VISIBLE
                    tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}