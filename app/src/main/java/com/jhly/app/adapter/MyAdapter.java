package com.jhly.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhly.app.api.Plan;
import com.jhly.app.R;

import java.util.List;

/**
 * Created by r on 2017/4/23.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Plan> list;
    private Context context;
    private int selectedPosition = -5;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(){}

    public MyAdapter(List<Plan> list ,Context context){
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(null == list) return;
        holder.itemView.setSelected(selectedPosition == position);
        holder.data.setText("id:"+list.get(position).getId()+"\t工厂:"+list.get(position).getSrc()+"\t开始时间:"+list.get(position).getBeginTime()+"\t->结束时间:"+list.get(position).getEndTime()+"\n总重量:"+list.get(position).getTotal()+"\t已使用:"+list.get(position).getUsed());
        if(selectedPosition == position){
            holder.selected.setVisibility(View.VISIBLE);
        }else{
            holder.selected.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,pos);
                    selectedPosition = pos;
                    notifyItemChanged(selectedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size()==0?0:list.size();
    }


public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView data;
        private ImageView selected;

    public ViewHolder(View itemView){
        super(itemView);
        data = (TextView)itemView.findViewById(R.id.tv_data);
        selected = (ImageView) itemView.findViewById(R.id.iv_selected);
    }
}

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
