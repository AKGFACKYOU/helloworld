package com.example.jack.zssq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.zssq.book.Book;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Jack on 2017/6/6.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mBook;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        SmartImageView pic;
        TextView bookname;
        TextView auther;
        TextView date;
        TextView desc;
        View bookview;
        public ViewHolder(View itemView) {
            super(itemView);
            bookview=itemView;
            pic= (SmartImageView) itemView.findViewById(R.id.im_pic);
            bookname= (TextView) itemView.findViewById(R.id.tv_bookname);
            auther= (TextView) itemView.findViewById(R.id.tv_auther);
            date= (TextView) itemView.findViewById(R.id.tv_date);
            desc= (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    public  BookAdapter(List<Book> bookList)
    {
        mBook=bookList;
    }
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booklist,parent,false);
        final  ViewHolder holder=new ViewHolder(view);

        holder.bookview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Book book=mBook.get(position);



//                Toast.makeText(view.getContext(), "你点击了"+book.getPicUrl(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(),CatalogActivity.class);
                intent.putExtra("url",book.getPicUrl());
                intent.putExtra("bookname",book.getBookname());
                intent.putExtra("bookauther",book.getAuther());
                intent.putExtra("desc",book.getDesc());

                intent.putExtra("picUrl",book.getPic());
                Toast.makeText(view.getContext(), "你点击了"+book.getPic(), Toast.LENGTH_SHORT).show();
//                Log.e("asfavdavascava", "onClickadvavdavdav: "+book.getPicUrl());
                view.getContext().startActivity(intent);

            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        Book book=mBook.get(position);
        holder.bookname.setText(book.getBookname());
        holder.auther.setText(book.getAuther());
        holder.date.setText(book.getDate());
        holder.pic.setImageUrl(book.getPic());
        holder.desc.setText(book.getDesc());

    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }

}
