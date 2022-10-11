package com.example.praktika11

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataOutputActivity : AppCompatActivity() {

    private val Task : MutableList<Tasks> = mutableListOf()
    private lateinit var rv: RecyclerView
    var indexChange = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_output)
        getTask()
        val adapter = TaskRVAdapter(this,Task)

        val rvListener = object : TaskRVAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent (this@DataOutputActivity, DataEntryActivity:: class.java)
                intent.putExtra("index",position)
                indexChange = position
                startActivity(intent)
            }

            override fun onItemLongClick(view: View?, position: Int) {
                Task.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        adapter.setOnClickListener(rvListener)
        rv = findViewById(R.id.resultRecyclerView)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        Task.forEach{
            Log.d("result",it.toString())
        }
    }

    private fun getTask(){
        val preferences = getSharedPreferences("str", MODE_PRIVATE)
        var json = ""
        if (!preferences.contains("str")){
            return
        }
        else {
            json = preferences.getString("str","not_json").toString()
        }
        val taskList = Gson().fromJson<List<Tasks>>(json, object : TypeToken<List<Tasks>>(){}.type)
        Task.addAll(taskList)
    }

    override fun onResume() {
        super.onResume()
        if (indexChange != -1) {
            Task.clear()
            getTask()
            rv.adapter?.notifyItemChanged(indexChange)
        }
    }


//    private fun  performContextMenu (position: Int){
//        val popupMenu = PopupMenu(this,)
//    }
}