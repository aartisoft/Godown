package com.infosolutions.ui.user.stock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.infosolutions.adapter.AdapterStockDetails;
import com.infosolutions.adapter.AdapterStockDetailsHeader;
import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsHeader;
import com.infosolutions.model.ModelStockDetails;
import com.infosolutions.network.Constants;
import com.infosolutions.network.ResponseListener;
import com.infosolutions.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockDetails extends AppCompatActivity implements ResponseListener, AdapterStockDetailsHeader.OnItemListener {

    private String GODOWN_ID;
    private String RESP_MSG = "";
    Toolbar toolbar;
    TextView mTitle, mSelectedTab;

    public static String itemName;

    RecyclerView recyclerViewHeader;
    AdapterStockDetailsHeader adapterHeader;
    LinearLayoutManager layoutManagerHeader;
    List<ModelOwnerDetailsHeader> list;
    LinearLayout headerOpening, headerClosing, headerReceive, headerSend, headerDelivery, headerOther;

    JSONObject jsonObject;
    String responseCode;

    RecyclerView recyclerViewContent;
    AdapterStockDetails adapterStockDetails;
    LinearLayoutManager layoutManagerDetails;
    List<ModelStockDetails> listDetails;

    String SOUND, DESCRIPTION, LOST_TRUCK_RECEVING, CREDIT, ON_FIELD, LOST_DELIVERY_CYLINDERS, DEFECTIVE, CLOSING_EMPTY, CLOSING_FULL,
            TRUCK_DEFECTIVE, OPENING_FULL, OPENING_EMPTY, SV, DELIVERY_EMPTY, DELIVERY_FULL, TV;

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
        setContentView(R.layout.activity_stock_details);

        VolleySingleton.getInstance(getApplicationContext()).addResponseListener(VolleySingleton.CallType.STOCK_REPORT, this);
        setGODOWN_ID(Constants.getSharedPrefWithKEY(getApplicationContext(),Constants.KEY_GODOWN));

        mSelectedTab = (TextView) findViewById(R.id.selectedTab);

        recyclerViewHeader = (RecyclerView) findViewById(R.id.recyclerViewHeader);
        recyclerViewHeader.setHasFixedSize(true);
        layoutManagerHeader = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHeader.setLayoutManager(layoutManagerHeader);

        recyclerViewContent = (RecyclerView) findViewById(R.id.recyclerViewContent);
        recyclerViewContent.setHasFixedSize(true);
        layoutManagerDetails = new LinearLayoutManager(this);
        recyclerViewContent.setLayoutManager(layoutManagerDetails);
        listDetails = new ArrayList<>();

        list = new ArrayList<>();
        list.add(new ModelOwnerDetailsHeader("OPENING STOCKS"));
        list.add(new ModelOwnerDetailsHeader("LOAD RECEIVED"));
        list.add(new ModelOwnerDetailsHeader("DELIVERY"));
        list.add(new ModelOwnerDetailsHeader("LOAD SEND"));
        list.add(new ModelOwnerDetailsHeader("CLOSING STOCKS"));
        list.add(new ModelOwnerDetailsHeader("OTHER STOCKS"));

        adapterHeader = new AdapterStockDetailsHeader(this, this, list);
        recyclerViewHeader.setAdapter(adapterHeader);

        toolbar    = findViewById(R.id.toolbar);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.stockdetailslabel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headerOpening  = (LinearLayout) findViewById(R.id.headerOpening);
        headerClosing = (LinearLayout) findViewById(R.id.headerClosing);
        headerReceive  = (LinearLayout) findViewById(R.id.headerReceive);
        headerSend     = (LinearLayout) findViewById(R.id.headerSend);
        headerDelivery     = (LinearLayout) findViewById(R.id.headerDelivery);
        headerOther    = (LinearLayout) findViewById(R.id.headerOther);

        checkInternet();
    }

    public void checkInternet() {
        if (Constants.isNetworkAvailable(getApplicationContext())){
            //showProgressDialog();
            fetchNetworkCall();
        }else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public String getGODOWN_ID() { return GODOWN_ID;}
    public void setGODOWN_ID(String GODOWN_ID) {
        this.GODOWN_ID = GODOWN_ID;
    }

    private void fetchNetworkCall() {

        VolleySingleton.getInstance(getApplicationContext())
                .apiGetStockReport(VolleySingleton.CallType.STOCK_REPORT,
                        Constants.STOCK_REPORT, "GETSTOCKSNEW", getGODOWN_ID());
    }

    @Override
    public void onSuccess(VolleySingleton.CallType type, String response) {

        //hideProgressDialog();

        try {

            jsonObject = new JSONObject(response);
            responseCode = jsonObject.optString("ResponseCode");

            if (responseCode.equalsIgnoreCase("200")) {

                /*openingStock(jsonObject.optString("OPENING_STOCKS"));
                loadsTextview.setVisibility(View.VISIBLE);
                loadsReceived(jsonObject.optString("LOAD_RECEVIED"));
                divider.setVisibility(View.VISIBLE);
                loadsSend(jsonObject.optString("LOAD_SEND"));
                loadDelivery(jsonObject.optString("DELIVERY"));
                loadClosingStock(jsonObject.optString("CLOSING_STOCKS"));
                loadOtherStock(jsonObject.optString("OTHER_STOCKS"));*/

            } else {
                //RESP_MSG = jsonObject.optString("RESP_MSG");
                RESP_MSG = jsonObject.optString("responseMessage");
                Toast.makeText(StockDetails.this, RESP_MSG, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex)
        {
            Toast.makeText(StockDetails.this,
                    R.string.invalid_data, Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onFailure(VolleySingleton.CallType type, VolleyError error) {

        //hideProgressDialog();

        Toast.makeText(StockDetails.this,
                R.string.server_not_responding,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {

        ModelOwnerDetailsHeader header = list.get(position);
        itemName = header.getDISPLAY_NAME();
        //Toast.makeText(this, "Tab Name : " + itemName, Toast.LENGTH_SHORT).show();

        String sourceString =  "You have selected : " + "<b><font color='#800000'>" + itemName + "</font></b>";
        mSelectedTab.setText(Html.fromHtml(sourceString));

        //listDetails.clear();

        if(itemName.equalsIgnoreCase("OPENING STOCKS")) {

            Toast.makeText(this, "Tab Name : " + itemName, Toast.LENGTH_SHORT).show();

            headerOpening.setVisibility(View.VISIBLE);
            headerClosing.setVisibility(View.GONE);
            headerReceive.setVisibility(View.GONE);
            headerSend.setVisibility(View.GONE);
            headerDelivery.setVisibility(View.GONE);
            headerOther.setVisibility(View.GONE);

            loadOpening(jsonObject.optString("OPENING_STOCKS"));

        } else if(itemName.equalsIgnoreCase("CLOSING STOCKS")) {

            headerClosing.setVisibility(View.VISIBLE);
            headerOpening.setVisibility(View.GONE);
            headerReceive.setVisibility(View.GONE);
            headerSend.setVisibility(View.GONE);
            headerDelivery.setVisibility(View.GONE);
            headerOther.setVisibility(View.GONE);

            loadClosing(jsonObject.optString("CLOSING_STOCKS"));

        } else if(itemName.equalsIgnoreCase("LOAD RECEIVED")) {

            headerReceive.setVisibility(View.VISIBLE);
            headerOpening.setVisibility(View.GONE);
            headerClosing.setVisibility(View.GONE);
            headerSend.setVisibility(View.GONE);
            headerDelivery.setVisibility(View.GONE);
            headerOther.setVisibility(View.GONE);

            loadsReceived(jsonObject.optString("LOAD_RECEVIED"));

        } else if(itemName.equalsIgnoreCase("LOAD SEND")) {

            headerSend.setVisibility(View.VISIBLE);
            headerOpening.setVisibility(View.GONE);
            headerClosing.setVisibility(View.GONE);
            headerReceive.setVisibility(View.GONE);
            headerDelivery.setVisibility(View.GONE);
            headerOther.setVisibility(View.GONE);

            loadsSend(jsonObject.optString("LOAD_SEND"));

        } else if(itemName.equalsIgnoreCase("DELIVERY")) {

            headerDelivery.setVisibility(View.VISIBLE);
            headerOpening.setVisibility(View.GONE);
            headerClosing.setVisibility(View.GONE);
            headerReceive.setVisibility(View.GONE);
            headerSend.setVisibility(View.GONE);
            headerOther.setVisibility(View.GONE);

            loadDelivery(jsonObject.optString("DELIVERY"));

        } else if(itemName.equalsIgnoreCase("OTHER STOCKS")) {

            headerOther.setVisibility(View.VISIBLE);
            headerOpening.setVisibility(View.GONE);
            headerClosing.setVisibility(View.GONE);
            headerReceive.setVisibility(View.GONE);
            headerSend.setVisibility(View.GONE);
            headerDelivery.setVisibility(View.GONE);

            loadOther(jsonObject.optString("OTHER_STOCKS"));
        }
    }

    private void loadOpening(String loadOpeningStock){

        listDetails.clear();

        try {

            JSONArray arrayOpening = new JSONArray(loadOpeningStock);
            int openingLength = arrayOpening.length();

            if (openingLength > 0){

                for (int i = 0; i<arrayOpening.length(); i++) {

                    JSONObject jsonReceived = arrayOpening.optJSONObject(i);

                    DEFECTIVE     =  jsonReceived.optString("DEFECTIVE");
                    DESCRIPTION   =  jsonReceived.optString("DESCRIPTION");
                    OPENING_FULL  =  jsonReceived.optString("OPENING_FULL");
                    OPENING_EMPTY =  jsonReceived.optString("OPENING_EMPTY");

                    listDetails.add(ModelStockDetails.setOpening(DESCRIPTION, DEFECTIVE, OPENING_FULL, OPENING_EMPTY));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Invalid data received opening field", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadClosing(String loadClosingStock){

        listDetails.clear();

        try {

            JSONArray arrayClosing = new JSONArray(loadClosingStock);
            int closingLength = arrayClosing.length();

            if (closingLength > 0){

                for (int i = 0; i<arrayClosing.length(); i++) {

                    JSONObject jsonReceived = arrayClosing.optJSONObject(i);

                    DESCRIPTION =jsonReceived.optString("DESCRIPTION");
                    CLOSING_FULL =jsonReceived.optString("CLOSING_FULL");
                    CLOSING_EMPTY =jsonReceived.optString("CLOSING_EMPTY");
                    DEFECTIVE =jsonReceived.optString("DEFECTIVE");

                    listDetails.add(ModelStockDetails.setClosing(DESCRIPTION, DEFECTIVE, CLOSING_FULL, CLOSING_EMPTY));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Invalid data received closing field", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadsReceived(String load_received) {

        listDetails.clear();

        try {

            JSONArray arrayReceived = new JSONArray(load_received);
            int receiveLength = arrayReceived.length();

            if (receiveLength > 0){

                for (int i = 0; i<arrayReceived.length(); i++){

                    JSONObject jsonReceived = arrayReceived.optJSONObject(i);

                    DESCRIPTION = jsonReceived.optString("DESCRIPTION");
                    SOUND = jsonReceived.optString("SOUND");
                    LOST_TRUCK_RECEVING = jsonReceived.optString("LOST_TRUCK_RECEVING");

                    listDetails.add(ModelStockDetails.setReceived(DESCRIPTION, SOUND, LOST_TRUCK_RECEVING));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e)
        {
            Toast.makeText(this, R.string.invalid_data_received, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadsSend(String load_send){

        listDetails.clear();

        try {

            JSONArray arraySend = new JSONArray(load_send);
            int sendLength = arraySend.length();

            if (sendLength > 0){

                for (int i = 0; i<arraySend.length(); i++){

                    JSONObject jsonSend = arraySend.optJSONObject(i);

                    DESCRIPTION = jsonSend.optString("DESCRIPTION");
                    SOUND = jsonSend.optString("SOUND");
                    TRUCK_DEFECTIVE = jsonSend.optString("TRUCK_DEFECTIVE");

                    listDetails.add(ModelStockDetails.setSend(DESCRIPTION, SOUND, TRUCK_DEFECTIVE));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e)
        {
            Toast.makeText(this, R.string.invalid_data_received, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadDelivery(String loadDelivery){

        listDetails.clear();

        try {

            JSONArray arrayDelivery = new JSONArray(loadDelivery);
            int deliveryLength = arrayDelivery.length();

            if (deliveryLength > 0){

                for (int i = 0; i<arrayDelivery.length(); i++){

                    JSONObject jsonReceived = arrayDelivery.optJSONObject(i);

                    DESCRIPTION = jsonReceived.optString("DESCRIPTION");
                    DEFECTIVE = jsonReceived.optString("DEFECTIVE");
                    SV = jsonReceived.optString("SV");
                    TV = jsonReceived.optString("TV");
                    DELIVERY_FULL = jsonReceived.optString("DELIVERY_FULL");
                    DELIVERY_EMPTY = jsonReceived.optString("DELIVERY_EMPTY");

                    listDetails.add(ModelStockDetails.setDelivery(DESCRIPTION, DEFECTIVE, SV, TV, DELIVERY_FULL, DELIVERY_EMPTY));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, R.string.invalid_data_received, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadOther(String loadOtherStock){

        listDetails.clear();

        try {

            JSONArray arrayOther = new JSONArray(loadOtherStock);
            int otherLength = arrayOther.length();

            if (otherLength > 0){

                for (int i = 0; i<arrayOther.length(); i++) {

                    JSONObject jsonReceived = arrayOther.optJSONObject(i);

                    DESCRIPTION = jsonReceived.optString("DESCRIPTION");
                    CREDIT = jsonReceived.optString("CREDIT");
                    LOST_DELIVERY_CYLINDERS = jsonReceived.optString("LOST_DELIVERY_CYLINDERS");
                    ON_FIELD = jsonReceived.optString("ON_FIELD");

                    listDetails.add(ModelStockDetails.setOther(DESCRIPTION, CREDIT, ON_FIELD, LOST_DELIVERY_CYLINDERS));
                }
                adapterStockDetails = new AdapterStockDetails(this, listDetails);
                recyclerViewContent.setAdapter(adapterStockDetails);
            } else {
                Toast.makeText(this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Invalid data received other stock field", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
