package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val btndelete = findViewById<Button>(R.id.btn_delete_task)
        val edtitle: TextView = findViewById(R.id.detail_ed_title)
        val eddueDate: TextView = findViewById(R.id.detail_ed_due_date)
        val eddescription: TextView = findViewById(R.id.detail_ed_description)

        val taskId = intent.getIntExtra(TASK_ID, 0)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]
        viewModel.setTaskId(taskId)

        viewModel.task.observe(this) {
            it?.let {
                edtitle.text = it.title
                eddescription.text = it.description
                eddueDate.text = DateConverter.convertMillisToString(it.dueDateMillis)
            }
        }

        btndelete.setOnClickListener {
            viewModel.deleteTask()
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}