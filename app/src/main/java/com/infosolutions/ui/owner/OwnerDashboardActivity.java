package com.infosolutions.ui.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.infosolutions.adapter.OwnerDashboardAdapter;
import com.infosolutions.evita.R;
import com.infosolutions.evita.databinding.ActivityOwnerdashboardBinding;
import com.infosolutions.model.OwnerDashBoardModel;
import com.infosolutions.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class OwnerDashboardActivity extends AppCompatActivity {

    /*@BindView(R.id.cardView_godownReport)
    CardView cardView_godownReport;
    @BindView(R.id.cardView_CommercialReport)
    CardView cardView_CommercialReport;*/

    public static String owner_resp;


    private ActivityOwnerdashboardBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_ownerdashboard);
        populateData();

        setupToolbar();

        /*setContentView(R.layout.activity_owner_dashboard);
        ButterKnife.bind(this);
        setupToolbar();*/

        Intent intent = getIntent();
        owner_resp = intent.getStringExtra("owner_resp");


        /*cardView_godownReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerDashboardActivity.this,GodownReportForOwner.class);
                intent.putExtra("owner_resp", owner_resp);
                startActivity(intent);
            }
        });*/
    }

    private void populateData() {
        List<OwnerDashBoardModel> dataModelList = new ArrayList<>();

        dataModelList.add(new OwnerDashBoardModel(R.drawable.ic_account_balance_black_24dp, getString(R.string.GodownReport)));
        dataModelList.add(new OwnerDashBoardModel(R.drawable.ic_location_city_black_24dp, getString(R.string.CommercialReport)));

        OwnerDashboardAdapter myRecyclerViewAdapter = new OwnerDashboardAdapter(this, dataModelList);
        mBinding.setMyAdapter(myRecyclerViewAdapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Admin Dashboard");

        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(this);
        builder1.setTitle("Logout Owner");
        builder1.setMessage("Are you sure you want to logout Owner?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Logout",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(OwnerDashboardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
