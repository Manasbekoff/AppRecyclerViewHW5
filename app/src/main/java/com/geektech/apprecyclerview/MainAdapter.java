package com.geektech.apprecyclerview;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    public ArrayList<Title> mData;

    public ItemClickLister listener;

    //Создаем конструктор для списка
    public MainAdapter(ArrayList<Title> mData) {
        this.mData = mData;
    }

    //Cоздали метод который добавляет элементы в наш ArrayList
    public void addApplication(Title title){
        mData.add(title);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //Это тот момент, когда создаётся layout строки списка, передается объекту ViewHolder
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview,parent,false);
        return new MainViewHolder(view);
    }

    //принимает объект ViewHolder и устанавливает необходимые данные из onBind для соответствующей строки во view-компоненте
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.onBind(mData.get(position));

    }

    //Возвращаем общее количество элементов списка
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView mark;
        TextView year;
        ImageView imageView;

        Title title;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            mark = itemView.findViewById(R.id.txtMark);
            year = itemView.findViewById(R.id.txtYear);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(this);



        }

        //Передаем нашим вьюшкам соответствующие поля в нашей модельке
        void onBind(Title title){
            this.title = title;
            name.setText(title.name);
            mark.setText(title.mark);
            imageView.setImageURI(Uri.parse(title.imageView));
            year.setText(title.year);
        }


        @Override
        //Добавили слушатель который при клике возвращает позиции адаптера
        public void onClick(View view) {
            if (listener != null)
                listener.onItemClick(title,getAdapterPosition());
        }
    }

    public void setOnClickListener(ItemClickLister mListener){
        this.listener = mListener;
    }

    public interface ItemClickLister {

        void onItemClick(Title title,int position);
    }
}


