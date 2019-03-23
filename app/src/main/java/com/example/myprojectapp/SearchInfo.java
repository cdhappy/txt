package com.example.myprojectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchInfo extends AppCompatActivity {
    private Connection m_con= null;
    private Spinner mCollegeSpinner = null;
    private Spinner mMajorSpinner = null;
    private Spinner mYearSpinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_info);

        mCollegeSpinner = (Spinner)findViewById(R.id.college);
        mMajorSpinner = (Spinner)findViewById(R.id.major);
        mYearSpinner = (Spinner)findViewById(R.id.year);

        //String[] arr = {"Software","Chemistry"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr);
        //mMajorSpinner.setAdapter(adapter);
        mCollegeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                //String selectText = mSpinner_type.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
        mMajorSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {

            }
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
        mYearSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {

            }
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });


        Button btn_Search_Information = (Button)findViewById(R.id.search_info);

        btn_Search_Information.setOnClickListener(new View.OnClickListener() {
            String searched_point = "";

            @Override
            public void onClick(View v){

                final Spinner txt1 = (Spinner)findViewById(R.id.college);
                final Spinner txt2 = (Spinner)findViewById(R.id.major);
                final Spinner txt3 = (Spinner)findViewById(R.id.year);
                //final EditText txt4 = (EditText)findViewById(R.id.major);
                final TextView txt5 = (TextView)findViewById(R.id.search_info_txt5);


                if(mCollegeSpinner.getSelectedItem().toString().equals(""))
                {
                    Toast.makeText(SearchInfo.this, "Please input University name! ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(mMajorSpinner.getSelectedItem().toString().equals(""))
                    {
                        Toast.makeText(SearchInfo.this, "Please input the major!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(mYearSpinner.getSelectedItem().toString().equals(""))
                        {
                            Toast.makeText(SearchInfo.this, "Please input the year!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                                new Thread()
                                {
                                    public void run()
                                    {
                                        try
                                        {
                                            searched_point = Point(mCollegeSpinner.getSelectedItem().toString(),mMajorSpinner.getSelectedItem().toString(),mYearSpinner.getSelectedItem().toString());
                                            if(searched_point.equals("Not found"))
                                            {
                                                txt5.setText("Not found");
                                            }
                                            else
                                            {
                                                txt5.setText("The point is "+Point(mCollegeSpinner.getSelectedItem().toString(),mMajorSpinner.getSelectedItem().toString(),mYearSpinner.getSelectedItem().toString()));
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

            }
        });
    }


    public String Point(String a, String b,String c) throws Exception
    {
        String point = "Not found";
        String sql = "SELECT point FROM Point WHERE  college=? and major=? and year=?" ;
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
                return point;
            else{
                pstmt.setString(1,a);
                pstmt.setString(2,b);
                pstmt.setString(3,c);
                ResultSet rs = pstmt.executeQuery();
                while(rs.next())
                {
                    point = rs.getString("point");
                }
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
        return point;
    }
}
