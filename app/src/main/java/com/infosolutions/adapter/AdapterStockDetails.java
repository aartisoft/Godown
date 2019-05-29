package com.infosolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetails;
import com.infosolutions.model.ModelStockDetails;
import com.infosolutions.ui.owner.OwnerDetailingActivity_new;
import com.infosolutions.ui.user.stock.StockDetails;

import java.util.List;

public class AdapterStockDetails extends RecyclerView.Adapter<AdapterStockDetails.MyViewHolder> {

    private Context context;
    private List<ModelStockDetails> list;

    public AdapterStockDetails(Context context, List<ModelStockDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_stockdetails, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelStockDetails modelStockDetails = list.get(position);

        if(StockDetails.itemName.equalsIgnoreCase("OPENING STOCKS")) {

            holder.valueOpening.setVisibility(View.VISIBLE);
            holder.descriptionOpening.setText(modelStockDetails.getDESCRIPTION());
            holder.defectiveOpening.setText(modelStockDetails.getDEFECTIVE());
            holder.fullOpening.setText(modelStockDetails.getOPENING_FULL());
            holder.emptyOpening.setText(modelStockDetails.getOPENING_EMPTY());

        }

        if(StockDetails.itemName.equalsIgnoreCase("CLOSING STOCKS")) {

            holder.valueClosing.setVisibility(View.VISIBLE);
            holder.descriptionClosing.setText(modelStockDetails.getDESCRIPTION());
            holder.defectiveClosing.setText(modelStockDetails.getDEFECTIVE());
            holder.fullClosing.setText(modelStockDetails.getCLOSING_FULL());
            holder.emptyClosing.setText(modelStockDetails.getCLOSING_EMPTY());

        }

        if(StockDetails.itemName.equalsIgnoreCase("LOAD RECEIVED")) {

            holder.valueReceive.setVisibility(View.VISIBLE);
            holder.descriptionReceive.setText(modelStockDetails.getDESCRIPTION());
            holder.soundReceive.setText(modelStockDetails.getSOUND());
            holder.lostReceive.setText(modelStockDetails.getLOST_TRUCK_RECEVING());

        }

        if(StockDetails.itemName.equalsIgnoreCase("LOAD SEND")) {

            holder.valueSend.setVisibility(View.VISIBLE);
            holder.descriptionSend.setText(modelStockDetails.getDESCRIPTION());
            holder.soundSend.setText(modelStockDetails.getSOUND());
            holder.defectiveSend.setText(modelStockDetails.getTRUCK_DEFECTIVE());

        }

        if(StockDetails.itemName.equalsIgnoreCase("DELIVERY")) {

            holder.valueDelivery.setVisibility(View.VISIBLE);
            holder.descriptionDelivery.setText(modelStockDetails.getDESCRIPTION());
            holder.defectiveDelivery.setText(modelStockDetails.getDEFECTIVE());
            holder.svDelivery.setText(modelStockDetails.getSV());
            holder.tvDelivery.setText(modelStockDetails.getTV());
            holder.fullDelivery.setText(modelStockDetails.getDELIVERY_FULL());
            holder.emptyDelivery.setText(modelStockDetails.getDELIVERY_EMPTY());

        }

        if(StockDetails.itemName.equalsIgnoreCase("OTHER STOCKS")) {

            holder.valueOther.setVisibility(View.VISIBLE);
            holder.descriptionOther.setText(modelStockDetails.getDESCRIPTION());
            holder.creditOther.setText(modelStockDetails.getCREDIT());
            holder.lostOther.setText(modelStockDetails.getLOST_DELIVERY_CYLINDERS());
            holder.onfieldOther.setText(modelStockDetails.getON_FIELD());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout valueOpening, valueClosing, valueReceive, valueSend, valueDelivery, valueOther;

        TextView descriptionOpening, defectiveOpening, fullOpening, emptyOpening;
        TextView descriptionClosing, defectiveClosing, fullClosing, emptyClosing;
        TextView descriptionReceive, soundReceive, lostReceive;
        TextView descriptionSend, soundSend, defectiveSend;
        TextView descriptionDelivery, defectiveDelivery, svDelivery, tvDelivery, fullDelivery, emptyDelivery;
        TextView descriptionOther, creditOther, lostOther, onfieldOther;

        public MyViewHolder(View itemView) {
            super(itemView);

            valueOpening = (LinearLayout) itemView.findViewById(R.id.valueOpening);
            valueClosing = (LinearLayout) itemView.findViewById(R.id.valueClosing);
            valueReceive = (LinearLayout) itemView.findViewById(R.id.valueReceive);
            valueSend = (LinearLayout) itemView.findViewById(R.id.valueSend);
            valueDelivery = (LinearLayout) itemView.findViewById(R.id.valueDelivery);
            valueOther = (LinearLayout) itemView.findViewById(R.id.valueOther);

            descriptionOpening = (TextView) itemView.findViewById(R.id.descriptionOpening);
            defectiveOpening = (TextView) itemView.findViewById(R.id.defectiveOpening);
            fullOpening = (TextView) itemView.findViewById(R.id.fullOpening);
            emptyOpening = (TextView) itemView.findViewById(R.id.emptyOpening);

            descriptionClosing = (TextView) itemView.findViewById(R.id.descriptionClosing);
            defectiveClosing = (TextView) itemView.findViewById(R.id.defectiveClosing);
            fullClosing = (TextView) itemView.findViewById(R.id.fullClosing);
            emptyClosing = (TextView) itemView.findViewById(R.id.emptyClosing);

            descriptionReceive = (TextView) itemView.findViewById(R.id.descriptionReceive);
            soundReceive = (TextView) itemView.findViewById(R.id.soundReceive);
            lostReceive = (TextView) itemView.findViewById(R.id.lostReceive);

            descriptionSend = (TextView) itemView.findViewById(R.id.descriptionSend);
            soundSend = (TextView) itemView.findViewById(R.id.soundSend);
            defectiveSend = (TextView) itemView.findViewById(R.id.defectiveSend);

            descriptionDelivery = (TextView) itemView.findViewById(R.id.descriptionDelivery);
            defectiveDelivery = (TextView) itemView.findViewById(R.id.defectiveDelivery);
            svDelivery = (TextView) itemView.findViewById(R.id.svDelivery);
            tvDelivery = (TextView) itemView.findViewById(R.id.tvDelivery);
            fullDelivery = (TextView) itemView.findViewById(R.id.fullDelivery);
            emptyDelivery = (TextView) itemView.findViewById(R.id.emptyDelivery);

            descriptionOther = (TextView) itemView.findViewById(R.id.descriptionOther);
            creditOther = (TextView) itemView.findViewById(R.id.creditOther);
            lostOther = (TextView) itemView.findViewById(R.id.lostOther);
            onfieldOther = (TextView) itemView.findViewById(R.id.onfieldOther);

        }
    }
}
