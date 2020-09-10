package com.geektech.apprecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickLister {

    public RecyclerView recyclerView;
    public MainAdapter mainAdapter;
    ArrayList<Title> list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        clean();

    }

    private void init() {
        //Cоздание экземпляра некоторых классов и инициализация вьюшек
        button=findViewById(R.id.cleanBtn);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(list);
        mainAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mainAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

    }


    //Создание ItemTouchHelper для возможности удалять с помощью свайпа и перемещать строки
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int position_drag = viewHolder.getAdapterPosition();
            int position_target = target.getAdapterPosition();
            Collections.swap(mainAdapter.mData, position_drag, position_target);
            mainAdapter.notifyItemMoved(position_drag, position_target);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mainAdapter.mData.remove(viewHolder.getAdapterPosition());
            mainAdapter.notifyDataSetChanged();
        }
    };

    //Создаем переход при нажатии на один из элементов(id) RecyclerView, и возвращаем от туда измененные данные(т.е отредактированные)
    @Override
    public void onItemClick(Title title, int position) {

        Intent intent = new Intent(this, ActivityTwo.class);
        intent.putExtra("changeKey", title);
        startActivityForResult(intent, 110);

    }

    //Создаем Intent для перехода в ActivityTwo,и возвращаем от туда данные для RecyclerView
    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivityForResult(intent, 100);

    }

    //Тут мы в первом if получаем данные для RecyclerView и передаем в адаптер, в else получаем отредактированные данные полей RecyclerView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Title title = (Title) data.getSerializableExtra("title");
            mainAdapter.addApplication(title);
        } else {
            if (requestCode == 110 && resultCode == RESULT_OK) {
                Title title = (Title) data.getSerializableExtra("title");
                mainAdapter.mData.clear();
                mainAdapter.addApplication(title);
            }

        }
    }

    public void clean() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainAdapter.mData.clear();
                mainAdapter.notifyDataSetChanged();
            }
        });
    }

}