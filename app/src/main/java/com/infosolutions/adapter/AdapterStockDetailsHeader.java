package com.infosolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsHeader;

import java.util.List;

public class AdapterStockDetailsHeader extends RecyclerView.Adapter<AdapterStockDetailsHeader.MyViewHolder> {

    private OnItemListener listener;
    private Context mContext;
    private List<ModelOwnerDetailsHeader> list;

    public AdapterStockDetailsHeader(Context mContext, OnItemListener listener, List<ModelOwnerDetailsHeader> list) {
        this.mContext = mContext;
        this.listener = listener;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_ownerdetails_header, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelOwnerDetailsHeader header = list.get(position);
        holder.txtHeader.setText(header.getDISPLAY_NAME());

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

            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
