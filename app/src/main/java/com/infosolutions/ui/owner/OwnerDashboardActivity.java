package com.infosolutions.ui.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolutions.evita.R;
import com.infosolutions.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerDashboardActivity extends AppCompatActivity {

    @BindView(R.id.cardView_godownReport)
    CardView cardView_godownReport;
    @BindView(R.id.cardView_CommercialReport)
    CardView cardView_CommercialReport;

    private String owner_resp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        ButterKnife.bind(this);
        setupToolbar();

        Intent intent = getIntent();
        owner_resp = intent.getStringExtra("owner_resp");

        cardView_godownReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OwnerDashboardActivity.this,GodownReportForOwner.class);
                intent.putExtra("owner_resp", owner_resp);
                startActivity(intent);
            }
        });

        cardView_CommercialReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OwnerDashboardActivity.this,"Development In Progress....",Toast.LENGTH_SHORT).show();
            }
        });
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
