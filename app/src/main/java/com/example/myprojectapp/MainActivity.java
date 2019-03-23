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

public class MainActivity extends AppCompatActivity {
    private Connection m_con= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button register = (Button) findViewById(R.id.register);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener(){
            String searchedresult = "";
            String usr = "";
            String pwd1 = "";
            @Override
            public void onClick(View v){
                final EditText username = (EditText) findViewById(R.id.login_username);
                final EditText pwd = (EditText) findViewById(R.id.login_password);
                final TextView txt = (TextView)findViewById(R.id.rs);
                usr = username.getText().toString();
                pwd1 = pwd.getText().toString();

                if(usr.equals(""))
                {
                    Toast.makeText(MainActivity.this, "userid can't be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pwd1.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Password can't be empty!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Intent i = new Intent(MainActivity.this , main.class);
                        //startActivity(i);
                        new Thread()
                        {
                            public void run()
                            {
                                try
                                {
                                    searchedresult = Login(usr,pwd1);
                                    if(searchedresult.equals("no"))
                                    {
                                        txt.setText("Examination number or password is wrong!");
                                    }
                                    else
                                    {
                                        Intent i = new Intent(MainActivity.this , main.class);
                                        startActivity(i);
                                    }

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
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent i = new Intent(MainActivity.this , Register.class);
                    startActivity(i);
            }
        });


    }

    public String Login(String a, String b) throws Exception
    {
        String result = "no";
        String sql = "SELECT password FROM UserInfo WHERE username=? and password=?" ;
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
            PreparedStatement pstmt = m_con.prepareStatement(sql) ;
            if(pstmt == null)
                return result;
            else{

                pstmt.setString(1,a);
                pstmt.setString(2,b);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next())
                {
                    result = rs.getString("password");
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
        return result;
    }
}
