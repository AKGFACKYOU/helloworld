package com.example.jack.zssq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jack.zssq.book.Catalog;

import java.util.List;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/7
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private List<Catalog> mCatalog;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView listContext;
        TextView catalogUrl;
        View CatalogView;
        public ViewHolder(View itemView) {
            super(itemView);
            CatalogView=itemView;
            listContext=(TextView)itemView.findViewById(R.id.tv_catalogList);
            catalogUrl=(TextView)itemView.findViewById(R.id.tv_url);
        }
    }

    public CatalogAdapter(List<Catalog> catalogList)
    {
        mCatalog=catalogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.CatalogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Catalog catalog=mCatalog.get(position);
                Intent intent=new Intent(view.getContext(),MainActivity.class);
                intent.putExtra("getCatalogUrl",catalog.getCatalogUrl());
                Log.e("adpter", "onClick: "+ catalog.getCatalogUrl());

                view.getContext().startActivity(intent);

                ((Activity)view.getContext()).finish();


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, int position) {
            Catalog catalog=mCatalog.get(position);
        holder.listContext.setText(catalog.getListContext());
        holder.catalogUrl.setText(catalog.getCatalogUrl());
    }

    @Override
    public int getItemCount() {
        return mCatalog.size();
    }

}
