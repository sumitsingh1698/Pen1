package com.example.sumit.pen;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class product_order extends AppCompatActivity {
    Connection con = null;
    ResultSet productlist;
    GlobalClass globalClass = null;
    private List<ListItem> listItems;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private int lastElement = 0;
    private int totalElement = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass)getApplicationContext();
        setContentView(R.layout.activity_product_order);
        try{
            con = globalClass.getConnection();
        }catch(Exception e){
            Log.i("Exception ",e.toString());
        }
        recyclerView = (RecyclerView) findViewById(R.id.Item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        Log.i("sumit singh","dance");
        loadFirstTime();
        Log.i("sumit singh","dfffff");
        viewItem();
        adapter = new CustomAdapter(listItems
                ,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mPreviousTotal = 0;
            private boolean mLoading = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (mLoading) {
                    if (totalItemCount > mPreviousTotal) {
                        mLoading = false;
                        mPreviousTotal = totalItemCount;
                    }
                }
                int visibleThreshold = 5;
                if (!mLoading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    Log.i("sumit singh","ram is back");
                    onLoadMore();

                    Log.i("sumit singh","ram is ");
                    mLoading = true;
                }
            }
        });
    }

    public void loadFirstTime(){

        Thread thread = new Thread(new Runnable() {
            int i=0;
            @Override
            public void run() {
                try {
                    PreparedStatement ps = con.prepareStatement("select * from product");
                    ps.execute();
                    productlist = ps.getResultSet();
                    while(productlist.next()){
                        String s = productlist.getString("name");

                        Log.i("sumit singh",s);
                        ListItem listItem = new ListItem(s);
                        Log.i("sumit singh","d");
                        listItems.add(listItem);
                        lastElement=i;
                        if(i==20)
                            break;
                        i++;
                        Log.i("sumit singh","df");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void onLoadMore(){
        try {
            boolean s = new LoadMore().execute().get();
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

 public void viewItem(){
     Log.i("dsjfk;las","sdafaa");
     for(int i =0 ;i<listItems.size();i++){
         Log.i("item"+i, listItems.get(i).toString());
     }
 }
   private class LoadMore extends AsyncTask<Object, Object, Boolean> {

       @Override
       protected Boolean doInBackground(Object... params) {
           int i = 0;
           int j = lastElement;
           try {
               while(productlist.next()){
                   ListItem listItem = new ListItem(productlist.getString("name"));
                   listItems.add(listItem);
                   i++;
                   if(i>=j){
                       lastElement=i;
                       break;
                   }
               }


           } catch(SQLException e) {
               e.printStackTrace();
           }

           return true;
       }
   }

}