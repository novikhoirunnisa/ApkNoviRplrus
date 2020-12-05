package com.example.sepedarental

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class ListData : AppCompatActivity(), OnRefreshListener {
    private var recyclerView: RecyclerView? = null
    private var adapter: DataAdapter? = null
    private var refreshLayout: SwipeRefreshLayout? = null
    private var rentalArraylist: ArrayList<Model>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
        recyclerView = findViewById<View>(R.id.list) as RecyclerView
        refreshLayout = findViewById(R.id.swipeRefresh)
//        refreshLayout.setOnRefreshListener(this)
//        refreshLayout.post(Runnable { dataFromRemote })
    }// handle error

    // do anything with response
    private val dataFromRemote: Unit
        private get() {
            refreshLayout!!.isRefreshing = true
            AndroidNetworking.post(BaseUrl.url + "getCustomerAll.php")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        // do anything with response
                        refreshLayout!!.isRefreshing = false
                        Log.d("hasiljson", "onResponse: $response")
                        rentalArraylist = ArrayList()
                        try {
                            Log.d("hasiljson", "onResponse: $response")
                            val jsonArray = response.getJSONArray("result")
                            Log.d("hasiljson2", "onResponse: $jsonArray")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                Log.i("jsonobject", "onResponse: $jsonObject")
                                val item =
                                    Model()
                                item.setId(jsonObject.optString("id"))
                                item.setRoleuser(jsonObject.optString("roleuser"))
                                item.setEmail(jsonObject.optString("email"))
                                item.setNama(jsonObject.optString("nama"))
                                item.setNoktp(jsonObject.optString("noktp"))
                                item.setNohp(jsonObject.optString("nohp"))
                                item.setAlamat(jsonObject.optString("alamat"))
                                rentalArraylist!!.add(item)
                            }
//                            adapter = DataAdapter(rentalArraylist)
                            val layoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@ListData)
                            recyclerView!!.layoutManager = layoutManager
                            recyclerView!!.adapter = adapter
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(error: ANError) {
                        // handle error
                        refreshLayout!!.isRefreshing = false
                        Log.d("errorku", "onError errorCode : " + error.errorCode)
                        Log.d("errorku", "onError errorBody : " + error.errorBody)
                        Log.d("errorku", "onError errorDetail : " + error.errorDetail)
                    }
                })
        }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 23 && data!!.getStringExtra("refresh") != null) {
            //refresh list
            dataFromRemote
            Toast.makeText(this, "hihihihi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        dataFromRemote
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Really Exit?")
            .setMessage("Are you sure you want to exit?")
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(
                android.R.string.yes
            ) { arg0, arg1 ->
                val a = Intent(Intent.ACTION_MAIN)
                a.addCategory(Intent.CATEGORY_HOME)
                a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(a)
            }.create().show()
    }
}