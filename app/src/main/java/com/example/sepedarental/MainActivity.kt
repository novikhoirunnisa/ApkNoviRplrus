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


class MainActivity : AppCompatActivity() {
    var txtusername: EditText? = null
    var txtpassword: EditText? = null
    var btnlogin: Button? = null
    var tvregister: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvregister = findViewById<View>(R.id.tvregister) as TextView
        tvregister!!.setOnClickListener {
            //startActivity(new Intent(getApplicationContext(), Registrasi.class));
        }
        txtusername = findViewById<View>(R.id.txtusername) as EditText
        txtpassword = findViewById<View>(R.id.txtpassword) as EditText
        btnlogin = findViewById<View>(R.id.btnlogin) as Button
        btnlogin!!.setOnClickListener {
            AndroidNetworking.post("http://192.168.6.26/api/login.php")
                .addBodyParameter("email", txtusername!!.text.toString())
                .addBodyParameter("password", txtpassword!!.text.toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        try {
                            val hasil = response.getJSONObject("PAYLOAD")
                            Log.d("RBA", "url: $hasil")
                            val respon = hasil.getBoolean("respon")
                            if (respon) {
                                //Toast.makeText(MainActivity.this, "Sukses Login", Toast.LENGTH_SHORT).show();
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        MainMenu::class.java
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Gagal Login",
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
    }
}