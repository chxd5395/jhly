package com.jhly.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhly.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by r on 2017/7/18.
 */

public class FirstPageRecycleViewAdapter extends RecyclerView.Adapter<FirstPageRecycleViewAdapter.ViewHolder> {
    public int[] bitmaps;
    public String[] stringList;
    public Context context;
    public OnItemClickListener onItemClickListener;

    public FirstPageRecycleViewAdapter(int[] bitmaps, String[] stringList, Context context) {
        this.bitmaps = bitmaps;
        this.stringList = stringList;
        this.context = context;
    }

    public void setOnItemClickListener(FirstPageRecycleViewAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.firstpage_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.src.setImageResource(bitmaps[position]);
        holder.content.setText(stringList[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        int count = Math.max(bitmaps.length,stringList.length);
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView src;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            src = (ImageView)itemView.findViewById(R.id.iv_fp);
            content = (TextView) itemView.findViewById(R.id.tv_fp);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
