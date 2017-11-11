package com.example.sumit.pen;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by sumit on 10/9/2017.
 */


public class ConnectionClass extends AsyncTask<String , String ,Connection> {

        Connection con;

    @Override
    protected Connection doInBackground(String... params) {
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://sql5014.smarterasp.net/DB_9B030C_noppnpdev","DB_9B030C_noppnpdev_admin","Geetuvas@1234");
            System.out.print("connection done");
        }catch(Exception e){
            Log.i("exception",e.toString());
        }
        return con;
    }
    public ResultSet productList(){
       try{
           PreparedStatement ps = con.prepareStatement("select * from PRODUCT");
           ps.execute();
           ResultSet rs = ps.getResultSet();
           return rs;
       }catch(Exception e){

       }
       return null;
    }

}


