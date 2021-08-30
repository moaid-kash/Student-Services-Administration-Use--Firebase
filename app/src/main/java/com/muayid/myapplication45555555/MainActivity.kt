package com.muayid.myapplication45555555

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var stdclass:String = ""
    var stdyrss:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            save()

        }

        buttonn.setOnClickListener {
            val intent = Intent(this, rejester::class.java)
            startActivity(intent)
        }
    }

    fun save(){


        if ((edittext2.text.toString().equals("admin")) && (edittext.text.toString() .equals("123456"))) {
            val intent = Intent(this, mangement::class.java)
            startActivity(intent)

        }else

            if( queryTitle(edittext2.text.toString(),edittext.text.toString())==2){

                val intent = Intent(this, sutActivtiy::class.java)
                intent.putExtra("classs",stdyrss)
                intent.putExtra("yeaaay",stdclass)

                startActivity(intent)

            }else

                if( queryTitle(edittext2.text.toString(),edittext.text .toString())==1) {

                    edittext.error = "check password"
                }
                else

                    if( queryTitle(edittext2.text.toString(),edittext.text.toString())==0) {


                        edittext2.error = "check user name"
                        edittext.error = "check password"
                    }


                    else
                        if ((!edittext2.text .equals("admin")) && (!edittext.text.equals("123456"))) {
                            queryTitle(edittext2.text.toString(),edittext.text.toString())

                            edittext2.error = "check user name"
                            edittext.error = "check password"
                            return
                        }
    }





        fun queryTitle(tilt: String,pass:String) :Int {
            var dbMmanager = DBMmanager(this)
            val p = arrayOf("stdID", "stdpassword","years","stdDepart")
            val selec = arrayOf(tilt)
            var cursor = dbMmanager.query(p, " stdID like ?", selec, "stdID")
            if (cursor.moveToFirst()) {

                do {
                    val spass = cursor.getString(cursor.getColumnIndex("stdpassword"))

                    stdyrss= cursor.getString(cursor.getColumnIndex("years"))
                    stdclass= cursor.getString(cursor.getColumnIndex("stdDepart"))
                    if(pass == spass) {
                        return 2
                    }
                    return 1
                } while (cursor.moveToNext())

            }

            return 0

        }



}
