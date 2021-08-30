package com.muayid.myapplication45555555

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mange.*
import java.io.ByteArrayOutputStream

class mange : AppCompatActivity() {
    val  REQUEST_CODE_GALLERY =999
    val   pass= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    var stdclass:String = ""

     var u:Uri? = null
    var stdyrss:String=""

    var tp="video/*"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mange)

        stdclass=intent.getStringExtra("class")
        stdyrss=intent.getStringExtra("yerss")

        val depart= arrayOf("المحاضرات","النتائج","الجداول")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,depart)
        spinner2.adapter =arrayAdapter

var te=0
        imageView4 .setOnClickListener {
             if(spinner2.selectedItem.toString().equals("المحاضرات"))

                {
                    tp="video/*"
                }
            else
             {
                 tp="image/*"
             }


            ActivityCompat.requestPermissions(
                this,
                pass,
                REQUEST_CODE_GALLERY
            )

           te=1

        }


        button2 .setOnClickListener {

            if (editText.text.toString().equals("")){
                editText.error="الرجاءادخال الوصف ......!!"

            }else
           if(te==0){

               textView.error="الرجاءادخال الصوره ......!!"

           }
            else{
               updel(u)
                       //Toast.makeText(this,"added secssfully",Toast.LENGTH_LONG).show()

               //////////////////////////////

           }


            }

        button4.setOnClickListener {
val atll=stdclass+"_"+stdyrss+"_"+spinner2.selectedItem.toString()
     val  ph=stdclass+"/"+stdyrss+"/"+spinner2.selectedItem.toString()
            val intent = Intent(this, displayInof::class.java)
            intent.putExtra("all", atll)

            intent.putExtra("phon", ph)

            intent.putExtra("tyy", tp)


            startActivity(intent)

        }
        button6.setOnClickListener {
            val intent = Intent(this, mangement::class.java)
            startActivity(intent)
        }

    }


    fun img(imag : ImageView): ByteArray {

        val bitmap=imag.drawable.toBitmap()
        val setram= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,setram)
        val btArr=setram.toByteArray()

        return btArr

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode ==REQUEST_CODE_GALLERY)
        {
            if(grantResults.size > 0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){

                val intent=Intent(Intent.ACTION_PICK)
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
        if(requestCode ==REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data!=null) {

            val ut=data.data

            val inputStream=contentResolver.openInputStream(ut!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView4 .setImageBitmap(bitmap)

            u=ut
            //updel(u)

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
        val re = mStorageRef.child(stdclass+"/"+stdyrss+"/"+spinner2.selectedItem.toString()+"/" + System.currentTimeMillis() +"."+ getfileRExt(selectedPdfFromStorage))



        re.putFile(selectedPdfFromStorage!!)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot>
                { taskSnapshot ->
                    //  progressDialog.dismiss()
                    val mydata =
                        FirebaseDatabase.getInstance("https://myapplication45555555-default-rtdb.firebaseio.com/")
                            .getReference(stdclass+"_"+stdyrss+"_"+spinner2.selectedItem.toString())



                    var u=taskSnapshot.storage.downloadUrl
                    while (!u.isComplete) {
                    }

                    val ur: Uri? = u.result
                    val eID = mydata.push().key
                    val d = data_img(eID, ur.toString())
                    pc.dismiss()



                    Toast.makeText(this, " ADD SUCCESSFUL ......!!", Toast.LENGTH_LONG ).show()
                    mydata.child(eID!!).setValue(d).addOnCompleteListener {

                    }

                })


    }

}
