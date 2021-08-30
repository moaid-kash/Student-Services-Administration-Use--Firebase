package com.muayid.myapplication45555555


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.webkit.WebChromeClient
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_display_inof.*
import kotlinx.android.synthetic.main.ylylyl.view.*

class displayInof : AppCompatActivity() {
    var All:String=""
    lateinit var im:data_img
var ph:String=""
    var tp=""
    lateinit var listN: MutableList<data_img>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_inof)

        All=intent.getStringExtra("all")
        ph=intent.getStringExtra("phon")
        tp=intent.getStringExtra("tyy")

        Diplay("","","")




    }



    fun Diplay(cls:String,sy:String,tp:String){


        val pc =ProgressDialog(this)
        pc.setTitle("Uploading......!")
        pc .show()
        val mydata =
            FirebaseDatabase.getInstance("https://myapplication45555555-default-rtdb.firebaseio.com/")
                .getReference(All)

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

                    var myAdapter = MyNotesAdpater(this@displayInof, listN)
                    kashDisplay.adapter = myAdapter
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
            val myView= this@displayInof.layoutInflater.inflate(R.layout .ylylyl  ,null)
            val note=listN [p0]


            ///mval u=URL(note.uri)

            myView.wV.webChromeClient= WebChromeClient()

            myView.wV.loadUrl(note.uri!!.toString())
            val   pass= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            // myView.tet.settings.pluginState(WebSettings.PluginState.ON)
            myView.wV.settings.javaScriptEnabled=true
            myView.wV.settings.loadWithOverviewMode=true
            myView.wV.settings.useWideViewPort=true
            // Glide.with(context).load(upload.getUrl()).into(holder.imageView);
            //  val inputStream=contentResolver.openInputStream(t)
            // val bitmap = BitmapFactory.decodeStream(inputStream)
            //   myView.imageView3.setImageBitmap(bitmap)
            //myView.textView3.setOnClickListener {


        myView.button10.setOnClickListener {


              FirebaseDatabase.getInstance("https://myapplication45555555-default-rtdb.firebaseio.com/")
                  .getReference(All).child(note.id!!).child("uri").setValue("").addOnCanceledListener {

              Toast.makeText(this@displayInof, "seave", Toast.LENGTH_SHORT).show()


         }


            }


            myView.button9.setOnClickListener {
                im=note

                ActivityCompat.requestPermissions(
                    this@displayInof,
                    pass,
                    999
                )

            }

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








    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode ==999)
        {
            if(grantResults.size > 0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){

                val intent= Intent(Intent.ACTION_PICK)
                intent.setType(tp)
                startActivityForResult(intent,999)


            }
            else
            {
                Toast.makeText(this,"die", Toast.LENGTH_LONG).show()

            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode ==999 && resultCode == RESULT_OK && data!=null) {

            val ut=data.data

            val inputStream=contentResolver.openInputStream(ut!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
//            image.setImageBitmap(bitmap)

            updel(ut)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }









    fun getfileRExt(uri: Uri?): String? {
        val cr=contentResolver
        val mi= MimeTypeMap.getSingleton()
        return mi.getExtensionFromMimeType(cr.getType(uri!!))

    }

    private fun updel(selectedPdfFromStorage: Uri?) {

        val pc = ProgressDialog(this)
        pc.setTitle("loading......!")
        pc .show()

        val mStorageRef = FirebaseStorage.getInstance().getReference()
        val re = mStorageRef.child(ph+"/" + System.currentTimeMillis() +"."+ getfileRExt(selectedPdfFromStorage))



        re.putFile(selectedPdfFromStorage!!)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot>
                { taskSnapshot ->
                    //  progressDialog.dismiss()

                    val mydata =
                        FirebaseDatabase.getInstance("https://myapplication45555555-default-rtdb.firebaseio.com/")
                            .getReference(All)



                    var u=taskSnapshot.storage.downloadUrl
                    while (!u.isComplete) {
                    }

                    val ur: Uri? = u.result

                    val d = data_img(im.id ,ur.toString())


                    pc.dismiss()
                    Toast.makeText(this, " THIS UPDATE ......!!", Toast.LENGTH_LONG ).show()
                    mydata.child(im.id!!).child("uri").setValue(d.uri!!).addOnCompleteListener {


                    }

                })


    }

    fun bb(view: View) {
        val intent = Intent(this, mange::class.java)
        startActivity(intent)
    }


}
