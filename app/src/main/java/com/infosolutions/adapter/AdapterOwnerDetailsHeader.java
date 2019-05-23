package com.infosolutions.adapter;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsHeader;

import java.util.ArrayList;
import java.util.List;

public class AdapterOwnerDetailsHeader extends RecyclerView.Adapter<AdapterOwnerDetailsHeader.MyViewHolder> {

    private static int mPosition;
    private static SparseBooleanArray sSelectedItems;
    private Context mContext;
    private List<ModelOwnerDetailsHeader> list;
    private OnItemListener listener;
    List<Integer> selectPos = new ArrayList<>();
    int row_index= -1;

    public AdapterOwnerDetailsHeader(List<ModelOwnerDetailsHeader> list, OnItemListener listener, Context mContext) {
        this.list = list;
        this.listener = listener;
        this.mContext = mContext;
        sSelectedItems = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_ownerdetails_header, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ModelOwnerDetailsHeader header = list.get(position);
        holder.txtHeader.setText(header.getDISPLAY_NAME());

        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });

        /*if (sSelectedItems.get(position)) {
            holder.txtHeader.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            holder.txtHeader.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        }*/

        if (row_index==position){
            holder.txtHeader.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }else{
            holder.txtHeader.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtHeader;
        OnItemListener listener;


        public MyViewHolder(View itemView, OnItemListener listener) {
            super(itemView);

            txtHeader = itemView.findViewById(R.id.txtHeader);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mPosition = getAdapterPosition();



            /*if (sSelectedItems.get(mPosition, false)) {
                sSelectedItems.delete(mPosition);
                //txtHeader.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            } else {
                //txtHeader.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                sSelectedItems.put(mPosition, true);
                selectPos.add(mPosition);
            }*/
            listener.onItemClick(mPosition);
        }


    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
