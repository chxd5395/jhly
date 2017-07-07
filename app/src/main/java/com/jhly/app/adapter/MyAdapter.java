package com.jhly.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhly.app.api.Plan;
import com.jhly.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r on 2017/4/23.
 */

public class MyAdapter extends BaseAdapter {
    private List<Plan> list = new ArrayList<Plan>();
    private Context context;

    public MyAdapter(List<Plan> list ,Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder holder = null;
        if(view == null){
            holder = new Viewholder();
            view = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            holder.data = (TextView) view.findViewById(R.id.tv_data);
            holder.quantity = (TextView) view.findViewById(R.id.tv_quantity);
            view.setTag(holder);
        }else {
            holder = (Viewholder) view.getTag();
        }
        holder.data.setText("id:"+list.get(i).getId()+"   "+"工厂:"+list.get(i).getSrc()+"   "+ "时间段:"+list.get(i).getBeginTime()+"--"+list.get(i).getEndTime());
        holder.quantity.setText("total:"+list.get(i).getTotal()+"   "+"used:"+list.get(i).getUsed());
        return view;
    }

    private class Viewholder{
        private TextView data;
        private TextView quantity;

    }
}
