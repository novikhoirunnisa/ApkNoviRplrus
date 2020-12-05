package com.example.sepedarental

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject


class Registrasi : AppCompatActivity() {
    var txtemail: EditText? = null
    var txtnamalengkap: EditText? = null
    var txtpassword: EditText? = null
    var txtcpassword: EditText? = null
    var txtnohp: EditText? = null
    var txtnoktp: EditText? = null
    var txtalamat: EditText? = null
    var btnregister: Button? = null
    var tvlogin: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)
        txtemail = findViewById<View>(R.id.txtemail) as EditText
        txtnamalengkap = findViewById<View>(R.id.txtnamalengkap) as EditText
        txtpassword = findViewById<View>(R.id.txtpassword) as EditText
        txtcpassword = findViewById<View>(R.id.txtcpassword) as EditText
        txtnohp = findViewById<View>(R.id.txtnohp) as EditText
        txtalamat = findViewById<View>(R.id.txtalamat) as EditText
        txtnoktp = findViewById<View>(R.id.txtnoktp) as EditText
        btnregister = findViewById<View>(R.id.btnregister) as Button
        btnregister!!.setOnClickListener {
            AndroidNetworking.post("http://192.168.6.24/sepeda/registrasi.php")
                .addBodyParameter("noktp", txtnoktp!!.text.toString())
                .addBodyParameter("email", txtemail!!.text.toString())
                .addBodyParameter("password", txtpassword!!.text.toString())
                .addBodyParameter("nama", txtnamalengkap!!.text.toString())
                .addBodyParameter("nohp", txtnoktp!!.text.toString())
                .addBodyParameter("alamat", txtalamat!!.text.toString())
                .addBodyParameter("roleuser", "2")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            val hasil = response.getJSONObject("hasil")
                            Log.d("RBA", "url: $hasil")
                            val respon = hasil.getBoolean("respon")
                            if (respon) {
                                Toast.makeText(
                                    this@Registrasi,
                                    "Sukses Registrasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        MainMenu::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this@Registrasi,
                                    "Gagal Registrasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(error: ANError) {
                        // handle error
                        Log.d("RBA", "onError: " + error.errorBody)
                        Log.d("RBA", "onError: " + error.localizedMessage)
                        Log.d("RBA", "onError: " + error.errorDetail)
                        Log.d("RBA", "onError: " + error.response)
                        Log.d("RBA", "onError: " + error.errorCode)
                    }
                })
        }
        tvlogin = findViewById<View>(R.id.tvlogin) as TextView
        tvlogin!!.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}