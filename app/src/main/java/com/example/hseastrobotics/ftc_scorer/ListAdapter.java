package com.example.hseastrobotics.ftc_scorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hseastrobotics on 2/4/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context context;
    List<UserData> dataList = new ArrayList<>();
    LayoutInflater inflater;
    Listener listener;

    public ListAdapter(Context context, List<UserData> dataList1) {

        this.context = context;
        this.dataList = dataList1;
        this.listener = (Listener) context;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.recycler_row, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.autoScore.setText(dataList.get(position).autoScore);
        holder.teleOpScore.setText(dataList.get(position).teleOpScore);
        holder.teamName.setText(dataList.get(position).TeamName);
        holder.delete.setTag(position);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nameToChnge(dataList.get((Integer) v.getTag()).TeamName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView autoScore;
        TextView teleOpScore;
        TextView teamName;
        ImageView delete;

        public ListViewHolder(View itemView) {
            super(itemView);

            autoScore       = (TextView)itemView.findViewById(R.id.Auto_score);
            teleOpScore     = (TextView)itemView.findViewById(R.id.TeleOp_Score);
            teamName        = (TextView)itemView.findViewById(R.id.Team_Name);
            delete          = (ImageView)itemView.findViewById(R.id.Delete);

        }
    }
}
