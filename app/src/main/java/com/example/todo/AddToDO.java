package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class AddToDO extends AppCompatActivity {

    //Declare variables
    private Button addNew;
    private EditText title, description;
    TextView label;
    DBHandler dbh;

    //Variables
    int id = -1;
    long finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        //Init variables
        addNew = (Button) findViewById(R.id.btnAddNew);
        title = (EditText) findViewById(R.id.editTextTitle);
        description = (EditText) findViewById(R.id.editTextDescription);
        label = (TextView) findViewById(R.id.txtTitle);

        //Get data from main activity
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        Log.d("id_new", String.valueOf(id));

        if(id != -1){
            addNew.setText("Update ToDo");
            label.setText("Update ToDo");
        }

        finished = intent.getLongExtra("finished", 0);
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));

    }

    @Override
    protected void onResume() {
        super.onResume();

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Save data
                String toDotitle = title.getText().toString();
                String todoDes = description.getText().toString();

                Log.d("Title", toDotitle);

                if (toDotitle.isEmpty() || todoDes.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    long startDate = System.currentTimeMillis();

                    //Call insert data function
                    dbh = new DBHandler(getApplicationContext());

                    //If update item
                    if(id != -1){
                        //Model
                        ToDoModel toDo = new ToDoModel(id, toDotitle, todoDes, startDate, finished);
                        dbh.updateItem(toDo);
                        Toast.makeText(getApplicationContext(), "TODO Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ToDoModel toDo = new ToDoModel(toDotitle, todoDes, startDate, 0);
                        dbh.addToDo(toDo);
                        Toast.makeText(getApplicationContext(), "TODO saved successfully", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                }
            }
        });
    }
}