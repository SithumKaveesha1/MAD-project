package com.example.quickloan
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class nv_PaymentForm : AppCompatActivity(){
    private lateinit var    RepaymentAmount : EditText
    private lateinit var Date : EditText
    private lateinit var   PostalCode : EditText
    private lateinit var MobileNO : EditText
    private lateinit var CardNo : EditText
    private lateinit var CardHolderName : EditText
    private lateinit var ExpiryDate : EditText
    private lateinit var cvv : EditText

    private lateinit var btnDone : Button
    private  lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_payment_form)


        RepaymentAmount=findViewById(R.id.edit_text_repayment_amount)
        Date=findViewById(R.id.edit_text_payment_month)
        PostalCode=findViewById(R.id.edit_text_postalCode)
        MobileNO=findViewById(R.id.edit_text_mobileNo)
        CardNo=findViewById(R.id.edit_text_card_number)
        CardHolderName=findViewById(R.id.edit_text_card_holder_name)
        ExpiryDate=findViewById(R.id.edit_text_expiry_date)
        cvv=findViewById(R.id.edit_text_cvv)
        btnDone=findViewById(R.id.btnPayNow)

        dbRef = FirebaseDatabase.getInstance().getReference("PaymentHistory")

        btnDone.setOnClickListener {
            savPaymentDetails()
        }
    }

    private fun savPaymentDetails(){
        //getting values
        val repaymentAmount= RepaymentAmount.text.toString()
        val date= Date.text.toString()
        val postalCode= PostalCode.text.toString()
        val mobileNO= MobileNO.text.toString()
        val cardNo= CardNo.text.toString()
        val cardHolderName= CardHolderName.text.toString()
        val expiryDate= ExpiryDate.text.toString()
        val cvvs= cvv.text.toString()


        if(repaymentAmount.isEmpty()){
            RepaymentAmount.error="Please enter repayment amount"
        }
        if(date.isEmpty()){
            Date.error="Please enter date"
        }
        if(postalCode.isEmpty()){
            PostalCode.error="Please enter postal code"
        }
        if(mobileNO.isEmpty()){
            MobileNO.error="Please enter mobile number"
        }
        if (!isValidMobileNumber(mobileNO)) {
            MobileNO.error = "Mobile No should have 10 digits "
            return
        }
        if(cardNo.isEmpty()){
            CardNo.error="Please enter card number"
        }
        if (!isValidCardNumber(cardNo)) {
            CardNo.error = "Card No should have 16 digits"
            return
        }
        if(cardHolderName.isEmpty()){
            CardHolderName.error="Please enter card holder name"
        }
        if(expiryDate.isEmpty()){
            ExpiryDate.error="Please enter expiry date"
        }
        if(cvvs.isEmpty()){
            cvv.error="Please enter cvv"
        }
        if (!isValidCVV(cvvs)) {
            cvv.error = "CVV should have 3 digits"
            return
        }
        val loanID =dbRef.push().key!!
        val payment = PaymentModel(loanID,repaymentAmount,date,postalCode,mobileNO,cardNo,cardHolderName,expiryDate,cvvs)

        dbRef.child(loanID).setValue(payment)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }

    }

    private fun isValidCVV(cvvs: String): Boolean {
        val cvvRegex = Regex("^[0-9]{3}$")
        return cvvRegex.matches(cvvs)
    }

    private fun isValidCardNumber(cardNo: String): Boolean {
        val cardRegex = Regex("^[0-9]{16}$")
        return cardRegex.matches(cardNo)
    }

    private fun isValidMobileNumber(mobileNO: String): Boolean {
        val mobileRegex = Regex("^[0-9]{10}$")
        return mobileRegex.matches(mobileNO)
    }

}