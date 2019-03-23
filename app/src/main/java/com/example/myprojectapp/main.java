package com.example.myprojectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.View;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn_Change_Password = (Button)findViewById(R.id.changepwd);
        Button btn_Search_Grades = (Button)findViewById(R.id.search_grades);
        Button btn_Search_Info = (Button)findViewById(R.id.search_info);
        Button btn_Notice = (Button)findViewById(R.id.notice);

        btn_Change_Password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(main.this , ChangePassword.class);
                startActivity(i);
            }

        });

        btn_Search_Grades.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(main.this , SearchGrades.class);
                startActivity(i);
            }

        });

        btn_Search_Info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(main.this , SearchInfo.class);
                startActivity(i);
            }

        });

        btn_Notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(main.this , Notice.class);
                startActivity(i);
            }

        });


    }
}

