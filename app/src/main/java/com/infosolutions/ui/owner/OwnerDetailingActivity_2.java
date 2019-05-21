package com.infosolutions.ui.owner;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolutions.evita.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OwnerDetailingActivity_2 extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout mTabLayout;

    public static String response;
    String title;
    public static String godownTitle;
    public static String selectedTab;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detailing_2);

        Intent intent     =  getIntent();
        response   =  intent.getStringExtra("response");
        title      =  intent.getStringExtra("layout_type");
        Toolbar toolbar   = findViewById(R.id.toolbar);
        TextView mTitle   = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!response.equalsIgnoreCase("")){
            initViews();
        }
    }

    private void initViews() {

        viewPager = findViewById(R.id.viewpager);
        mTabLayout =  findViewById(R.id.tabs);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.colorBlack), getResources().getColor(R.color.colorAccent));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                selectedTab = tab.getText().toString();
                Toast.makeText(OwnerDetailingActivity_2.this, "Selected : " + selectedTab, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectedTab = tab.getText().toString();
                Toast.makeText(OwnerDetailingActivity_2.this, "Reselected : " + selectedTab, Toast.LENGTH_SHORT).show();
            }
        });

        //setDynamicFragmentToTabLayout();
        try {
            createStockDetailing(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDynamicFragmentToTabLayout() {
        for (int i = 0; i < 10; i++) {

            mTabLayout.addTab(mTabLayout.newTab().setText("Category: " + i));
        }

        OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(mDynamicFragmentAdapter);
        viewPager.setCurrentItem(0);
    }

    private void createStockDetailing(String response) throws JSONException {

        JSONObject objectDetailing = new JSONObject(response);

        if (objectDetailing.has("OPENING_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("OPENING_STOCKS_GODOWN_WISE").length() > 0) {

            JSONArray openingArray = objectDetailing.optJSONArray("OPENING_STOCKS_GODOWN_WISE");
            int arrayLength = openingArray.length();

            if (arrayLength > 0){

                for (int i = 0; i < arrayLength; i++)
                {
                    if (openingArray.optJSONObject(i).has("valueArray") && openingArray.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray openArray = openingArray.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = openingArray.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));

                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }

                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);

            }else {showError();}
        } else if(objectDetailing.has("OTHER_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("OTHER_STOCKS_GODOWN_WISE").length() > 0) {

            JSONArray arrayDetailing = objectDetailing.optJSONArray("OTHER_STOCKS_GODOWN_WISE");
            int arrayLength = arrayDetailing.length();

            if (arrayLength > 0) {
                for (int i = 0; i < arrayLength; i++) {

                    if (arrayDetailing.optJSONObject(i).has("valueArray") && arrayDetailing.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray otherArray = arrayDetailing.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arrayDetailing.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));

                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE") &&
                objectDetailing != null && objectDetailing.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

            JSONArray arrayDomestic = objectDetailing.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE");
            int arrayLength =arrayDomestic.length();

            if (arrayLength>0) {
                for (int i = 0; i < arrayLength; i++) {

                    if (arrayDomestic.optJSONObject(i).has("valueArray") && arrayDomestic.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray domesticArray = arrayDomestic.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arrayDomestic.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));

                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

            JSONArray arrayCommercial = objectDetailing.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE");
            int arrayLength = arrayCommercial.length();

            if (arrayLength>0){
                for (int i=0; i<arrayLength; i++) {
                    if (arrayCommercial.optJSONObject(i).has("valueArray") && arrayCommercial.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray commercialArray = arrayCommercial.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arrayCommercial.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));
                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("RECEVING_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("RECEVING_STOCKS_GODOWN_WISE").length() > 0) {
            JSONArray arrayReceive = objectDetailing.optJSONArray("RECEVING_STOCKS_GODOWN_WISE");
            int arraylength = arrayReceive.length();

            if (arraylength>0) {
                for (int i = 0; i < arraylength; i++) {
                    if (arrayReceive.optJSONObject(i).has("valueArray") && arrayReceive.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray commercialArray = arrayReceive.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arrayReceive.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));
                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("SENDING_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("SENDING_STOCKS_GODOWN_WISE").length() > 0) {
            JSONArray arraySending = objectDetailing.optJSONArray("SENDING_STOCKS_GODOWN_WISE");
            int arrayLength = arraySending.length();

            if (arrayLength>0) {
                for (int i = 0; i < arrayLength; i++) {
                    if (arraySending.optJSONObject(i).has("valueArray") && arraySending.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray openingArray = arraySending.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arraySending.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));
                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("TV_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("TV_STOCKS_GODOWN_WISE").length() > 0) {
            JSONArray jsonArrayTV = objectDetailing.optJSONArray("TV_STOCKS_GODOWN_WISE");
            int arrayLength = jsonArrayTV.length();

            if (arrayLength>0) {
                for (int i = 0; i < arrayLength; i++) {
                    if (jsonArrayTV.optJSONObject(i).has("valueArray") && jsonArrayTV.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray tvArray = jsonArrayTV.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = jsonArrayTV.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));
                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        } else if(objectDetailing.has("CLOSING_STOCKS_GODOWN_WISE") &&
                objectDetailing.optJSONArray("CLOSING_STOCKS_GODOWN_WISE").length() > 0) {
            JSONArray arrayClosing = objectDetailing.optJSONArray("CLOSING_STOCKS_GODOWN_WISE");
            int arrayLength = arrayClosing.length();

            if (arrayLength>0) {
                for (int i = 0; i < arrayLength; i++) {
                    if (arrayClosing.optJSONObject(i).has("valueArray") && arrayClosing.optJSONObject(i).has("DISPLAY_NAME")){
                        JSONArray openingArray = arrayClosing.optJSONObject(i).optJSONArray("valueArray");
                        godownTitle = arrayClosing.optJSONObject(i).optString("DISPLAY_NAME");
                        mTabLayout.addTab(mTabLayout.newTab().setText(godownTitle));
                    }else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
                OwnerDetailingFragment_2_Adapter mDynamicFragmentAdapter = new OwnerDetailingFragment_2_Adapter(getSupportFragmentManager(), mTabLayout.getTabCount());
                viewPager.setAdapter(mDynamicFragmentAdapter);
                viewPager.setCurrentItem(0);
            }else {showError();}
        }
    }

    private void showError() {
        TextView tvError = findViewById(R.id.tvError);
        tvError.setText(getResources().getString(R.string.no_record_found));
        tvError.setVisibility(View.VISIBLE);
    }

}
