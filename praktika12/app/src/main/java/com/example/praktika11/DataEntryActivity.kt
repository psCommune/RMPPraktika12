package com.example.praktika11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataEntryActivity : AppCompatActivity() {
    val Task : MutableList<Tasks> = mutableListOf()
    private lateinit var btnNT: Button
    //private lateinit var btn: Button
    private lateinit var etH: EditText
    private lateinit var etM: EditText
    private lateinit var etDT: EditText
    //private lateinit var tw: TextView
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)
        btnNT = findViewById(R.id.button3)
        //btn = findViewById(R.id.button2)
        etH = findViewById(R.id.editTextTextPersonName)
        etM = findViewById(R.id.editTextTextPersonName2)
        etDT = findViewById(R.id.editTextTime)
        //tw = findViewById(R.id.textView3)
        val preferences = getSharedPreferences("str",MODE_PRIVATE)
        getTask()
        index = intent.getIntExtra("index",-1)

        if (index != -1){
            btnNT.setText("Изменить контакт")
            etH.setText(Task[index].heading)
            etM.setText(Task[index].meaning)
            etDT.setText(Task[index].time)
        }
        btnNT.setOnClickListener {
            if (etH.text.toString()!=""&&etM.text.toString()!=""&&etDT.text.toString()!=""){
                var HeadingTask = etH.text.toString()
                var DescriptionTask = etM.text.toString()
                var DataTime = etDT.text.toString()
                if (index == -1){
                    addTask(etH.text.toString(),etM.text.toString(), etDT.text.toString())
                }
                else {
                    Task[index].heading = etH.text.toString()
                    Task[index].meaning = etM.text.toString()
                    Task[index].time = etDT.text.toString()
                    preferences.edit{
                        this.putString("str", Gson().toJson(Task).toString())
                    }
                }
                val task = Tasks(HeadingTask, DescriptionTask, DataTime)
                etDT.text.clear()
                etM.text.clear()
                etH.text.clear()
                Toast.makeText(this, "Сохранена задача: " + task.toString(), Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"Не все поля заполнены", Toast.LENGTH_SHORT).show()
            }
        }
        btnNT.setOnLongClickListener {
            val intent = Intent (this, DataOutputActivity:: class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }
//        btn.setOnClickListener {
//
//        }
    }

    private fun getTask(){
        val preferences = getSharedPreferences("str",MODE_PRIVATE)
        var json = ""
        if (!preferences.contains("str")){
            return
        }
        else {
            json = preferences.getString("str","Not_str").toString()
        }
        val taskList = Gson().fromJson<List<Tasks>>(json,object :TypeToken<List<Tasks>>(){}.type)
        Task.addAll(taskList)
    }

    private fun addTask(heading:String, meaning:String, time:String){
        val task = Tasks(heading, meaning, time)
        Task.add(task)
        val preferences = getSharedPreferences("str", MODE_PRIVATE)
        preferences.edit{
            this.putString("str", Gson().toJson(Task).toString())
        }
    }

}