package com.example.apimethod

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableReplay.observeOn
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val todoListView=findViewById<ListView>(R.id.todolistView)
        val postbutton = findViewById<Button>(R.id.todobtn)
        postbutton.setOnClickListener{

            val retrofit= Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build().create(ApiInterface::class.java).getToDoList()

            retrofit.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    todoListView.adapter=todosAdapter(it,this)

                    Toast.makeText(this, "${it[0].id}", Toast.LENGTH_SHORT).show()
                }
        }


        val editid=findViewById<EditText>(R.id.idedit)
        val edituserid=findViewById<EditText>(R.id.useridedit)
        val edittitle=findViewById<EditText>(R.id.titleedit)
        val todos = findViewById<Button>(R.id.postbtn)
        todos.setOnClickListener {

            val editidtext=Integer.parseInt(editid.text.toString())
            val edituserid=Integer.parseInt(edituserid.text.toString())
            val edittitletext=edittitle.text.toString()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build().create(ApiInterface::class.java).postToDoList(TodoData(edituserid,editidtext,edittitletext))
            retrofit.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Toast.makeText(this, "${it.id} , ${it.title},${it.userId}", Toast.LENGTH_SHORT).show()
                }
        }


        val putbutton = findViewById<Button>(R.id.putbtn)
        putbutton.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build().create(ApiInterface::class.java).putToDoList(TodoData(89,8,"hello",true),1)
            retrofit.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Toast.makeText(this, "${it.id},${it.userId},${it.title},${it.completed} ", Toast.LENGTH_SHORT).show()
                }

        }


        val patchbutton = findViewById<Button>(R.id.patchbtn)
        patchbutton.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()
                .create(ApiInterface::class.java)

            retrofit.patchToDoList(TodoData(88,5,"hello",true),1,)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Toast.makeText(this, "${it.userId},${it.id},${it.title},${it.completed}", Toast.LENGTH_SHORT).show()
                }


        }


        val deletedbutton=findViewById<Button>(R.id.deletedbtn)
        deletedbutton.setOnClickListener{

            val retrofit= Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build().create(ApiInterface::class.java)

            retrofit.deletepost(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {

                    Toast.makeText(this, "post Deleted successfully", Toast.LENGTH_SHORT).show()
                }
        }




    }
}