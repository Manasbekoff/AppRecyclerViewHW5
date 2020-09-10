package com.geektech.apprecyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.media.CamcorderProfile.get;

public class ActivityTwo extends AppCompatActivity {

    public EditText etName;
    public EditText etMark;
    public EditText etYear;
    public Button date;
    public Button save;
    public ImageView imageView;
    public Date dateOf;
    public Uri imageDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        init();




    }

    private void init() {
        //init view's
        etName = findViewById(R.id.etName);
        etMark = findViewById(R.id.etMark);
        etYear = findViewById(R.id.etYear);
        date = findViewById(R.id.btnDate);
        save = findViewById(R.id.btnSave);
        imageView = findViewById(R.id.imageView_two);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Преобразовываем введенные в EditText поля в String (строку),также и imageviewURI
                String titleName = etName.getText().toString().trim();
                String titleMark = etMark.getText().toString().trim();
                String titleYear = etYear.getText().toString().trim();
                String image = imageDate.toString();

               //Полученные данные String значение (строки) передаем через команду(set)в нашу модельку
                Intent intent = new Intent();
                Title title = new Title();
                title.setName(titleName);
                title.setMark(titleMark);
                title.setYear(titleYear);
                title.setImageView(image);
             // а модельку в которой уже имеюся данные передаем в Intent
                intent.putExtra("title",title);
                setResult(RESULT_OK,intent);
                finish();

            }
        });




        //Cоздаем календарь с помощью клика на кнопку date
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ActivityTwo.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        ActivityTwo.this.dateOf = new Date(year,month,day);
                        etYear.setText(i2 + "." + (i1+1) + "." + i);
                    }
                },  year,month,day);
                   dialog.show();
            }
        });
        // Здесь мы получаем данные из MainActivity и передаем в поля EditText для того чтобы редактировать
        Intent intent = getIntent();
        if (intent !=null) {
            Title title = (Title) intent.getSerializableExtra("changeKey");
            if (title != null) {
                etName.setText(title.name);
                etMark.setText(title.mark);
                etYear.setText(title.year);
                imageView.setImageURI(Uri.parse(title.imageView));

            }
        }
    }

    //Создали переход в галерею,и возвращаем от туда картину
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose image"),200);

    }

    //Тут мы получаем данные из галерии и передаем в наш ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK){

            imageDate = data.getData();
            imageView.setImageURI(imageDate);

        }
    }

    public void setTime(View view) {
    }
}