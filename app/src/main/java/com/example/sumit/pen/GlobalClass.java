package com.example.sumit.pen;

import android.app.Application;
import android.widget.Toast;

import java.sql.Connection;

/**
 * Created by sumit on 11/4/2017.
 */

public class GlobalClass extends Application{
    public Connection con;
    public void setConnection(Connection con){
        this.con = con;
    }
    public Connection getConnection(){
      return con;
    }
}
