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

public class MyAdapterComplaint extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<ComplaintDataClass> dataList;

    public MyAdapterComplaint(Context context, List<ComplaintDataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_recycler,parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recText.setText(dataList.get(position).getText());
        holder.recDate.setText(dataList.get(position).getDate());
        holder.recTime.setText(dataList.get(position).getTime());
        holder.recDesc.setText(dataList.get(position).getDesc());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ComplaintDetailActivity.class);
                intent.putExtra("Text", dataList.get(holder.getAdapterPosition()).getText());
                intent.putExtra("Desc", dataList.get(holder.getAdapterPosition()).getDesc());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getTime());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView recText;
    TextView recDate;
    TextView recTime;
    TextView recDesc;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recText=itemView.findViewById(R.id.recComplaintText);
        recDate=itemView.findViewById(R.id.recComplaintDate);
        recTime=itemView.findViewById(R.id.recComplaintTime);
        recDesc=itemView.findViewById(R.id.recComplaintDesc);
        recCard = itemView.findViewById(R.id.recComplaintCard);

    }
}

