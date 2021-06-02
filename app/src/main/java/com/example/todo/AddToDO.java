package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class AddToDO extends AppCompatActivity {

    //Declare variables
    private Button addNew;
    private EditText title, description;
    DBHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        //Init variables
        addNew = (Button) findViewById(R.id.btnAddNew);
        title = (EditText) findViewById(R.id.editTextTitle);
        description = (EditText) findViewById(R.id.editTextDescription);
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

                if (toDotitle.isEmpty() || todoDes.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    long startDate = System.currentTimeMillis();

                    //Model
                    ToDoModel toDo = new ToDoModel(toDotitle, todoDes, startDate, 0);

                    //Call insert data function
                    dbh = new DBHandler(getApplicationContext());

                    dbh.addToDo(toDo);
                    Toast.makeText(getApplicationContext(), "TODO saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}