package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView todoList;
    private TextView countTxt;
    private Button addTodo;
    private List<ToDoModel> toDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = (ListView)findViewById(R.id.listViewToDo);
        countTxt = (TextView)findViewById(R.id.txtCount);
        addTodo = (Button)findViewById(R.id.btnAdd);
        toDos = new ArrayList<>();

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to next activity
                startActivity(new Intent(getApplicationContext(), AddToDO.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHandler dbh = new DBHandler(getApplicationContext());

        //Set count
        int count = dbh.getCountTODO();
        countTxt.setText("You have " + count + " TODOs");

        //Save list
        toDos = dbh.getAllToDos();

        //Set adapter to list
        ToDoAdapter toDoAdapter = new ToDoAdapter(getApplicationContext(), R.layout.todo_cardview, toDos);
        todoList.setAdapter(toDoAdapter);
    }
}