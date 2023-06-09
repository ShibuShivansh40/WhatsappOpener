package com.example.whatsappopener

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var number : String = "0"

        if(intent.action == Intent.ACTION_PROCESS_TEXT){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
            }
        }
        if(number.isDigitsOnly()){
            startWhatsapp(number)
        }
        else{
            Toast.makeText(this,"Please check the number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startWhatsapp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        val data: String = if(number[0] == '+'){
           number.substring(1)
        }
        else if(number.length == 10){
            "91$number"
        }
        else{
            number
        }
        intent.data = Uri.parse("https://wa.me/$data")
        if(packageManager.resolveActivity(intent,0) != null){
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Please install Whatsapp", Toast.LENGTH_SHORT).show()
        }
    }
}