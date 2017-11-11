package com.example.sumit.pen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // intialization of global varriable
    TextView textView;
    EditText editText, editText1;
    String user_nam, user_pass;
    int user_id;
    Connection con;
    GlobalClass globalClass = null;
    ResultSet user;
    Button button;
    Thread thread;
    boolean internetconn;
    //Methods
    // Oncreate methods.this method is execute when the app start.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalClass = (GlobalClass)getApplicationContext();
        editText = (EditText) findViewById(R.id.Username);
        editText1 = (EditText) findViewById(R.id.Password);
        textView = (TextView) findViewById(R.id.instruction);
        internetconn = internetConneciton();

    }

    // click method when the Login button is clicked this function will call.

    public void click(View v) {
         if(internetconn == false){
             internetconn = internetConneciton();
         }
         else{
             textView.setText("");
             int temp;
             final String User_name = editText.getText().toString();
             final String User_password = editText1.getText().toString();

             System.out.print("edd"+User_name+"dsfs");
             if ((User_name.toString().equals("")) || (User_password.toString().equals(""))) {
                 textView.setText("Fill user Id and Password");
             } else {
                 System.out.println("sklfjaslkdfjalksd;");
                 if(thread != null){
                     thread = null;
                     Log.i("fdiad","edfada");
                 }
                 thread = new Thread(new Runnable() {
                     @Override
                     public void run() {

                         String temp = null;
                         try {

                             PreparedStatement ps = con.prepareStatement("Select [password],[passwordSalt],[id] from customer where username='" + User_name + "'");
                             ps.execute();
                             ResultSet user = ps.getResultSet();
                             if(user== null){
                                 textView.setText("Invalid Username");
                             }
                             else{
                                 while (user.next()) {
                                     String user_passwordSalt_db;
                                     int user_id_db;
                                     String user_password_db;
                                     user_id_db = user.getInt("id");
                                     user_password_db = user.getString("password");
                                     user_passwordSalt_db = user.getString("passwordSalt");
                                     String hashPassword = generateHash(User_password+user.getString("passwordSalt"));
                                     Log.i("password",hashPassword);

                                     user_passwordSalt_db = user.getString("passwordSalt");
                                     System.out.println("the password =" + user.getString("password") + "id =" + user.getString("id") + "the password salt =" + user.getString("passwordsalt"));
                                     if(hashPassword.equalsIgnoreCase(user_password_db)){
                                         Intent dashboardPage = new Intent(getApplicationContext(),Dashboard.class);
                                         dashboardPage.putExtra("id",user_id_db);
                                         startActivity(dashboardPage);
                                     }
                                     else{
                                         runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_SHORT).show();

                                             }
                                         });
                                         break;
                                     }
                                 }
                             }


                         } catch (SQLException e) {
                             e.printStackTrace();
                         }
                     }
                 });
                 thread.start();
             }
         }

    }
    public  String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }
        return hash.toString();
    }
    public boolean internetConneciton(){
        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = null;
        network = cn.getActiveNetworkInfo();
        if(network!= null){
            try {
                ConnectionClass database = new ConnectionClass();
                con = (Connection) database.execute().get();
                globalClass.setConnection(con);
            }  catch (Exception e) {
                Toast.makeText(this, "check the internet connection", Toast.LENGTH_SHORT).show();
                con = null;
            }
            if(con == null){

              return false;
            }
            else{
              return true;
            }
        }
        else{
            Toast.makeText(this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
