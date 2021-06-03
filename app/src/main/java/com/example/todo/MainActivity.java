package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        //Add click listener to list element
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get clicked item
                ToDoModel todo = toDos.get(position);

                //Dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(todo.getTitle());
                builder.setMessage(todo.getDescription());

                //Buttons
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbh.deleteItem(todo.getId());
                        Toast.makeText(getApplicationContext(), "ToDo Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });

                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        long time = System.currentTimeMillis();
                        //Update status
                        dbh.updateFinished(todo.getId(), time);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });


                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      Intent intent = new Intent(MainActivity.this, AddToDO.class);
                      intent.putExtra("id", todo.getId());
                      intent.putExtra("title", todo.getTitle());
                      intent.putExtra("description", todo.getDescription());
                      intent.putExtra("finished", todo.getFinished());
                      startActivity(intent);

                    }
                });

                builder.show();
            }
        });
    }
}