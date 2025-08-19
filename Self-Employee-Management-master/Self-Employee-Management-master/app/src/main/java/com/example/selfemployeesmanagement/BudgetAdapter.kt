package com.example.selfemployeesmanagement

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.selfemployeesmanagement.BudgetModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class BudgetAdapter(private val budgetList: ArrayList<BudgetModel>) :
    RecyclerView.Adapter<BudgetAdapter.ViewHolder>() {

    private var mListener: onItemClickListener? = null
    private lateinit var dbRef: DatabaseReference

    interface onItemClickListener{
        fun onItemClick(position: Int)
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_view_activity, parent, false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnClickListener {
            mListener?.onItemClick(holder.adapterPosition)
        }
        holder.editButton.setOnClickListener {
            mListener?.onEditClick(holder.adapterPosition)
        }

        holder.deleteButton.setOnClickListener {
            mListener?.onDeleteClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBudget = budgetList[position]
        holder.customername.text= currentBudget.name
        holder.period.text = currentBudget.period
        holder.currency.text = currentBudget.currencyType

        var totalExpenses = 0.0

        totalExpenses += (currentBudget.foods?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.shopping?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.transpotaion?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.housing?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.communication?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.finance?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.life?.toDoubleOrNull() ?: 0.0)
        totalExpenses += (currentBudget.other?.toDoubleOrNull() ?: 0.0)

        val balance = currentBudget.amount?.toDoubleOrNull()?.minus(totalExpenses)
        holder.balanceTextView.text = String.format("%.2f", balance ?: 0.0)

        // Set progress bar
        holder.progressBar.progress = balance?.toInt() ?: 0
        holder.progressBar.progressDrawable.setColorFilter(
            if (balance != null && balance < 0) Color.RED else Color.GREEN,
            PorterDuff.Mode.SRC_IN
        )




//        holder.totalExpensesTextView.text = holder.getTotalExpensesForPeriod(budgetModel).toString()

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete Budget")
            builder.setMessage("Are you sure you want to delete this budget?")
            builder.setPositiveButton("Delete") { dialog, which ->
                val budgetModel = budgetList[position]
                Log.d("BudgetAdapter", "Deleting budget with ID: ${budgetModel.budgetId!!}")
                dbRef.child(budgetModel.budgetId!!).removeValue()
                budgetList.removeAt(position)
                notifyItemRemoved(position)
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                // Do nothing
            }
            builder.show()
        }

        holder.editButton.setOnClickListener {
            val intent = Intent(it.context, UpdateBudgetActivity::class.java)
            intent.putExtra("budgetId", budgetList[position].budgetId)
            it.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customername : TextView = itemView.findViewById(R.id.customername)
        val period : TextView = itemView.findViewById(R.id.period)
        val editButton: Button = itemView.findViewById(R.id.btnEdit)
        val deleteButton: Button = itemView.findViewById(R.id.btndelete)
        val balanceTextView: TextView = itemView.findViewById(R.id.balance)
        val currency: TextView = itemView.findViewById(R.id.currency)
        val progressBar: ProgressBar = itemView.findViewById(R.id.ProgressBar)


        init {
            dbRef = FirebaseDatabase.getInstance().getReference("budget")
        }

        fun deleteItem(position: Int) {
            Log.d("BudgetAdapter", "deleteItem() called at position $position")
            val budgetModel = budgetList[position]
            Log.d("BudgetAdapter", "budgetModel retrieved: $budgetModel")
            dbRef.child(budgetModel.budgetId!!).removeValue()
        }
    }
}

