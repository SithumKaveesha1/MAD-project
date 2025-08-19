package com.example.selfemployeesmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ViewBudgetActivity : AppCompatActivity() {

    lateinit var budgetRecycierView: RecyclerView;
    lateinit var tvLoadingData: TextView
    private lateinit var budgetList: ArrayList<BudgetModel>
    lateinit var dbRef: DatabaseReference
    lateinit var createBudgetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_view_activity)
        supportActionBar?.hide()

        budgetRecycierView = findViewById(R.id.rvBudget)
        budgetRecycierView.layoutManager = LinearLayoutManager(this)
        budgetRecycierView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)
        createBudgetBtn = findViewById(R.id.btnInsert)

        budgetList = arrayListOf<BudgetModel>()
        getBudgetData()

        createBudgetBtn.setOnClickListener {
            val intent = Intent(this, InsertionBudgetActivity::class.java)
            startActivity(intent)
        }
    }
    fun getBudgetData(){

        budgetRecycierView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("budget")
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                budgetList.clear()
                if(snapshot.exists()){
                    for(budgetsnap in snapshot.children){
                        val budgetData = budgetsnap.getValue(BudgetModel::class.java)
                        budgetList.add(budgetData!!)
                    }
                    val mAdapter = BudgetAdapter(budgetList)
                    budgetRecycierView.adapter = mAdapter

                    budgetRecycierView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}