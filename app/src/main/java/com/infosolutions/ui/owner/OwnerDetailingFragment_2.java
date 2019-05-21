package com.infosolutions.ui.owner;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolutions.adapter.AdapterOwnerDetailsOpening;
import com.infosolutions.evita.R;
import com.infosolutions.model.ModelOwnerDetailsOpening;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerDetailingFragment_2 extends Fragment {


    private String response, selected_tab;
    private int position;
    String description, defective, opening_full, opening_empty;

    List<ModelOwnerDetailsOpening> list;

    public static OwnerDetailingFragment_2 newInstance() {
        return new OwnerDetailingFragment_2();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_owner_detailing_fragment_2, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        TextView textView = view.findViewById(R.id.commonTextView);
        response = getArguments().getString("response");
        position = getArguments().getInt("position");
        list = new ArrayList<ModelOwnerDetailsOpening>();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {

            JSONObject objectDetailing = new JSONObject(response);

            if (objectDetailing.has("OPENING_STOCKS_GODOWN_WISE") &&
                    objectDetailing.optJSONArray("OPENING_STOCKS_GODOWN_WISE").length() > 0) {

                JSONArray openingArray = objectDetailing.optJSONArray("OPENING_STOCKS_GODOWN_WISE");
                int arrayLength = openingArray.length();
                //String godown_name = openingArray.optJSONObject(position).optString("valueArray");
                //textView.setText(godown_name);

                if(arrayLength > 0) {
                    for(int i = 0; i < arrayLength; i++) {

                        if (openingArray.optJSONObject(i).has("valueArray") && openingArray.optJSONObject(i).has("DISPLAY_NAME")){

                            JSONArray openArray = openingArray.optJSONObject(i).optJSONArray("valueArray");
                            int openArrayLength = openArray.length();
                            String godown_name = openingArray.optJSONObject(i).optString("DISPLAY_NAME");

                            if(openArrayLength > 0) {
                                for(int j = 0; j < openArrayLength; j++) {

                                    switch (godown_name) {

                                        case "PRAGATI GAS":
                                            description = openArray.optJSONObject(j).optString("DESCRIPTION");
                                            defective = openArray.optJSONObject(j).optString("DEFECTIVE");
                                            opening_full = openArray.optJSONObject(j).optString("OPENING_FULL");
                                            opening_empty = openArray.optJSONObject(j).optString("OPENING_EMPTY");
                                            ModelOwnerDetailsOpening openingPra = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                            list.add(openingPra);
                                            break;

                                            case "VIRAR EAST":
                                                description = openArray.optJSONObject(j).optString("DESCRIPTION");
                                                defective = openArray.optJSONObject(j).optString("DEFECTIVE");
                                                opening_full = openArray.optJSONObject(j).optString("OPENING_FULL");
                                                opening_empty = openArray.optJSONObject(j).optString("OPENING_EMPTY");
                                                ModelOwnerDetailsOpening openingVirarE = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                                list.add(openingVirarE);
                                                break;

                                        case "VIRAR WEST":
                                            description = openArray.optJSONObject(j).optString("DESCRIPTION");
                                            defective = openArray.optJSONObject(j).optString("DEFECTIVE");
                                            opening_full = openArray.optJSONObject(j).optString("OPENING_FULL");
                                            opening_empty = openArray.optJSONObject(j).optString("OPENING_EMPTY");
                                            ModelOwnerDetailsOpening openingVirarW = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                            list.add(openingVirarW);
                                            break;

                                         case "YASHWANT":
                                            description = openArray.optJSONObject(j).optString("DESCRIPTION");
                                            defective = openArray.optJSONObject(j).optString("DEFECTIVE");
                                            opening_full = openArray.optJSONObject(j).optString("OPENING_FULL");
                                            opening_empty = openArray.optJSONObject(j).optString("OPENING_EMPTY");
                                            ModelOwnerDetailsOpening openingYash = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                            list.add(openingYash);
                                            break;

                                    }

                                    /*if(godown_name.equalsIgnoreCase("PRAGATI GAS")) {

                                    } else if(godown_name.equalsIgnoreCase("VIRAR EAST")) {
                                        description = openArray.optJSONObject(j).optString("DESCRIPTION");
                                        defective = openArray.optJSONObject(j).optString("DEFECTIVE");
                                        opening_full = openArray.optJSONObject(j).optString("OPENING_FULL");
                                        opening_empty = openArray.optJSONObject(j).optString("OPENING_EMPTY");
                                        ModelOwnerDetailsOpening opening = new ModelOwnerDetailsOpening(description, defective, opening_full, opening_empty);
                                        list.add(opening);
                                    }*/
                                }
                            }
                        }
                    }

                    AdapterOwnerDetailsOpening adapter = new AdapterOwnerDetailsOpening(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
