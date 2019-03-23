package com.example.myprojectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class Register extends AppCompatActivity {
    private Connection m_con= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button register1 = (Button) findViewById(R.id.register_register);
        register1.setOnClickListener(new View.OnClickListener(){
            String check_username = "";
            @Override
            public void onClick(View v){
                final EditText register_user = (EditText) findViewById(R.id.register_username);
                final EditText pwd1 = (EditText) findViewById(R.id.register_password);
                final EditText pwd2 = (EditText) findViewById(R.id.register_repassword);

                if(register_user.getText().toString().equals(""))
                {
                    Toast.makeText(Register.this, "userid can't be empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pwd1.getText().toString().equals(""))
                    {
                        Toast.makeText(Register.this, "Password can't be empty!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(pwd2.getText().toString().equals(""))
                        {
                            Toast.makeText(Register.this, "Please input your password again!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(pwd1.getText().toString().equals(pwd2.getText().toString()))
                            {
                                new Thread()
                                {
                                    public void run()
                                    {
                                        try
                                        {
                                            insert(register_user.getText().toString(),pwd1.getText().toString());
                                        }
                                        catch(Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                                Toast.makeText(Register.this, "You have successfully registered!",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Register.this, "The passwords entered twice are different ",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                }
            }
        });
    }

    public void insert(String a, String b) throws Exception
    {
        String sql1 = "INSERT INTO UserInfo(username,password) VALUES(?,?)" ;
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
            PreparedStatement pstmt = m_con.prepareStatement(sql1) ;  ;
            if(pstmt == null)
                return ;
            else{
                pstmt.setString(1,a);
                pstmt.setString(2,b);
                pstmt.executeUpdate();
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
    }
}
