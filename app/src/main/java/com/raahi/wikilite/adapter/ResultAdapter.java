package com.raahi.wikilite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.raahi.wikilite.R;
import com.raahi.wikilite.activity.DetailsActivity;
import com.raahi.wikilite.model.Page;
import com.raahi.wikilite.model.ResultData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Raahi on 24-06-2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private ResultData mResultData;
    private List<Page> pages;
    private Context context;

    /**
     * View holder class
     */

    public ResultAdapter(ResultData resultData, Context context) {
        this.mResultData = resultData;
        pages = mResultData.getQuery().getPages();
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
        public ImageButton downloadBtn;
        public ProgressBar progressBar;
        public LinearLayout main_layout;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.item_image);
            downloadBtn = view.findViewById(R.id.download_button);
            progressBar = view.findViewById(R.id.download_progress);
            main_layout = view.findViewById(R.id.main_layout);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Page item = pages.get(position);
        String description = "";
        holder.title.setText(item.getTitle());
        if (item != null) {
            if (item.getTerms() != null) {
                if (!item.getTerms().getDescription().isEmpty() && item.getTerms().getDescription() != null && item.getTerms().getDescription().size() != 0)
                    description = String.valueOf(item.getTerms().getDescription());
                holder.description.setText(description.substring(1, description.length() - 1)); //;
            }

            if (item.getThumbnail() != null) {
                if (item.getThumbnail().getSource() != "" && item.getThumbnail().getSource() != null) {
                    String s1 = item.getThumbnail().getSource().substring(item.getThumbnail().getSource().lastIndexOf("/"));
                    s1 = s1.substring(1, s1.indexOf('-'));

                    s1 = item.getThumbnail().getSource().replace(s1, "100px");
                    System.out.println("aa ------------------------------------------------------       " + s1);
                    Picasso.get().load(s1).into(holder.image);
                }
            }
        }

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = item.getTitle().trim().replaceAll(" ", "_");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("title", title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view_holder, parent, false);
        return new MyViewHolder(v);
    }

}