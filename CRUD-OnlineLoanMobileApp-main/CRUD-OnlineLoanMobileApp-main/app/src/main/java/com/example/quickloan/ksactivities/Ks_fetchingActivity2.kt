package com.example.quickloan.ksactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickloan.R
import com.example.quickloan.ksadapters.LoneAdapter
import com.example.quickloan.ksmodel.KsLoneModel
import com.google.firebase.database.*


class Ks_fetchingActivity2 : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvKsLoadingData: TextView
    private lateinit var lonList: ArrayList<KsLoneModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ks_fetching2)

        empRecyclerView = findViewById(R.id.ksLon)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvKsLoadingData = findViewById(R.id.tvKsLoadingData)


        lonList = arrayListOf<KsLoneModel>()

        val backButton: ImageButton = findViewById(R.id.btnBack2)

        getLonesData()

        backButton.setOnClickListener {
            val intent = Intent(this, Ks_ButtonActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun filterLonesData() {
        TODO("Not yet implemented")


    }

    private fun getLonesData() {
        empRecyclerView.visibility = View.GONE
        tvKsLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Loans")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                lonList.clear()
                if (snapshot.exists()){
                    for(empSnap in snapshot.children){
                        val lonData = empSnap.getValue(KsLoneModel::class.java)
                        lonList.add(lonData!!)
                    }
                    val mAdapter = LoneAdapter(lonList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : LoneAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Ks_fetchingActivity2, LoneDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("lonId",lonList[position].loneId)
                            intent.putExtra("fname",lonList[position].fName)
                            intent.putExtra("nno",lonList[position].nNo)
                            intent.putExtra("mno",lonList[position].mNo)
                            intent.putExtra("emails",lonList[position].emails)
                            intent.putExtra("address",lonList[position].address)
                            intent.putExtra("hmuch",lonList[position].hMuch)
                            intent.putExtra("hlong",lonList[position].hLong)
                            startActivity(intent)

                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvKsLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
  }

}
