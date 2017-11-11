package com.example.sumit.pen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sumit on 11/3/2017.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder>{
    private List<ListItem> itemlist;
    private Context context;

    public CustomAdapter(List<ListItem> itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productlist,parent,false);

        return new CustomHolder(v);
    }
    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
      ListItem listItem = itemlist.get(position);
      holder.textView.setText(listItem.getItemName());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
    class CustomHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public CustomHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.productName);

        }
    }
}
