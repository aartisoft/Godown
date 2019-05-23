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
import com.infosolutions.ui.owner.OwnerDetailingActivity_new;

import java.util.List;

public class AdapterOwnerDetails extends RecyclerView.Adapter<AdapterOwnerDetails.MyViewHolder> {

    private Context context;
    private List<ModelOwnerDetails> list;

    public AdapterOwnerDetails(Context context, List<ModelOwnerDetails> list) {
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

        ModelOwnerDetails modelOwnerDetails = list.get(position);

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("OPENING")) {

            holder.valueOpening.setVisibility(View.VISIBLE);
            holder.descriptionOpening.setText(modelOwnerDetails.getDESCRIPTION());
            holder.fullOpening.setText(modelOwnerDetails.getOPENING_FULL());
            holder.emptyOpening.setText(modelOwnerDetails.getOPENING_EMPTY());
            holder.defectiveOpening.setText(modelOwnerDetails.getDEFECTIVE());
        }

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("CLOSING")) {

            holder.valueOpening.setVisibility(View.VISIBLE);
            holder.descriptionOpening.setText(modelOwnerDetails.getDESCRIPTION());
            holder.fullOpening.setText(modelOwnerDetails.getOPENING_FULL());
            holder.emptyOpening.setText(modelOwnerDetails.getOPENING_EMPTY());
            holder.defectiveOpening.setText(modelOwnerDetails.getDEFECTIVE());
        }

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("DOMESTIC")) {

            holder.valueDomestic.setVisibility(View.VISIBLE);
            holder.descriptionDomestic.setText(modelOwnerDetails.getDESCRIPTION());
            holder.deliveryDomestic.setText(modelOwnerDetails.getDELIVERY());
            holder.svDomestic.setText(modelOwnerDetails.getSV());
            holder.dbcDomestic.setText(modelOwnerDetails.getDBC());
            holder.defectiveDomestic.setText(modelOwnerDetails.getDEFECTIVE());
            holder.lostDomestic.setText(modelOwnerDetails.getLOST());
        }

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("RECEIVING")) {

            holder.valueReceive.setVisibility(View.VISIBLE);
            holder.descriptionReceive.setText(modelOwnerDetails.getDESCRIPTION());
            holder.soundReceive.setText(modelOwnerDetails.getSOUND());
            holder.lostReceive.setText(modelOwnerDetails.getLOST());
        }

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("SENDING")) {

            holder.valueSend.setVisibility(View.VISIBLE);
            holder.descriptionSend.setText(modelOwnerDetails.getDESCRIPTION());
            holder.soundSend.setText(modelOwnerDetails.getSOUND());
            holder.defectiveSend.setText(modelOwnerDetails.getDEFECTIVE());
        }

        if(OwnerDetailingActivity_new.title.equalsIgnoreCase("OTHER")) {

            holder.valueOther.setVisibility(View.VISIBLE);
            holder.descriptionOther.setText(modelOwnerDetails.getDESCRIPTION());
            holder.creditOther.setText(modelOwnerDetails.getCREDIT());
            holder.lostOther.setText(modelOwnerDetails.getLOST());
            holder.onfieldOther.setText(modelOwnerDetails.getON_FIELD());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout valueOpening, valueDomestic, valueReceive, valueSend, valueOther;

        TextView descriptionOpening, fullOpening, emptyOpening, defectiveOpening;
        TextView descriptionDomestic, deliveryDomestic, svDomestic, dbcDomestic, defectiveDomestic, lostDomestic;
        TextView descriptionReceive, soundReceive, lostReceive;
        TextView descriptionSend, soundSend, defectiveSend;
        TextView descriptionOther, creditOther, lostOther, onfieldOther;

        public MyViewHolder(View itemView) {
            super(itemView);

            valueOpening = (LinearLayout) itemView.findViewById(R.id.valueOpening);
            valueDomestic = (LinearLayout) itemView.findViewById(R.id.valueDomestic);
            valueReceive = (LinearLayout) itemView.findViewById(R.id.valueReceive);
            valueSend = (LinearLayout) itemView.findViewById(R.id.valueSend);
            valueOther = (LinearLayout) itemView.findViewById(R.id.valueOther);

            descriptionOpening = (TextView) itemView.findViewById(R.id.descriptionOpening);
            fullOpening = (TextView) itemView.findViewById(R.id.fullOpening);
            emptyOpening = (TextView) itemView.findViewById(R.id.emptyOpening);
            defectiveOpening = (TextView) itemView.findViewById(R.id.defectiveOpening);

            descriptionDomestic = (TextView) itemView.findViewById(R.id.descriptionDomestic);
            deliveryDomestic = (TextView) itemView.findViewById(R.id.deliveryDomestic);
            svDomestic = (TextView) itemView.findViewById(R.id.svDomestic);
            dbcDomestic = (TextView) itemView.findViewById(R.id.dbcDomestic);
            defectiveDomestic = (TextView) itemView.findViewById(R.id.defectiveDomestic);
            lostDomestic = (TextView) itemView.findViewById(R.id.lostDomestic);

            descriptionReceive = (TextView) itemView.findViewById(R.id.descriptionReceive);
            soundReceive = (TextView) itemView.findViewById(R.id.soundReceive);
            lostReceive = (TextView) itemView.findViewById(R.id.lostReceive);

            descriptionSend = (TextView) itemView.findViewById(R.id.descriptionSend);
            soundSend = (TextView) itemView.findViewById(R.id.soundSend);
            defectiveSend = (TextView) itemView.findViewById(R.id.defectiveSend);

            descriptionOther = (TextView) itemView.findViewById(R.id.descriptionOther);
            creditOther = (TextView) itemView.findViewById(R.id.creditOther);
            lostOther = (TextView) itemView.findViewById(R.id.lostOther);
            onfieldOther = (TextView) itemView.findViewById(R.id.onfieldOther);
        }
    }
}
