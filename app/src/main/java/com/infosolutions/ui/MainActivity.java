package com.infosolutions.ui;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.commercialMgmt.AddNewConsumerActivity;
import com.commercialMgmt.CommercialSaleActivity;
import com.infosolutions.adapter.ModuleAdapter;
import com.infosolutions.adapter.ModuleGridAdapter;
import com.infosolutions.adapter.ModuleModel;
import com.infosolutions.core.BaseActivity;
import com.infosolutions.core.EvitaApplication;
import com.infosolutions.database.CommercialDeliveryCreditDB;
import com.infosolutions.database.DatabaseHelper;
import com.infosolutions.database.DomesticDeliveryDB;
import com.infosolutions.database.SVConsumersDB;
import com.infosolutions.event.EvitaEvent;
import com.infosolutions.evita.R;
import com.infosolutions.factory.IntentFactory;
import com.infosolutions.network.Constants;
import com.infosolutions.network.VolleySingleton;
import com.infosolutions.ui.login.LoginActivity;
import com.infosolutions.ui.user.commercial.CommercialActivity;
import com.infosolutions.ui.user.domestic.DomesticActivity;
import com.infosolutions.ui.user.reports.ReportListItemsActivity;
import com.infosolutions.ui.user.setting.SettingsActivity;
import com.infosolutions.ui.user.stock.StockDetails;
import com.infosolutions.ui.user.stock.StockListActivity;
import com.infosolutions.ui.user.stock.StockTransferActivity;
import com.infosolutions.ui.user.tvdetails.TVDetailsActivity;
import com.infosolutions.utils.AppSettings;
import com.infosolutions.utils.Constant;
import com.infosolutions.utils.GlobalVariables;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.kcode.bottomlib.BottomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.inject.Inject;

import khangtran.preferenceshelper.PreferencesHelper;

import static com.infosolutions.network.Constants.KEY_GODOWN;
import static com.infosolutions.network.Constants.KEY_GODOWN_NAME;


public class MainActivity extends BaseActivity {

    private static final int RECORD_REQUEST_CODE = 3367;
    private static final int RQ_DEVICE_STATE = 3360;
    boolean doubleBackToExitPressedOnce = false;

    @Inject
    EventBus eventBus;
    Timer mtimer;
    TimerTask mTimerTask;
    private RecyclerView recyclerView;
    private List<ModuleModel> listModel = new ArrayList<>();
    private String view_type;
    private DatabaseHelper databaseHelper;

    // auto sync timer
    private android.content.BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent ) {
            stopTimer();
            if(login_type.equalsIgnoreCase(Constants.LOGIN_GODOWNKEEPER)) {
                startTimer();
            }
        }
    };
    private String login_type;

    @Override
    protected void onStart() {
        super.onStart();
        getSVConsumersList();
    }

    private void getSVConsumersList() {

        VolleySingleton.getInstance(getApplicationContext())
                .addResponseListener(VolleySingleton.CallType.GET_SVCONSUMERS, this);

        VolleySingleton.getInstance(getApplicationContext())
                .apiGetSVConsumers(VolleySingleton.CallType.GET_SVCONSUMERS, Constants.SV_CONSUMERS);

    }

    @Override
    public void onSuccess(VolleySingleton.CallType type, String response) {
        serverSuccessResponse(type, response);
    }

    @Override
    public void onFailure(VolleySingleton.CallType type, VolleyError error) {
        serverFailResponse(error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PreferencesHelper.initHelper(this, "SharedPref");
        login_type = PreferencesHelper.getInstance().getStringValue(Constants.LOGIN_TYPE,"");
        Log.d("oncreate_logintype",login_type);
        loadLocale();
        loadToolbar();
        loadModuleList();
        initialiseUI();
        loadListView();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                goToNextActivity(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                goToNextActivity(view, position);
            }
        }));


        if(login_type.equalsIgnoreCase(Constants.LOGIN_GODOWNKEEPER)) {
            startTimer();
        }else{
            stopTimer();
        }
        permission();

        eventBus.getDefault().register(this);

        VolleySingleton.getInstance(getApplicationContext()).addResponseListener(VolleySingleton.CallType.SYNC_LOCAL_DATA, this);
        VolleySingleton.getInstance(getApplicationContext()).addResponseListener(VolleySingleton.CallType.UPDATE_LOCAL_DATA, this);
        VolleySingleton.getInstance(getApplicationContext()).addResponseListener(VolleySingleton.CallType.COMMERCIAL_DELIVERY_COUNT, this);

        /*LocalBroadcastManager .getInstance(this).registerReceiver(listener,
                new IntentFilter(Constants.RESET_TIMER_BROADCAST));
*/
    }

    private void startTimer() {
        mtimer = new Timer();
        mTimerTask = new TimerTask() {
            public void run() {

                if (!AppSettings.getInstance(MainActivity.this).isSyncing) {
                    syncData();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(MainActivity.this, getResources().getString(R.string.syncing_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        mtimer.scheduleAtFixedRate(mTimerTask, 200000, 200000);
        //mtimer.scheduleAtFixedRate(mTimerTask, 10000,10000);
    }

    private void stopTimer(){
        if(mTimerTask != null){
            mTimerTask.cancel();
        }

        if(mtimer != null){
            mtimer.cancel();
        }
    }

    @Override
    public void injectDependency() {
        //EvitaApplication.getEvitaComponents().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_module, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_list_view:

                if (view_type.equalsIgnoreCase("list_view")) {
                    item.setIcon(getResources().getDrawable(R.drawable.ic_dns_black));
                    loadGridView();
                } else {
                    item.setIcon(getResources().getDrawable(R.drawable.ic_dashboard));
                    loadListView();
                }

                break;

            case R.id.action_setting:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            case R.id.action_transfer:
                startActivity(new Intent(getApplicationContext(), StockTransferActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;

            case R.id.action_logout:
                logout();
                break;

            case R.id.action_info:
                getInfo();
                break;

            case R.id.action_language:
                changeLanguage();
                break;
        }
        return true;
    }

    private void getInfo() {
        try {

            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pm.getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            // install datetime
            String appInstallDate = DateUtils.getDate(
                    "dd-MM-yyyy hh:mm:ss.SSS", packageInfo.lastUpdateTime);

            // build datetime
           /* String appBuildDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss.SSS",
                    DateUtils.getBuildDate(this));

            Log.i("", "appBuildDate = " + appBuildDate);
            Log.i("", "appInstallDate = " + appInstallDate);*/

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("App Info......");
            builder.setMessage("Web Site :-- "+Constants.INFO +"\n App Installed Date :-- "+appInstallDate);
            //builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.show();
            TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);

        } catch (Exception e) {
        }


    }



    private void changeLanguage() {
        final String[] lang={"हिंदी","मराठी","English"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               showProgressDialog();
                if (i==0){
                    //Hindi
                    setLocale("hi");
                    recreate();
                }else if (i==1) {
                    //Marathi
                    setLocale("mr");
                    recreate();
                }else if (i==2) {
                    //English
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
    }

    // Change Language method
    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Language",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
        hideProgressDialog();
    }

    public void loadLocale(){
        SharedPreferences preferences=getSharedPreferences("Language",Activity.MODE_PRIVATE);
        String language=preferences.getString("My_Lang","");
        setLocale(language);
    }



    private void logout() {
        BottomDialog dialog = BottomDialog.newInstance("Do you want to Logout ?", "Dismiss", new String[]{"YES", "NO"});
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position) {
                    case 0:
                        Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                        next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(next);
                        finish();
                    case 1:
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                           String permissions[], int[] grantResults) {
            switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    showSnackBar(getString(R.string.permis_denied));

                } else {
                    showSnackBar(getString(R.string.permis_sd_card));
                }
                return;
            }

            case RQ_DEVICE_STATE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showSnackBar(getString(R.string.permis_denied));
                } else {
                    showSnackBar(getString(R.string.permis_sd_card));
                }

                return;
            }
        }
    }

    private void permission() {

        if (!GlobalVariables.permissionsEnabled(MainActivity.this, new String[]{permission.WRITE_EXTERNAL_STORAGE})) {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECORD_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        eventBus.getDefault().unregister(this);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(listener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EvitaEvent.EventDataSyncToServer event) {
        if (event != null && event.isDataSynced()) {
            showSnackBar(getString(R.string.data_to_sync_server));
        }
    }

    private void goToNextActivity(View view, int position) {

        ModuleModel model = listModel.get(position);
        String selectedModuleType = model.getModuleId();

        if(login_type.equalsIgnoreCase(Constants.LOGIN_GODOWNKEEPER)) {
            if (selectedModuleType.equalsIgnoreCase("1")) {
                startActivity(new Intent(getApplicationContext(), DomesticActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (selectedModuleType.equalsIgnoreCase("2")) {
                startActivity(new Intent(getApplicationContext(), CommercialActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (selectedModuleType.equalsIgnoreCase("3")) {
                Intent intent = IntentFactory.getIntent(this, IntentFactory.TRUCKDELIVERYACTIVITY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (selectedModuleType.equalsIgnoreCase("4")) {
                startActivity(new Intent(getApplicationContext(), TVDetailsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (selectedModuleType.equalsIgnoreCase("5")) {
                //startActivity(new Intent(getApplicationContext(), StockListActivity.class));
                startActivity(new Intent(getApplicationContext(), StockDetails.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (selectedModuleType.equalsIgnoreCase("6")) {
                startActivity(new Intent(getApplicationContext(), ReportListItemsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }
        else{
            if (selectedModuleType.equalsIgnoreCase("7")) {
                startActivity(new Intent(getApplicationContext(), AddNewConsumerActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            else if (selectedModuleType.equalsIgnoreCase("8")) {
                startActivity(new Intent(getApplicationContext(), CommercialSaleActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }else if (selectedModuleType.equalsIgnoreCase("9")) {
                startActivity(new Intent(getApplicationContext(), ReportListItemsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }
    }

    private void loadModuleList() {

        String moduleKEY = this.getResources().getString(R.string.key_shared_pref_module_list);
        String moduleVALUE = Constants.getSharedPrefWithKEY(getApplicationContext(), moduleKEY);

        try {
            JSONArray jsonArray = new JSONArray(moduleVALUE.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonModel = jsonArray.optJSONObject(i);
                String imagePathUrl = jsonModel.optString("imagePath");
                String moduleName = jsonModel.optString("name");
                String modulePosition = jsonModel.optString("seq");

                listModel.add(new ModuleModel(imagePathUrl, moduleName, modulePosition));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadToolbar() {

            Toolbar toolbar = findViewById(R.id.toolbar);
            TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(R.string.godown);

            /*ImageView imageView=toolbar.findViewById(R.id.imageview);
            imageView.setVisibility(View.INVISIBLE);*/

            String SELECTED_GODOWN_NAME = PreferencesHelper.getInstance().getStringValue(KEY_GODOWN_NAME, "Empty");
            String SELECTED_GODOWN_CODE = PreferencesHelper.getInstance().getStringValue(KEY_GODOWN, "Empty");
            mTitle.setText(SELECTED_GODOWN_NAME + "( " + SELECTED_GODOWN_CODE + " )");

            //showToast(getPreferences(Constants.KEY_AGENCY_NAME));
            setSupportActionBar(toolbar);
    }

    @Override
        public boolean onPrepareOptionsMenu(Menu menu) {

        login_type = PreferencesHelper.getInstance().getStringValue(Constants.LOGIN_TYPE,"");
        if(login_type.equalsIgnoreCase(Constants.LOGIN_GODOWNKEEPER)) {
            Log.d("loginType:",Constants.LOGIN_GODOWNKEEPER);
            menu.findItem(R.id.action_transfer).setVisible(true);
            menu.findItem(R.id.action_setting).setVisible(true);
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_info).setVisible(true);
        }else{
            Log.d("loginType:",Constants.LOGIN_DELIVERYMAN);
            menu.findItem(R.id.action_transfer).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
            menu.findItem(R.id.action_info).setVisible(true);
        }
        return true;
    }

    private void loadGridView() {

        view_type = "grid_view";
        ModuleGridAdapter gridAdapter = new ModuleGridAdapter(this, listModel);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gridAdapter);
    }

    private void loadListView() {

        view_type = "list_view";
        ModuleAdapter listAdapter = new ModuleAdapter(this, listModel);
        LinearLayoutManager verticalManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
    }

    private void initialiseUI() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
    }

    private void serverSuccessResponse(VolleySingleton.CallType type, String response) {
        String responseMsg = "";
        //progress_bar.setVisibility(View.GONE);
        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (type.equals(VolleySingleton.CallType.SYNC_LOCAL_DATA)) {

            try {

                responseMsg = jsonResult.optString("responseMessage");
                if (jsonResult.optString("responseCode").equalsIgnoreCase("200")) {
                    AppSettings.getInstance(this).getCommercialDeliveryCreditCount(this);

                    /*updateDatabase();

                    EvitaEvent.EventDataSyncToServer eventDataSyncToServer = new EventDataSyncToServer();
                    eventDataSyncToServer.setDataSynced(true);
                    eventBus.post(eventDataSyncToServer);

                    finish();
*/
                } else {
                    AppSettings.getInstance(this).isSyncing = false;
                    //Toast.makeText(this, responseMsg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type.equals(VolleySingleton.CallType.UPDATE_LOCAL_DATA)) {


            RuntimeExceptionDao<DomesticDeliveryDB, Integer> daoDatabase =
                    getHelper().getDomesticRuntimeExceptionDao();

            try {
                daoDatabase.deleteBuilder().delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            JSONArray jsonArray = jsonResult.optJSONArray("Table");
            if (jsonArray!=null){
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    DomesticDeliveryDB domesticDeliveryDB = new DomesticDeliveryDB(jsonObject);
                    daoDatabase.createOrUpdate(domesticDeliveryDB);
                }
            }

            AppSettings.getInstance(this).updateDatabase(this);
            //AppSettings.getInstance(this).notification(getApplicationContext(), responseMsg);
            AppSettings.getInstance(this).isSyncing = false;

            EvitaEvent.EventDataSyncToServer eventDataSyncToServer = new EvitaEvent.EventDataSyncToServer();
            eventDataSyncToServer.setDataSynced(false);
            eventBus.getDefault().post(eventDataSyncToServer);
        } else if (type.equals(VolleySingleton.CallType.COMMERCIAL_DELIVERY_COUNT)) {

            RuntimeExceptionDao<CommercialDeliveryCreditDB, Integer> daoDatabase =
                    getHelper().getCommercialCreditExceptionDao();

            try {
                daoDatabase.deleteBuilder().delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = jsonResult.optJSONArray("Table");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                CommercialDeliveryCreditDB commercialDeliveryCreditDB = new CommercialDeliveryCreditDB(jsonObject);
                daoDatabase.createOrUpdate(commercialDeliveryCreditDB);
            }

            AppSettings.getInstance(this).updateLocalFromServer(this);
        }

        else if (type.equals(VolleySingleton.CallType.GET_SVCONSUMERS)) {

            JSONArray arrayPRODUCT = jsonResult.optJSONArray("SvDetails");

            //AppSettings.getInstance(this).notification(getApplicationContext(), "notification success");

            RuntimeExceptionDao<SVConsumersDB, Integer> productDB = getHelper().getSVConsumersRTExceptionDao();
            try {
                productDB.deleteBuilder().delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(arrayPRODUCT != null) {

                for (int product = 0; product < arrayPRODUCT.length(); product++) {
                    JSONObject objectProduct = arrayPRODUCT.optJSONObject(product);

                    SVConsumersDB commercialProductModel = new SVConsumersDB(objectProduct);
                    productDB.create(commercialProductModel);

                }
            }
        }

        /*else if (type.equals(VolleySingleton.CallType.GET_SVCONSUMERS)) {


            RuntimeExceptionDao<SVConsumersDB, Integer> daoDatabase =
                    getHelper().getSVConsumersRTExceptionDao();

            try {
                daoDatabase.deleteBuilder().delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (jsonResult.optString("ResponseCode").equalsIgnoreCase("200")) {
                JSONArray jsonArray = jsonResult.optJSONArray("SvDetails");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    SVConsumersDB svConsumersDB = new SVConsumersDB(jsonObject);
                    daoDatabase.createOrUpdate(svConsumersDB);
                }

                AppSettings.getInstance(this).getSVConsumersFromServer(this);
            }
        }*/
    }

    private void serverFailResponse(VolleyError error) {
        AppSettings.getInstance(this).isSyncing = false;
    }

    private void autoSync() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                syncData();
            }
        });
    }

    void syncData() {
        try {
            AppSettings.getInstance(this).isSyncing = true;
            JSONObject localJSON_DATA = new JSONObject(AppSettings.getInstance(this).getBodyJson(this));
            AppSettings.getInstance(this).manualSyncAndroidDataToServer(this, localJSON_DATA);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private interface ClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    static class DateUtils {

        public static String getDate(String dateFormat) {
            Calendar calendar = Calendar.getInstance();
            return new SimpleDateFormat(dateFormat, Locale.getDefault())
                    .format(calendar.getTime());
        }

        public static String getDate(String dateFormat, long currenttimemillis) {
            return new SimpleDateFormat(dateFormat, Locale.getDefault())
                    .format(currenttimemillis);
        }

       /* public static long getBuildDate(Context context) {

            try {
                ApplicationInfo ai = context.getPackageManager()
                        .getApplicationInfo(context.getPackageName(), 0);
                ZipFile zf = new ZipFile(ai.sourceDir);
                ZipEntry ze = zf.getEntry("classes.dex");
                long time = ze.getTime();

                return time;

            } catch (Exception e) {
            }

            return 0l;
        }*/
    }


}



