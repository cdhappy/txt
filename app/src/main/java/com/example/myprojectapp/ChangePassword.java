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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangePassword extends AppCompatActivity{
    private Connection m_con= null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Button btn_Change_Commit = (Button)findViewById(R.id.Commit_Change_Password);
        //EditText txt1 = (EditText)findViewById(R.id.change_oldpassword);
        //EditText txt2 = (EditText)findViewById(R.id.change_password);
        //EditText txt3 = (EditText)findViewById(R.id.change_repassword);

        btn_Change_Commit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final EditText txt1 = (EditText)findViewById(R.id.change_username);
                final EditText txt2 = (EditText)findViewById(R.id.change_oldpassword);
                final EditText txt3 = (EditText)findViewById(R.id.change_newpassword);

                if(txt1.getText().toString().equals(""))
                {
                    Toast.makeText(ChangePassword.this, "Please input your username!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(txt2.getText().toString().equals(""))
                    {
                        Toast.makeText(ChangePassword.this, "Please input old password!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(txt3.getText().toString().equals(""))
                        {
                            Toast.makeText(ChangePassword.this, "Please input your new password!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                                Toast.makeText(ChangePassword.this, "You have changed your password! Please Login Again!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ChangePassword.this , MainActivity.class);
                                startActivity(i);
                                new Thread()
                                {
                                    public void run()
                                    {
                                        try
                                        {
                                            update(txt3.getText().toString(),txt1.getText().toString());
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

                }



        });
    }

    //change password
    public void update(String a, String b) throws Exception
    {
        String sql = "UPDATE UserInfo SET password=? WHERE username=?" ;
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
                return ;
            else{
                //pstmt = m_con.prepareStatement(sql) ;
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
