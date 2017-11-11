package com.example.sumit.pen;
import android.os.AsyncTask;
import java.sql.Connection;
import android.util.Log;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by sumit on 10/8/2017.
 */

public class Database extends AsyncTask<String,String, ResultSet> {
    Connection con;
    @Override
    protected ResultSet doInBackground(String... params) {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:jtds:sqlserver://sql5014.smarterasp.net/DB_9B030C_noppnpdev","DB_9B030C_noppnpdev_admin","Geetuvas@1234");
            if(con == null){
                Log.i("notic","connection lost");
            }
            else{
                Log.i("connection","connection successfully");
            }

        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPreExecute(){

    }
    protected void onProgressUpdate(String s){

    }
    protected void onPostUpdate(String k){

    }


}
