package com.aoppp.magnetsearch.domain;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoppp.magnetsearch.R;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by guguyanhua on 6/23/15.
 */
public class MagnetAdapter extends android.widget.BaseAdapter {
    List<Magnet> magnetList;
    Context context;

    public MagnetAdapter(Context context) {
        this.context = context;
        this.magnetList = Lists.newArrayList();
    }

    public MagnetAdapter(List<Magnet> magnetList,Context context) {
        this.magnetList = magnetList;
        this.context = context;
    }

    public void addNewList(List<Magnet> magnetList){
        this.magnetList.addAll(magnetList);
    }

    @Override
    public int getCount() {
        return magnetList.size();
    }

    @Override
    public Object getItem(int i) {
        return magnetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    class ViewHolder {
        ImageView picture;
        TextView number;
        TextView name;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View layoutView = LayoutInflater.from(context).inflate(
                R.layout.sample_magnet_cell, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.picture = (ImageView) layoutView
                .findViewById(R.id.imageView);
        viewHolder.number = (TextView) layoutView.findViewById(R.id.number);

        viewHolder.name = (TextView) layoutView.findViewById(R.id.name);
//        viewHolder.picture.setImageResource(Integer.parseInt(magnetList.get(
//                position).get("imageView").toString()));
        viewHolder.number.setText(Html.fromHtml(magnetList.get(position).getTitle()));
//        Log.e("id", data.get(position).get("name").toString());
        viewHolder.name.setText(magnetList.get(position).getUrl());
        return layoutView;
    }
}
