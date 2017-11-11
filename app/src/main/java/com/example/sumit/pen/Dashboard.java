package com.example.sumit.pen;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.Connection;

public class Dashboard extends AppCompatActivity {
    private static Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        TextView title = (TextView)findViewById(R.id.dashboard_title);
        title.setText("Dashboard"+getIntent().getExtras().getInt("id"));

        Button order = (Button)findViewById(R.id.order);
        order.setOnClickListener(new Onclick());

    }
    class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dashboardClick(v);
        }
    }
    public void dashboardClick(View v){
        Intent productOrder = null;
       switch(v.getId()) {
           case R.id.order:
               if(productOrder!=null)
                productOrder = null;
               productOrder = new Intent(getApplicationContext(),product_order.class);
                productOrder.putExtra("Connection", (Parcelable) con);
               startActivity(productOrder);

               break;
            }
    }


}
