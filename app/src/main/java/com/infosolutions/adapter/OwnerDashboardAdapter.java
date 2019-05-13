package com.infosolutions.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.infosolutions.evita.R;
import com.infosolutions.evita.databinding.OwnerDashboardRowItemBinding;
import com.infosolutions.interfaces.ClickListener;
import com.infosolutions.model.OwnerDashBoardModel;
import com.infosolutions.ui.owner.GodownReportForOwner;
import com.infosolutions.ui.owner.OwnerDashboardActivity;

import java.util.List;

public class OwnerDashboardAdapter extends RecyclerView.Adapter<OwnerDashboardAdapter.MyViewHolder> implements ClickListener {

    private Context context;
    private List<OwnerDashBoardModel> list;

    public OwnerDashboardAdapter(Context context, List<OwnerDashBoardModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        OwnerDashboardRowItemBinding rowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.owner_dashboard_row_item, parent, false);
        return new MyViewHolder(rowItemBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OwnerDashBoardModel dashBoardModel = list.get(position);
        holder.rowItemBinding.setOwnerdashboardmodel(dashBoardModel);
        holder.rowItemBinding.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void cardClicked(OwnerDashBoardModel model) {

        if(model.menuname.startsWith("Godown")){
            Intent intent=new Intent(context, GodownReportForOwner.class);
            intent.putExtra("owner_resp", OwnerDashboardActivity.owner_resp);
            context.startActivity(intent);
        } else if(model.menuname.equalsIgnoreCase("Commercial Reports")){
            Toast.makeText(context, "You clicked " + model.menuname, Toast.LENGTH_SHORT).show();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        OwnerDashboardRowItemBinding rowItemBinding;

        public MyViewHolder(OwnerDashboardRowItemBinding itemView) {
            super(itemView.getRoot());
            rowItemBinding = itemView;
        }
    }
}
