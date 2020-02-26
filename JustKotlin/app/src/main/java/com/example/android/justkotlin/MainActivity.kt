package com.example.android.justkotlin

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var quantity: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incrementButton.setOnClickListener {
            increment()
        }

        decrementButton.setOnClickListener {
            decrement()
        }
        submitOrderButton.setOnClickListener {
            submitOrder()
        }

    }

    /**
     * This method is called when the order button is clicked.
     */
    private fun submitOrder() {
        //name Text Field
        val nameEditText: EditText = findViewById(R.id.nameField)
        val hasName: String = nameEditText.text.toString()
        //whippedCream CheckBox
        val whippedCreamCheckBox: CheckBox = findViewById(R.id.whippedCreamCheckBox)
        val hasWhippedCream: Boolean = whippedCreamCheckBox.isChecked
        //chocolate CheckBox
        val chocolateCheckBox: CheckBox = findViewById(R.id.chocolateCheckBox)
        val hasChocolate: Boolean = chocolateCheckBox.isChecked

        val price: Int = calculatePrice(hasWhippedCream,hasChocolate)
        val priceMessage: String = createOrderSummary(price,hasWhippedCream,hasChocolate, hasName)


        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT,getString(R.string.email_subject) + hasName )
            putExtra(EXTRA_TEXT, priceMessage )
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    private fun calculatePrice(addWhippedCream: Boolean,
                               addChocolate: Boolean): Int {
        var basePrice = 5
        if (addWhippedCream){
            basePrice += 1
        }
        if (addChocolate){
            basePrice += 2
        }

        return quantity * basePrice
    }

    private fun createOrderSummary(price: Int,addWhippedCream: Boolean,
                                  addChocolate: Boolean,name: String): String{
        var priceMessage = getString(R.string.order_summary_name,name)
        priceMessage += "\n" + getString(R.string.add_whipped_cream) +addWhippedCream
        priceMessage += "\n" + getString(R.string.add_chocolate) + addChocolate
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity
        priceMessage += "\n" + getString(R.string.total) + ": " + price
        priceMessage += "\n" + getString(R.string.thank_you)
        return priceMessage
    }


    private fun increment(){
        if(quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show()
            return
        }
        quantity += 1
        display(quantity)

    }

    private fun decrement(){
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show()
            return
        }
        quantity -= 1
        display(quantity)
    }


    private fun display(number: Int){
        quantityTextView.text = " " + number
    }


}
