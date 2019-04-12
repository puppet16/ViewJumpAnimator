package com.luck.viewjumpwithanimator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/12
 *************************************************************************************/
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.BeautifulViewHolder> {
    private Context context;
    private List<String> list;
    private onItemClickListener mOnItemClickListener;

    public SimpleRecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BeautifulViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_string, parent, false);
        return new BeautifulViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeautifulViewHolder holder, final int position) {
        holder.textview.setText(list.get(position));
        holder.imageView.setImageResource(R.drawable.test1);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position, View v);
    }

    public class BeautifulViewHolder extends RecyclerView.ViewHolder {

        TextView textview;
        ImageView imageView;

        public BeautifulViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.tv);
            imageView = itemView.findViewById(R.id.iv);
        }
    }
}
