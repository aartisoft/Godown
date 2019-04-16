package com.infosolutions.ui.user.truckdelivery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infosolutions.database.DatabaseHelper;
import com.infosolutions.database.ProductDB;
import com.infosolutions.database.PurchaseERVProduct;
import com.infosolutions.database.TruckSendDetailsDB;
import com.infosolutions.database.VehicleDB;
import com.infosolutions.evita.R;
import com.infosolutions.network.Constants;
import com.infosolutions.ui.user.truckdelivery.TruckDeliveryActivity;
import com.infosolutions.utils.AppSettings;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.infosolutions.network.Constants.getSharedPrefWithKEY;
import static java.lang.Integer.parseInt;


public class TruckSendOwnFragment extends Fragment {

    EditText etErvNumber, etQuantity;
    android.widget.Button btnSelectTruckNumber, generateET;
    TextView tvSelectedTruck;
    TextInputLayout input_layout_erv;
    private LinearLayout myLinearLay;
    private ScrollView scrollView;
    AppCompatButton btnSubmit;
    private int USER_ID;
    private DatabaseHelper databaseHelper;
    private int selected_vehicle_id;
    private int godownId;
    private String load_type;
    TruckDeliveryActivity truckDeliveryActivity;
    private List<PurchaseERVProduct> purchaseERVProduct = new ArrayList<>();
    private String selected_vehicle_number;
    private List<String> listSpinItems = new ArrayList<>();
    private ArrayAdapter<String> spinAdapter;
    private List<EditText> dynamicQuantity = new ArrayList<>();
    private List<EditText> dynamicDefective = new ArrayList<>();
    private List<Spinner> dynamicSpinner = new ArrayList<>();
    private ArrayList<String> arrayDynamicViews = new ArrayList<>();
    private String default_str = "--select--";
    private int spinItemsCount = -1;
    private ArrayList<String> spinItems;
    private SpinnerDialog ervdialog;


    public TruckSendOwnFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_send_own_truck, container, false);
        USER_ID = parseInt(getSharedPrefWithKEY(getContext(), Constants.KEY_USER_ID));
        setGodownId(Integer.parseInt(Constants.getSharedPrefWithKEY(getContext(), Constants.KEY_GODOWN)));
        ButterKnife.bind(getActivity());

        truckDeliveryActivity = ((TruckDeliveryActivity) getActivity());
        initUI(rootView);
        return rootView;
    }

    private void initUI(final View view) {

        generateET = view.findViewById(R.id.generateBtn);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnSelectTruckNumber = (AppCompatButton) view.findViewById(R.id.btnSelectTruckNumber);
        etErvNumber = view.findViewById(R.id.etErvNumber);
        etErvNumber.setEnabled(false);
        myLinearLay = view.findViewById(R.id.dynamic);
        tvSelectedTruck = view.findViewById(R.id.tvSelectedTruck);
        scrollView = view.findViewById(R.id.scrollView);

         TruckSendFragment fragment = (TruckSendFragment) getParentFragment();
        if (TruckDeliveryActivity.isShowPopup && truckDeliveryActivity != null && truckDeliveryActivity.lstERVOWNModel != null) {
            if(fragment != null && !fragment.isFromHome){
                initERVOWNAdapter();
            }

        }

        //etEnterTruckNo.setVisibility(View.GONE);
        btnSelectTruckNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getVisibility() == View.VISIBLE) {
                    showTruckList();
                }
            }
        });

        submitBtnClick();
        applyDynamicViews();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(listener);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(listener,
                new IntentFilter("showDialog"));
    }

    private android.content.BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (truckDeliveryActivity != null && truckDeliveryActivity.lstERVOWNModel != null) {
                if (isAdded()) {
                    initERVOWNAdapter();
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    private void applyDynamicViews() {

        spinItems = new ArrayList<>();

        RuntimeExceptionDao<ProductDB, Integer> productDB = getHelper().getProductRTExceptionDao();
        List<ProductDB> productDBList = productDB.queryForAll();
        int productSize = productDBList.size();

        spinItems.clear();
        if (productSize > 0) {
            for (ProductDB item : productDBList)
                spinItems.add(item.product_description);
        }
        spinItems.add(0, default_str);
        spinAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinItems);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        generateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadDynamicProducts(null, false);
            }
        });


    }

    private void showTruckList() {


        RuntimeExceptionDao<VehicleDB, Integer> vehicleDB = getHelper().getVehicleRTExceptionDao();
        final List<VehicleDB> vehicleDBList = vehicleDB.queryForAll();
        int vehSize = vehicleDBList.size();

        if (vehSize > 0) {

            /* Adding truck numbers from database */
            ArrayList<String> listTruckNumber = new ArrayList<>();
            for (VehicleDB truckNum : vehicleDB) {
                listTruckNumber.add(truckNum.vehicle_number);
            }
            /* show spinner dialog*/



            final SpinnerDialog dialog = new SpinnerDialog(getActivity(), listTruckNumber, getResources().getString(R.string.select_truck_no));
            dialog.showSpinerDialog();
            dialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String truckNumber, int position) {
                    int vehicle_id = vehicleDBList.get(position).vehicle_id;
                    String vehicle_num = vehicleDBList.get(position).vehicle_number;
                    Log.e("" + vehicle_id, vehicle_num);
                    tvSelectedTruck.setText(vehicle_num);
                    tvSelectedTruck.setVisibility(View.VISIBLE);
                    setSelected_vehicle_id(vehicle_id);
                    setSelected_vehicle_number(vehicle_num);

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    etErvNumber.requestFocus();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Truck Available", Toast.LENGTH_SHORT).show();
        }


    }

    void initERVOWNAdapter() {

        etErvNumber.setText("");
        List<String> ownItems = truckDeliveryActivity.lstERVOWNModel;
        ervdialog = new SpinnerDialog(getActivity(), new ArrayList<String>(ownItems), getResources().getString(R.string.select_erv_no));
        ervdialog.showSpinerDialog();
        ervdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String ervNumber, int position) {
                //purchaseERVProduct.clear();
                purchaseERVProduct = truckDeliveryActivity.hashProduct.get(ervNumber);
                if (purchaseERVProduct != null && purchaseERVProduct.size() > 0) {
                    PurchaseERVProduct purchaseERVHeader = purchaseERVProduct.get(0);
                    tvSelectedTruck.setVisibility(View.VISIBLE);
                    tvSelectedTruck.setText(purchaseERVHeader.Vehicle_No);
                    etErvNumber.setText(purchaseERVHeader.ERV_No);
                    etErvNumber.setEnabled(false);
                    btnSelectTruckNumber.setEnabled(false);
                    setSelected_vehicle_number(purchaseERVHeader.Vehicle_No);
                    setSelected_vehicle_id(purchaseERVHeader.vehicleId);

                }

                if (myLinearLay != null) {
                    if (myLinearLay.getChildCount() > 0)
                        myLinearLay.removeAllViews();
                }

                listSpinItems.clear();
                autofillUI();
            }
        });

    }

    private void autofillUI() {
        /*if(!TextUtils.isEmpty(model.ERV_No)) {
            tvSelectedTruck.setText("");
        }

        if(!TextUtils.isEmpty(model.ERV_No)) {
            etEnterTruckNo.setText("");
        }*/
        spinItemsCount = -1;
        if (purchaseERVProduct!=null) {
            for (int i = 0; i < purchaseERVProduct.size(); i++) {
                loadDynamicProducts(purchaseERVProduct.get(i), true);
            }
        }
        else{
            Toast.makeText(getContext(),"Data not available",Toast.LENGTH_SHORT).show();
        }

    }

    private void loadDynamicProducts(PurchaseERVProduct purchaseERVProduct, boolean isAutofill) {

        if (listSpinItems.contains(default_str)) {
            Toast.makeText(getActivity(), "Please Enter valid Product Id", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Spinner spinner1 : dynamicSpinner) {

            spinner1.setClickable(false);
            spinner1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        spinItemsCount++;

        final View viewToAdd = getActivity().getLayoutInflater().inflate(R.layout.dynamic_layout_truck_send, null);
        final Button btnDelete = viewToAdd.findViewById(R.id.btnDelete);
        btnDelete.setTag(spinItemsCount);
        final Spinner spinner = viewToAdd.findViewById(R.id.spinner);
        spinner.setTag(spinItemsCount);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = spinner.getSelectedItem().toString();

                spinItemsCount = Integer.parseInt(spinner.getTag().toString());
                int pos = spinItemsCount;
                if (listSpinItems.contains(selectedItem)) {
                    try {
                        if ((listSpinItems.size() - 1) == spinItemsCount) {
                            listSpinItems.remove(spinItemsCount);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    spinner.setSelection(0);
                    Toast.makeText(getActivity(), "Already Product Selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedItem.trim().equalsIgnoreCase(default_str)) {


                    if (listSpinItems.contains(selectedItem)) {
                        //int pos = spinItemsCount;
                        //pos = --pos;
                        listSpinItems.remove(pos);
                    } else {
                        productsAddRemoveCommon(pos, selectedItem);
                    }
                    return;
                }
                        /*if(listSpinItems.contains(selectedItem)){
                            Toast.makeText(getActivity(),"cannot select same product type",Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                else {

                    if (Integer.parseInt(spinner.getTag().toString()) == spinItemsCount) {

                        try {

                            //pos = --pos;
                            productsAddRemoveCommon(pos, selectedItem);
                        } catch (Exception e) {
                            try{

                                listSpinItems.add(pos, selectedItem);

                            }catch (NullPointerException en){
                                en.printStackTrace();
                            } catch (IndexOutOfBoundsException index){
                                index.printStackTrace();
                            }
                        }

                    }


                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        etQuantity = viewToAdd.findViewById(R.id.etQuantity);
        final EditText etDefective = viewToAdd.findViewById(R.id.etDefective);
        spinner.setAdapter(spinAdapter);
        /* add dynamic data to spinners*/
        dynamicQuantity.add(etQuantity);
        dynamicSpinner.add(spinner);
        dynamicDefective.add(etDefective);

        etQuantity.requestFocus();
        focusOnView(etQuantity);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = "";
                Spinner spinner2 = null;
                LinearLayout linearLayout = (LinearLayout) ((Button) view).getParent();
                if (linearLayout != null) {
                    spinner2 = (Spinner) linearLayout.findViewById(R.id.spinner);
                    if (spinner2 != null) {
                        text = spinner2.getSelectedItem().toString();
                    }
                    etQuantity = (EditText) linearLayout.findViewById(R.id.etQuantity);
                }

                int pos = (int) btnDelete.getTag();
                //pos = --pos;
                spinItemsCount = --spinItemsCount;

                listSpinItems.remove(text);
                myLinearLay.removeView(viewToAdd);
                dynamicQuantity.remove(etQuantity);
                dynamicSpinner.remove(spinner);
                dynamicDefective.remove(etDefective);
            }
        });

        if (isAutofill) {
            if (purchaseERVProduct != null) {
                spinner.setSelection(spinItems.indexOf(purchaseERVProduct.Product_Name));
                etQuantity.setText(Integer.toString(purchaseERVProduct.Sound_Quantity));
                etDefective.setText(Integer.toString(purchaseERVProduct.Defective));
                //listSpinItems.add(purchaseERVProduct.Product_Name);
            }
        }
        myLinearLay.addView(viewToAdd);

    }

    void productsAddRemoveCommon(int pos, String selectedItem) {
        if (listSpinItems.size() > 0) {
            try {
                String str = listSpinItems.get(pos);
                if (!TextUtils.isEmpty(str)) {
                    listSpinItems.set(pos, selectedItem);

                }
            } catch (IndexOutOfBoundsException index){
                index.printStackTrace();
            } catch (Exception e) {
                listSpinItems.add(pos, selectedItem);
            }

        } else {
            /*if(pos != 0 && selectedItem.equalsIgnoreCase(default_str)) {

            }*/
            listSpinItems.add(pos, selectedItem);
        }

    }

    private final void focusOnView(final View view) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, view.getBottom());
            }
        });
    }

    private void submitBtnClick() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int i = 0;
                List<TruckSendDetailsDB> lsttruckSendDetailsDB = new ArrayList<>();
                arrayDynamicViews.clear();
                RuntimeExceptionDao<ProductDB, Integer> productDB = getHelper().getProductRTExceptionDao();
                List<ProductDB> productDBList = productDB.queryForAll();

                final String erv_number = etErvNumber.getText().toString().trim();
                String randomNumber = erv_number + getGodownId() + AppSettings.currentDateTime() + AppSettings.getInstance(getActivity()).getRandomNumber();

                for (int j = 0; j < dynamicQuantity.size(); j++) {

                    String et_quantity = String.valueOf(dynamicQuantity.get(j).getText());
                    String et_defective = String.valueOf(dynamicDefective.get(j).getText());


                    /*Added ZERO by default if validation is missed */
                    if (TextUtils.isEmpty(et_quantity)) {
                        et_quantity = "0";
                    }

                    if (TextUtils.isEmpty(et_defective)) {
                        et_defective = "0";
                    }


                    String spinner = (String) dynamicSpinner.get(j).getSelectedItem();
                    if (spinner.trim().equalsIgnoreCase(default_str)) {
                        Toast.makeText(getActivity(), "Invalid Product", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    int spinnerPosition = spinAdapter.getPosition(spinner);
                    spinnerPosition = --spinnerPosition;

                    //fixed crash using try catch
                    int spinnerCode = 0;
                    try {
                        spinnerCode = productDBList.get(spinnerPosition).product_code;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    //arrayDynamicViews.add(spinnerCode + "|" + et_quantity + "|" + et_defective + "~");

                    //Amey
                    int id_product = spinnerCode;
                    int quantity = Integer.parseInt(et_quantity);
                    int defective = Integer.parseInt(et_defective);


                    //String truck_no = etEnterTruckNo.getText().toString().trim().toUpperCase();


                    if (erv_number.equalsIgnoreCase("")) {
                        etErvNumber.requestFocus();
                        etErvNumber.setError("Provide Erv Number");
                        return;
                    } else if (Integer.toString(id_product).equalsIgnoreCase("")) {
                        Toast.makeText(getContext(), "Please Add product type", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (btnSelectTruckNumber.getVisibility() == View.VISIBLE && selected_vehicle_number.equalsIgnoreCase("")) {
                        Toast.makeText(getContext(), "Select Truck Number", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Integer.toString(quantity).equals("0") || Integer.toString(quantity).equals("00") ||
                            Integer.toString(quantity).equals("000")) {
                        Toast.makeText(getContext(), "Enter Quantity Of Cylinder's", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String deviceId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                        TruckSendDetailsDB truckSendDetailsDB = null;

                        truckSendDetailsDB = new TruckSendDetailsDB();
                        truckSendDetailsDB.truck_details_send_id = 1;
                        truckSendDetailsDB.truck_send_id = 1;
                        truckSendDetailsDB.ervNo = erv_number;
                        truckSendDetailsDB.idProduct = id_product;
                        truckSendDetailsDB.vehicleId = getSelected_vehicle_id();
                        truckSendDetailsDB.pcoVehicleNo = "";
                        truckSendDetailsDB.createdBy = USER_ID;
                        truckSendDetailsDB.deviceId = deviceId;
                        truckSendDetailsDB.typeOfQuery = "INSERT";
                        truckSendDetailsDB.godown_Id = getGodownId();
                        truckSendDetailsDB.send_date = getDateTime();
                        truckSendDetailsDB.is_sync = "N";
                        truckSendDetailsDB.mode_of_entry = "mobile";
                        truckSendDetailsDB.Quantity = quantity;
                        truckSendDetailsDB.Defective = defective;
                        truckSendDetailsDB.Id_ERV = randomNumber;


                        lsttruckSendDetailsDB.add(truckSendDetailsDB);
                    }

                    //i++;
                }

                if (lsttruckSendDetailsDB.size() > 0) {
                    saveSendTruck(lsttruckSendDetailsDB);
                } else {
                    Toast.makeText(getContext(), "Please Add product type", Toast.LENGTH_SHORT).show();
                    return;
                }

/*
                String dynamicList = "";
                for (int list = 0; list < arrayDynamicViews.size(); list++) {
                    dynamicList = dynamicList + arrayDynamicViews.get(list);
                }

                String id_product = "";
                if (dynamicList.length() > 0) {
                    id_product = dynamicList.substring(0, dynamicList.length() - 1);
                }

*/

            }
        });

    }

    public String getDate() {

        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            date = formatter.parse(new Date().toString());
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return simpleDateFormat.format(date);
    }

    private void saveSendTruck(final List<TruckSendDetailsDB> lsttruckSendDetailsDB) {

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Entry");
        alertDialog.setMessage(getResources().getString(R.string.proceed_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    RuntimeExceptionDao<TruckSendDetailsDB, Integer> domesticSendDB = getHelper().getTruckDetailSendRTExceptionDao();

                    //String send_date = getDateTime();
                    for (int i = 0; i < lsttruckSendDetailsDB.size(); i++) {
                        domesticSendDB.create(lsttruckSendDetailsDB.get(i));

                    }
                    getActivity().finish();
                    Toast.makeText(getContext(), "Entry saved", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    Toast.makeText(getContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }


    public String getDateTime() {

        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            date = formatter.parse(new Date().toString());
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return simpleDateFormat.format(date);
    }


    public int getSelected_vehicle_id() {
        return selected_vehicle_id;
    }

    public void setSelected_vehicle_id(int selected_vehicle_id) {
        this.selected_vehicle_id = selected_vehicle_id;
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    public int getGodownId() {
        return godownId;
    }

    public void setGodownId(int godownId) {
        this.godownId = godownId;
    }

    public String getLoad_type() {
        return load_type;
    }

    public void setLoad_type(String load_type) {
        this.load_type = load_type;
    }

    public String getSelected_vehicle_number() {
        return selected_vehicle_number;
    }

    public void setSelected_vehicle_number(String selected_vehicle_number) {
        this.selected_vehicle_number = selected_vehicle_number;
    }

}
