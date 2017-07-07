package com.jhly.app.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhly.app.R;
import com.jhly.app.api.Result;


/**
 * Created by r on 2017/6/30.
 */

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {
    private Result mResult;
    private Context context;

    public MyRecycleViewAdapter(Result result, Context context){
        this.mResult = result;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText("id:"+mResult.getId()+"\nid:"+mResult.lv.getId()+"\nvehicle:"+mResult.lv.getVehicle()+"\nphone:"+mResult.cv.getPhone());
    }


    @Override
    public int getItemCount() {
        if(null == mResult){
            return 0;
        }
        return 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_rec);
        }
    }
}
