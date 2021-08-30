package com.muayid.myapplication45555555

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mangement.*

class mangement : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mangement)



        bt2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bt3.setOnClickListener {


            val intent = Intent(this, mange::class.java)
            intent.putExtra("class",spinner.selectedItem.toString() )
            intent.putExtra("yerss",spinnerr.selectedItem.toString() )

            startActivity(intent)
        }

        val depart = arrayOf("علوم حاسوب", "تقنية المعلومات", "تكنلوجيا المعلومات")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, depart)
        spinner.adapter = arrayAdapter

        val departt = arrayOf("السنة الاولي", "السنة الثانية", "السنة الثالثة","السنة الرابعة")
        val arrayAdapterr = ArrayAdapter(this, android.R.layout.simple_spinner_item, departt)
        spinnerr.adapter = arrayAdapterr




    }
    }
