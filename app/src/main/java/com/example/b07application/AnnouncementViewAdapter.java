package com.example.b07application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07application.ui.Announcement;

import java.util.ArrayList;

public class AnnouncementViewAdapter extends RecyclerView.Adapter<AnnouncementViewAdapter
        .AnnouncementViewHolder> {
    Context context;
    ArrayList<Announcement> announcementList;

    public AnnouncementViewAdapter(Context context, ArrayList<Announcement> announcementList) {
        this.context = context;
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.announcement_template, parent, false
        );
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcementList.get(position);

        holder.title.setText(announcement.getTitle());
        holder.author.setText(announcement.getAuthor());
        holder.body.setText(announcement.getBody());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView body;
        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.sampleTitle);
            author = itemView.findViewById(R.id.sampleAuthor);
            body = itemView.findViewById(R.id.sampleBody);
        }
    }
}
