package com.infosolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsOpening;

import java.util.List;

public class AdapterOwnerDetailsOpening extends RecyclerView.Adapter<AdapterOwnerDetailsOpening.MyViewHolder> {

    private Context context;
    private List<ModelOwnerDetailsOpening> list;

    public AdapterOwnerDetailsOpening(Context context, List<ModelOwnerDetailsOpening> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_ownerdetails_opening, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelOwnerDetailsOpening modelOwnerDetailsOpening = list.get(position);

        holder.txt_DESCRIPTION.setText(modelOwnerDetailsOpening.getDESCRIPTION());
        holder.txt_DEFECTIVE.setText(modelOwnerDetailsOpening.getDEFECTIVE());
        holder.txt_OPENING_FULL.setText(modelOwnerDetailsOpening.getOPENING_FULL());
        holder.txt_OPENING_EMPTY.setText(modelOwnerDetailsOpening.getOPENING_EMPTY());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_DESCRIPTION, txt_DEFECTIVE, txt_OPENING_FULL, txt_OPENING_EMPTY;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_DESCRIPTION = (TextView) itemView.findViewById(R.id.txt_DESCRIPTION);
            txt_DEFECTIVE = (TextView) itemView.findViewById(R.id.txt_DEFECTIVE);
            txt_OPENING_FULL = (TextView) itemView.findViewById(R.id.txt_OPENING_FULL);
            txt_OPENING_EMPTY = (TextView) itemView.findViewById(R.id.txt_OPENING_EMPTY);
        }
    }
}
