package com.infosolutions.ui.owner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolutions.adapter.AdapterOwnerDetailsHeader;
import com.infosolutions.adapter.AdapterOwnerDetailsOpening;
import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsHeader;
import com.infosolutions.model.ModelOwnerDetailsOpening;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.infosolutions.ui.owner.OwnerDetailingActivity_2.response;

public class OwnerDetailingActivity_new extends AppCompatActivity implements AdapterOwnerDetailsHeader.OnItemListener {

    String godownTitle;
    RecyclerView recyclerViewHeader, recyclerViewContent;
    LinearLayoutManager layoutManagerHeader, layoutManagerContent;
    AdapterOwnerDetailsHeader adapterHeader;
    AdapterOwnerDetailsOpening adapterOpening;
    List<ModelOwnerDetailsHeader> list;

    Intent intent;
    String response, title;
    Toolbar toolbar;
    TextView mTitle;
    String description, defective, opening_full, opening_empty;
    List<ModelOwnerDetailsOpening> insideList;

    JSONObject rootObject;
    JSONArray parentArray, childArray;
    ModelOwnerDetailsHeader model;
    int arrayLength;

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
        setContentView(R.layout.activity_owner_detailing_new);

        list = new ArrayList<>();
        insideList = new ArrayList<>();

        recyclerViewHeader = (RecyclerView) findViewById(R.id.recyclerViewHeader);
        recyclerViewHeader.setHasFixedSize(true);
        layoutManagerHeader = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHeader.setLayoutManager(layoutManagerHeader);
        //RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        //recyclerView.addItemDecoration(divider);
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        recyclerViewContent = (RecyclerView) findViewById(R.id.recyclerViewContent);
        recyclerViewContent.setHasFixedSize(true);
        layoutManagerContent = new LinearLayoutManager(this);
        recyclerViewContent.setLayoutManager(layoutManagerContent);

        intent     =  getIntent();
        response   =  intent.getStringExtra("response");
        title      =  intent.getStringExtra("layout_type");
        toolbar   = findViewById(R.id.toolbar);

        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!response.equalsIgnoreCase("")){
            try{
                createStockDetailing(response);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private void createStockDetailing(String response) throws JSONException {

            rootObject = new JSONObject(response);

            if(rootObject != null) {

                if(title.equalsIgnoreCase("OPENING")) {

                    if (rootObject.has("OPENING_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("OPENING_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("OPENING_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength > 0){

                            for (int i = 0; i < arrayLength; i++) {

                                if (parentArray.optJSONObject(i).has("valueArray") &&
                                        parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                } else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("OTHER")) {

                    if(rootObject.has("OTHER_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("OTHER_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("OTHER_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength > 0) {

                            for (int i = 0; i < arrayLength; i++) {

                                if (parentArray.optJSONObject(i).has("valueArray") &&
                                        parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("DOMESTIC")) {

                    if(rootObject.has("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE");
                        arrayLength =parentArray.length();

                        if (arrayLength>0) {

                            for (int i = 0; i < arrayLength; i++) {

                                if (parentArray.optJSONObject(i).has("valueArray") &&
                                        parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        }else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if (title.equalsIgnoreCase("COMMERCIAL")) {

                    if(rootObject.has("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength > 0){

                            for (int i=0; i<arrayLength; i++) {
                                if (parentArray.optJSONObject(i).has("valueArray") && parentArray.optJSONObject(i).has("DISPLAY_NAME")){
                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("RECEIVING")) {

                    if(rootObject.has("RECEVING_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("RECEVING_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("RECEVING_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength > 0) {

                            for (int i = 0; i < arrayLength; i++) {
                                if (parentArray.optJSONObject(i).has("valueArray") && parentArray.optJSONObject(i).has("DISPLAY_NAME")){
                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("SENDING")) {
                    if(rootObject.has("SENDING_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("SENDING_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("SENDING_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength>0) {

                            for (int i = 0; i < arrayLength; i++) {
                                if (parentArray.optJSONObject(i).has("valueArray") && parentArray.optJSONObject(i).has("DISPLAY_NAME")){
                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("TV-DETAILS")) {

                    if(rootObject.has("TV_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("TV_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("TV_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength>0) {

                            for (int i = 0; i < arrayLength; i++) {
                                if (parentArray.optJSONObject(i).has("valueArray") && parentArray.optJSONObject(i).has("DISPLAY_NAME")){
                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                }else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                } else if(title.equalsIgnoreCase("CLOSING")) {

                    if(rootObject.has("CLOSING_STOCKS_GODOWN_WISE") &&
                            rootObject.optJSONArray("CLOSING_STOCKS_GODOWN_WISE").length() > 0) {

                        parentArray = rootObject.optJSONArray("CLOSING_STOCKS_GODOWN_WISE");
                        arrayLength = parentArray.length();

                        if (arrayLength>0) {

                            for (int i = 0; i < arrayLength; i++) {
                                if (parentArray.optJSONObject(i).has("valueArray") && parentArray.optJSONObject(i).has("DISPLAY_NAME")){
                                    childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                    godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                    ModelOwnerDetailsHeader model = new ModelOwnerDetailsHeader(godownTitle);
                                    list.add(model);

                                } else {
                                    Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                                }
                            }
                            adapterHeader = new AdapterOwnerDetailsHeader(list, this, this);
                            recyclerViewHeader.setAdapter(adapterHeader);
                        } else {showError();}
                    } else {
                        Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    private void showError() {
        TextView tvError = findViewById(R.id.tvError);
        tvError.setText(getResources().getString(R.string.no_record_found));
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(int position) {

        ModelOwnerDetailsHeader header = list.get(position);
        String itemName = header.getDISPLAY_NAME();
        //Toast.makeText(this, "Clicked : " + itemName, Toast.LENGTH_LONG).show();

        insideList.clear();

        try {

            if(title.equalsIgnoreCase("OPENING")) {

                if (rootObject.has("OPENING_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("OPENING_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("OPENING_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {
                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("DOMESTIC")) {

                if(rootObject.has("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("DOMESTIC_DELIVERY_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("COMMERCIAL")) {

                if(rootObject.has("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("COMMERCIAL_DELIVERY_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("RECEIVING")) {

                if(rootObject.has("RECEVING_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("RECEVING_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("RECEVING_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("SENDING")) {

                if(rootObject.has("SENDING_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("SENDING_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("SENDING_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("CLOSING")) {

                if(rootObject.has("CLOSING_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("CLOSING_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("CLOSING_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            } else if(title.equalsIgnoreCase("OTHER")) {

                if(rootObject.has("OTHER_STOCKS_GODOWN_WISE") &&
                        rootObject.optJSONArray("OTHER_STOCKS_GODOWN_WISE").length() > 0) {

                    parentArray = rootObject.optJSONArray("OTHER_STOCKS_GODOWN_WISE");
                    arrayLength = parentArray.length();

                    if (arrayLength > 0){

                        for (int i = 0; i < arrayLength; i++) {

                            if (parentArray.optJSONObject(i).has("valueArray") &&
                                    parentArray.optJSONObject(i).has("DISPLAY_NAME")){

                                childArray = parentArray.optJSONObject(i).optJSONArray("valueArray");
                                godownTitle = parentArray.optJSONObject(i).optString("DISPLAY_NAME");

                                if(godownTitle.equalsIgnoreCase(itemName)) {

                                    for(int j=0; j<childArray.length(); j++) {
                                        description = childArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = childArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = childArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = childArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        insideList.add(openingPra);
                                    }
                                    adapterOpening = new AdapterOwnerDetailsOpening(this, insideList);
                                    recyclerViewContent.setAdapter(adapterOpening);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}