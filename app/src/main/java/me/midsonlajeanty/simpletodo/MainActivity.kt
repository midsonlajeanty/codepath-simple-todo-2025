package me.midsonlajeanty.simpletodo

import android.annotation.SuppressLint
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    val FILE_NAME = "data.txt"
    var listOfTasks = mutableListOf<String>()

    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, ime.bottom)
            insets
        }

        val onLongClickListener = object  : TaskItemAdapter.OnLongClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemLongClicked(position: Int) {
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle(getString(R.string.delete_task))
                    .setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_task))
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        listOfTasks.removeAt(position)
                        adapter.notifyDataSetChanged()
                        saveFile()
                    }
                    .show()
            }
        }

        loadFile()

        val listOfTasksView = findViewById<RecyclerView>(R.id.tasksRecyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        listOfTasksView.adapter = adapter
        listOfTasksView.layoutManager = LinearLayoutManager(this)

        val btn = findViewById<Button>(R.id.addButton)
        val addTextEdit = findViewById<EditText>(R.id.addTaskField)

        btn.setOnClickListener{
            val userInput = addTextEdit.text.toString()

            if (userInput != "") {
                listOfTasks.add(userInput)
                adapter.notifyItemInserted(listOfTasks.size - 1)
                addTextEdit.setText("")
                saveFile()
            }
        }
    }

    private fun getFile() : File {
        return  File(filesDir, FILE_NAME)
    }

    fun loadFile(){
        try {
            listOfTasks = FileUtils.readLines(getFile(), Charset.defaultCharset())
        }catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }

    fun saveFile(){
        try {
            FileUtils.writeLines(getFile(), listOfTasks)
        }catch (ioException : IOException){
            ioException.printStackTrace()
        }
    }
}