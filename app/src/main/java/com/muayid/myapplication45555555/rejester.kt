package com.muayid.myapplication45555555

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_rejester.*

class rejester : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejester)

        val departclass = arrayOf("السنة الاولي", "السنة الثانية", "السنة الثالثة","السنة الرابعة")
        val departprt = arrayOf("علوم حاسوب", "تقنية المعلومات", "تكنلوجيا المعلومات")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departclass)

        val arrayAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, departprt)

        edittext5.adapter = arrayAdapter/// السنه

        edittext6.adapter = arrayAdapter2/// القسم

        bt1.setOnClickListener {
            save()

        }
        bt2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun save() {


        val number   = edittext3.text.toString().trim()
        val name     = edittext4.text.toString().trim()
        val depart   = edittext5.selectedItem.toString().trim()
        val year     = edittext6.selectedItem.toString().trim()
        val password = edittext7.text.toString().trim()

        if (number.isEmpty()){
            edittext3.error="please enter your id"

        }
        if (name.isEmpty()){
            edittext4.error="please enter your name"

        }
        if (depart.isEmpty()){
            Toast.makeText(this, "depart is not selected  ", Toast.LENGTH_LONG).show()

        }
        if (year.isEmpty ()){
            Toast.makeText(this, "year is not selected ", Toast.LENGTH_LONG).show()

        }
        if (password.isEmpty()){
            edittext7.error="please enter your semester"

        }
        if (    (!number.isEmpty()) && (!name.isEmpty())&&(!depart.isEmpty()) &&(!year.isEmpty()) &&(!password.isEmpty()) ) {
            val intent = Intent(this, MainActivity::class.java)
            addstd()
            startActivity(intent)
        }


    }

    fun addstd() {
        val number   = edittext3.text.toString().trim()
        val name     = edittext4.text.toString().trim()
        val depart   = edittext5.selectedItem.toString().trim()
        val year     = edittext6.selectedItem.toString().trim()
        val password = edittext7.text.toString().trim()


        val db = DBMmanager(this)
        val value = ContentValues()
        value.put("stdID", number)
        value.put("stdname", name)
        value.put("stdDepart", depart)
        value.put("years", year)
        value.put("stdpassword", password)
        val id = db.insertsutd(value)
        if (id > 0) {
            Toast.makeText(this, "is Rejester ", Toast.LENGTH_LONG).show()

        } else {

            Toast.makeText(this, "is not  rejester", Toast.LENGTH_LONG).show()

        }


    }


}
