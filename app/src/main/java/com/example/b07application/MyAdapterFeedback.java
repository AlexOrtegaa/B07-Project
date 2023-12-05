package com.example.b07application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterFeedback extends RecyclerView.Adapter<MyViewHolderFeedback>{
    private Context context;
    private List<FeedbackDataClass> dataList;

    public MyAdapterFeedback(Context context, List<FeedbackDataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolderFeedback onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_recycler,parent, false);
        return new MyViewHolderFeedback(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFeedback holder, int position) {

        holder.recComment.setText(dataList.get(position).getComment());
        holder.recRating.setText(String.valueOf(dataList.get(position).getRating()));


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ComplaintDetailActivity.class);
                intent.putExtra("comment", dataList.get(holder.getAdapterPosition()).getComment());
                intent.putExtra("rating", String.valueOf(dataList.get(holder.getAdapterPosition()).getRating()));



                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolderFeedback extends RecyclerView.ViewHolder {
    TextView recComment;
    TextView recRating;
    CardView recCard;

    public MyViewHolderFeedback(@NonNull View itemView) {
        super(itemView);

        recRating=itemView.findViewById(R.id.recFeedbackRating);
        recComment=itemView.findViewById(R.id.recFeedbackComment);
        recCard = itemView.findViewById(R.id.recFeedbackCard);

    }
}

