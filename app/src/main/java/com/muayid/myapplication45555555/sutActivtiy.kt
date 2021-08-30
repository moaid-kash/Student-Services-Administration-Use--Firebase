package com.muayid.myapplication45555555

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sut_activtiy.*
import kotlinx.android.synthetic.main.webvi.view.*

class sutActivtiy : AppCompatActivity() {
    var stdclass:String = ""
    lateinit var listN: MutableList<data_img>

     var stdyrss:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sut_activtiy)

        val depart= arrayOf("المحاضرات","النتائج","الجداول")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,depart)
        showall.adapter =arrayAdapter


        stdclass=intent.getStringExtra("classs")
        stdyrss=intent.getStringExtra("yeaaay")

        button8.setOnClickListener {

            Diplay(stdclass,stdyrss,showall.selectedItem.toString())


            }

    }



    fun Diplay(cls:String,sy:String,tp:String){


        val pc = ProgressDialog(this)
        pc.setTitle("Uploading......!")
        pc .show()
        val mydata =
            FirebaseDatabase.getInstance("https://myapplication45555555-default-rtdb.firebaseio.com/")
                .getReference(stdclass+"_"+stdyrss+"_"+showall.selectedItem.toString())

        listN= mutableListOf()



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                //  val post = dataSnapshot.getValue()
                //Toast.makeText(this@lis, "seave", Toast.LENGTH_SHORT).show()
                if (dataSnapshot.exists()) {
                    listN.clear()

                    for (e in dataSnapshot.children) {
                        val d = e.getValue(data_img::class.java)
                        if(d!!.uri != "") {
                            listN.add(d)
                        }

                    }

                    pc.dismiss()
                    var myAdapter = MyNotesAdpater(this@sutActivtiy, listN)
                    display.adapter = myAdapter
                    // .
                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException())
                // ...
            }


        }


        mydata.addValueEventListener(postListener)
    }








    inner class MyNotesAdpater: BaseAdapter {


        @SuppressLint("ViewHolder", "SetJavaScriptEnabled", "InflateParams")
         override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView= this@sutActivtiy.layoutInflater.inflate(R.layout .webvi  ,null)
            val note=listN [p0]


            ///mval u=URL(note.uri)

            myView.tet.webChromeClient= WebChromeClient()

            myView.tet.loadUrl(note.uri!!.toString())
            val   pass= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

             // myView.tet.settings.pluginState(WebSettings.PluginState.ON)
            myView.tet.settings.javaScriptEnabled=true
            myView.tet.settings.loadWithOverviewMode=true
            myView.tet.settings.useWideViewPort=true
            // Glide.with(context).load(upload.getUrl()).into(holder.imageView);
            //  val inputStream=contentResolver.openInputStream(t)
            // val bitmap = BitmapFactory.decodeStream(inputStream)
            //   myView.imageView3.setImageBitmap(bitmap)
            //myView.textView3.setOnClickListener {




            // Glide.with(this@Display)
            //  .load(ste)
            // .into(myView. imageView3)


            // myView. imageView3.setImageURI(t)






            return myView


        }

        override fun getItem(p0: Int): Any {
            return listN [p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listN.size

            return 1
        }

        var listN: MutableList<data_img>



        var content: Context?=null
        constructor(content: Context, listN: MutableList<data_img>):super()
        {
            this.content=content
            this.listN=listN


        }



    }




}
