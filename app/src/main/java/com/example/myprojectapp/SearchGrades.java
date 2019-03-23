package com.example.myprojectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchGrades extends AppCompatActivity{
    private Connection m_con= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_grades);

        Button btn_Search_Grades = (Button)findViewById(R.id.search_grades);
        btn_Search_Grades.setOnClickListener(new View.OnClickListener(){
            String searched_grade = "";
            @Override
            public void onClick(View v){

                final EditText txt1 = (EditText)findViewById(R.id.Examination_Number);
                final EditText txt2 = (EditText)findViewById(R.id.Examination_Password);
                final TextView txt3 = (TextView)findViewById(R.id.grades);



                if(txt1.getText().toString().equals(""))
                {
                    Toast.makeText(SearchGrades.this, "Please input your Entrance Examination Number! ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(txt2.getText().toString().equals(""))
                    {
                        Toast.makeText(SearchGrades.this, "Please input your Entrance Examination Password",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        new Thread()
                        {
                            public void run()
                            {
                                try
                                {
                                    searched_grade = searchgrades(txt1.getText().toString(),txt2.getText().toString());
                                    if(searched_grade.equals("Not found"))
                                    {
                                        txt3.setText("Examination number or password is wrong!");
                                    }
                                    else
                                    {
                                        txt3.setText("Your grade is "+searchgrades(txt1.getText().toString(),txt2.getText().toString()));
                                    }
                                    //if(searchgrades(txt1.getText().toString(),txt2.getText().toString().equals("Examination number or examination password is wrong!"))
                                    //txt3.setText("Your grade is "+searchgrades(txt1.getText().toString(),txt2.getText().toString()));
                                    //grade_str = searchgrades(txt1.getText().toString());
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    }

                }

            }

        });
    }

    public String searchgrades(String a, String b) throws Exception
    {
        String grades = "Not found";
        String sql = "SELECT grade FROM Grades WHERE number=? and password=?" ;
        //PreparedStatement pstmt  ;
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        Log.d("加载驱动", "成功");
        //10.253.219.33192.168.145.1
        m_con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.145.1:1433/MyProjectApp" ,"sa", "998528");

        if(m_con!=null)
        {
            Log.d("sqlserver", "数据库连接成功");
        }
        try
        {
            PreparedStatement pstmt = m_con.prepareStatement(sql) ;  ;
            if(pstmt == null)
                return grades;
            else{
                //pstmt = m_con.prepareStatement(sql) ;
                pstmt.setString(1,a);
                pstmt.setString(2,b);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next())
                {
                    grades = rs.getString("grade");
                }
                //pstmt.executeUpdate();
                Log.d("加载驱动", "更新成功");
                pstmt.close() ;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("操作中出现错误！！！") ;
        }
        finally
        {
            m_con.close() ;
        }
        return grades;
    }
}
