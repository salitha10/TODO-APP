package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDoModel> {

    private Context context;
    private int resource;
    List<ToDoModel> todos;

    public ToDoAdapter(@NonNull Context context, int resource, @NonNull List<ToDoModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.todos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Inflate card view
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource, parent, false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.txtDescription);
        ImageView img = row.findViewById(R.id.img_done);
        img.setVisibility(View.INVISIBLE);

        //Set data in card view
        ToDoModel todo = todos.get(position);
        title.setText(todo.getTitle());
        description.setText(todo.getDescription());

        //Show done image
        if(todo.getFinished() > 0){
            img.setVisibility(View.VISIBLE);
        }

        return row;
    }
}
