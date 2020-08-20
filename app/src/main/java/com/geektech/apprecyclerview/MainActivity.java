package com.geektech.apprecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickLister {

    public RecyclerView recyclerView;
    public MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        ArrayList <String> newsToday = new ArrayList<>();
        newsToday.add("Привет ");
        newsToday.add("Понедельник ");
        newsToday.add("Вторник ");
        newsToday.add("Среда ");
        newsToday.add("Четверг");
        newsToday.add("Привет ");
        newsToday.add("Привет ");
        newsToday.add("Привет ");
        newsToday.add("Привет ");
        newsToday.add("Привет ");

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(newsToday);
        mainAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mainAdapter);
        new ItemTouchHelper(simpleCallback);

    }

       ItemTouchHelper.SimpleCallback simpleCallback =  new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN , ItemTouchHelper.RIGHT) {

           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               int position_drag = viewHolder.getAdapterPosition();
               int position_target = target.getAdapterPosition();
               Collections.swap(mainAdapter.list,position_drag, position_target);
               mainAdapter.notifyItemMoved(position_drag,position_target);
               return true;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mainAdapter.list.remove(viewHolder.getAdapterPosition());
            mainAdapter.notifyDataSetChanged();
           }
       };


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "You clicked " + position,Toast.LENGTH_LONG).show();
    }
}