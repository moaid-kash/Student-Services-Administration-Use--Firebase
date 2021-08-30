package com.muayid.myapplication45555555

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.ContactsContract
import android.widget.Toast

class DBMmanager(context: Context) {
    val dbNmar="Batta"
    val dbTablestudent ="stdinfo"
    val stdID="stdID"
    val stdname="stdname"
    val stddep="stdDepart"
    val stdyaer="years"
    val stdpassword="stdpassword"

    val dbTabinfo ="kash"
    val inID="inID"
    val img="img"
    val indep="inDepart"
    val inyaer="inyears"
    val infoDess="infoDess"
    val infotype="infotype"



    val dbV=1


    val sqlCeateIF="CREATE TABLE IF NOT EXISTS $dbTabinfo ($inID INTEGER PRIMARY KEY ,$img BLOB,$indep TEXT , $inyaer TEXT , $infoDess TEXT ,$infotype TEXT);"

    val sqlCeate="CREATE TABLE IF NOT EXISTS $dbTablestudent ($stdID TEXT PRIMARY KEY ,$stdname TEXT,$stddep TEXT , $stdyaer TEXT , $stdpassword TEXT );"

    var sqlBD: SQLiteDatabase?=null

    init {
        val db=DatabsseNotes(context)
        sqlBD =db .writableDatabase
    }



    inner class DatabsseNotes(context: Context) : SQLiteOpenHelper(context, dbNmar, null, dbV) {
        override fun onCreate(p0: SQLiteDatabase?) {
            Toast .makeText(context,"Databaes is cretad  ",Toast .LENGTH_LONG).show()

                      p0!!.execSQL(sqlCeate)
                      p0.execSQL(sqlCeateIF)


         }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS $dbTablestudent")

          }


        var context:Context?= context


    }


    fun insertsutd(valuer:ContentValues):Long{
        val id=sqlBD !!.insert (dbTablestudent ,null,valuer)

       return id

    }

    fun insertInfo(valuer:ContentValues):Long{
        val id=sqlBD !!.insert (dbTabinfo ,null,valuer)

        return id

    }

    fun query(P:Array<String>,selec:String,seArgs:Array<String>,sort:String ):Cursor{

        val db =SQLiteQueryBuilder()
        db.tables=dbTablestudent
        val cursor=db.query(sqlBD,P,selec ,seArgs,null,null,sort)
        return cursor

    }

    fun queryIOf(P:Array<String>,selec:String,seArgs:Array<String>,sort:String ):Cursor{

        val db =SQLiteQueryBuilder()
        db.tables=dbTabinfo
        val cursor=db.query(sqlBD,P,selec ,seArgs,null,null,sort)
        return cursor

    }


    fun delete(selec: String,seArgs: Array<String>):Int{
        val cont =sqlBD!!.delete(dbTablestudent,selec,seArgs)
        return cont



    }


    fun deleteIf(selec: String,seArgs: Array<String>):Int{
        val cont =sqlBD!!.delete(dbTabinfo,selec,seArgs)
        return cont



    }
}

