package com.infosolutions.network;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.infosolutions.evita.R;
import com.irozon.sneaker.Sneaker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by shailesh on 14/7/17.
 */

public class Constants {


    public static final String KEY_GODOWN = "GODOWN";
    public static final String KEY_GODOWN_NAME = "GODOWN_NAME";
    public static final String LOGIN_GODOWNKEEPER = "login_godown_keeper";
    public static final String LOGIN_DELIVERYMAN = "login_deliveryman";
    public static final String LOGIN_DELIVERYMAN_ID = "login_deliveryman_id";
    public static final String LOGIN_TYPE = "login_type";
    public static final String REPORTS = "reports";
    public static final String KEY_AGENCY_NAME = "AGENCY_NAME";
    public static final String KEY_REPORT_NAME = "reportName";
    public static final String KEY_GODOWN_ID = "godown_code";
    public static final String SHARED_PREF = "SharedPref";
    public static final String PREF_DATE = "pref_date";
    public static final String PREF_SYNC_JSON_DATA = "sync_json";
    public static boolean isNetworkAvailable;
    public static final String DEFAULT_STRING_VALUE = "No Preference Found";
    public static final String error_message ="ग्राहक उपलब्ध नहीं है";
    public static final String commercial_sale = "Sale";
    public static final String commercial_cash = "Cash";
    public static final String commercial_empty_received = "Empty_Received";
    public static final String StockReportTitle = "Stock";
    public static final String responseCcode = "ResponseCode";

    /*Typefaces*/
    public static final String TYPEFACE_BOLD = "fonts/Ubuntu-B.ttf";
    public static final String TYPEFACE_LARGE = "fonts/Ubuntu-L.ttf";

    /**/
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_TYPE = "loginId";

    /**/

    private static final String TEST_URL = "http://103.31.144.174:8080/EVITA/User";
    private static final String BHIVANDI_GAS_SERVICE = "http://103.31.144.174:8080/BHIVANDI_GAS_AGENCY/User";
    private static final String godown_ags = "http://103.31.144.174:8080/GODOWN_AGS/User";
    //private static final String TESTER_AGS = "http://103.31.144.174:8080/TESTER_AGS/User";
    //private static final String PROD_URL = "http://103.31.144.174:8080/EVITA_PROD/User";
    private static final String PROD_URL = "http://103.31.144.174/login/validateLogin";
    public static final  String EVITA_API_URL = PROD_URL;

    /*
    Cloud
     */

   /* public static final String dbname = "EVITA_PROD";
    public static final String INFO ="android.evitasoftsolutions.com";
    public static final String GET_ALL_REPORT = "http://android.evitasoftsolutions.com/Reports/GetAndrReportAll";
    public static final String get_url = "http://android.evitasoftsolutions.com/masters/GetAndrEmp"; //"http://103.31.144.174/masters/GetAndrEmp";
    public static final String post_url = "http://android.evitasoftsolutions.com/Purchase/SaveDomesticDeliveryAndrA";
    public static final String COMMERCIAL_DELIVERY_COUNT = "http://android.evitasoftsolutions.com/Purchase/GetCreditCommCyl";
    public static final String GET_CONSUMER_DETAILS ="http://android.evitasoftsolutions.com/Purchase/GetAndrConsumerDetails";
    public static final String GET_STOCKS_URL = "http://android.evitasoftsolutions.com/Purchase/GetStockDetials";
    public static final String POST_STOCKS_URL = "http://android.evitasoftsolutions.com/Purchase/SaveTransfer";
    public static final String GET_PURCHASE_ERV = "http://android.evitasoftsolutions.com/Purchase/GetAndrPurchaseERV";
    public static final String LOGIN_URL = "http://android.evitasoftsolutions.com/login/validateLogin";
    public static final String COMMERCIAL_REPORTS = "http://android.evitasoftsolutions.com/Purchase/GetAndrReport";
    public static final String COMMERCIAL_GET_CONSUMER_DETAILS = "http://android.evitasoftsolutions.com/Masters/GetAndrCommConsumer";
    public static final String COMMERCIAL_SAVE_CONSUMER_DELIVERY = "http://android.evitasoftsolutions.com/CommercialMng/SaveAndrCommercial";
    public static final String SAVE_CONSUMER_DETAILS="http://android.evitasoftsolutions.com/masters/SaveCommercialParty";
    public static final String GET_AVAILABLE_CYLINDERS="http://android.evitasoftsolutions.com/masters/GetAndrAvilableCylinders";
    public static final String AGENCY_NAME="http://android.evitasoftsolutions.com/Masters/GetAndrPrimaryCompany";
    public static final String STOCK_REPORT="http://android.evitasoftsolutions.com/Inventory/GetAndrGodownWiseStocks";
    public static final String OWNER_DETAIL_REPORT = "http://android.evitasoftsolutions.com/Inventory/GetAndrOwnerReports";
    public static final String SV_CONSUMERS = "http://android.evitasoftsolutions.com/Masters/GetAndrSvDetails";
*/

    public static final String dbname = "EVITA_PROD";
    public static final String INFO ="http://ags.evitasoftsolutions.com/";
    public static final String GET_ALL_REPORT = "http://ags.evitasoftsolutions.com/Reports/GetAndrReportAll";
    public static final String get_url = "http://ags.evitasoftsolutions.com/masters/GetAndrEmp"; //"http://103.31.144.174/masters/GetAndrEmp";
    public static final String post_url = "http://ags.evitasoftsolutions.com/Purchase/SaveDomesticDeliveryAndrA";
    public static final String COMMERCIAL_DELIVERY_COUNT = "http://ags.evitasoftsolutions.com/Purchase/GetCreditCommCyl";
    public static final String GET_CONSUMER_DETAILS ="http://ags.evitasoftsolutions.com/Purchase/GetAndrConsumerDetails";
    public static final String GET_STOCKS_URL = "http://ags.evitasoftsolutions.com/Purchase/GetStockDetials";
    public static final String POST_STOCKS_URL = "http://ags.evitasoftsolutions.com/Purchase/SaveTransfer";
    public static final String GET_PURCHASE_ERV = "http://ags.evitasoftsolutions.com/Purchase/GetAndrPurchaseERV";
    public static final String LOGIN_URL = "http://ags.evitasoftsolutions.com/login/validateLogin";
    public static final String COMMERCIAL_REPORTS = "http://ags.evitasoftsolutions.com/Purchase/GetAndrReport";
    public static final String COMMERCIAL_GET_CONSUMER_DETAILS = "http://ags.evitasoftsolutions.com/Masters/GetAndrCommConsumer";
    public static final String COMMERCIAL_SAVE_CONSUMER_DELIVERY = "http://ags.evitasoftsolutions.com/CommercialMng/SaveAndrCommercial";
    public static final String SAVE_CONSUMER_DETAILS="http://ags.evitasoftsolutions.com/masters/SaveCommercialParty";
    public static final String GET_AVAILABLE_CYLINDERS="http://ags.evitasoftsolutions.com/masters/GetAndrAvilableCylinders";
    public static final String AGENCY_NAME="http://ags.evitasoftsolutions.com/Masters/GetAndrPrimaryCompany";
    public static final String STOCK_REPORT="http://ags.evitasoftsolutions.com/Inventory/GetAndrGodownWiseStocks";
    public static final String OWNER_DETAIL_REPORT = "http://ags.evitasoftsolutions.com/Inventory/GetAndrOwnerReports";
    public static final String SV_CONSUMERS = "http://ags.evitasoftsolutions.com/Masters/GetAndrSvDetails";

/*
    public static final String dbname = "EVITA_PROD";
    public static final String INFO ="tara.mygasagency.com";
    public static final String GET_ALL_REPORT = "http://tara.mygasagency.com/Reports/GetAndrReportAll";
    public static final String get_url = "http://tara.mygasagency.com/masters/GetAndrEmp"; //"http://103.31.144.174/masters/GetAndrEmp";
    public static final String post_url = "http://tara.mygasagency.com/Purchase/SaveDomesticDeliveryAndrA";
    public static final String COMMERCIAL_DELIVERY_COUNT = "http://tara.mygasagency.com/Purchase/GetCreditCommCyl";
    public static final String GET_CONSUMER_DETAILS ="http://tara.mygasagency.com/Purchase/GetAndrConsumerDetails";
    public static final String GET_STOCKS_URL = "http://tara.mygasagency.com/Purchase/GetStockDetials";
    public static final String POST_STOCKS_URL = "http://tara.mygasagency.com/Purchase/SaveTransfer";
    public static final String GET_PURCHASE_ERV = "http://tara.mygasagency.com/Purchase/GetAndrPurchaseERV";
    public static final String LOGIN_URL = "http://tara.mygasagency.com/login/validateLogin";
    public static final String COMMERCIAL_REPORTS = "http://tara.mygasagency.com/Purchase/GetAndrReport";
    public static final String COMMERCIAL_GET_CONSUMER_DETAILS = "http://tara.mygasagency.com/Masters/GetAndrCommConsumer";
    public static final String COMMERCIAL_SAVE_CONSUMER_DELIVERY = "http://tara.mygasagency.com/CommercialMng/SaveAndrCommercial";
    public static final String SAVE_CONSUMER_DETAILS="http://tara.mygasagency.com/masters/SaveCommercialParty";
    public static final String GET_AVAILABLE_CYLINDERS="http://tara.mygasagency.com/masters/GetAndrAvilableCylinders";
    public static final String AGENCY_NAME="http://tara.mygasagency.com/Masters/GetAndrPrimaryCompany";
    public static final String STOCK_REPORT="http://tara.mygasagency.com/Inventory/GetAndrGodownWiseStocks";
    public static final String OWNER_DETAIL_REPORT = "http://tara.mygasagency.com/Inventory/GetAndrOwnerReports";
    public static final String SV_CONSUMERS = "http://tara.mygasagency.com/Masters/GetAndrSvDetails";
*/


    public static String LOGIN_API_VALUE = "userAuth";
    public static String SYNC_ANDROID_DATA_VALUE = "syncAndriodData";
    public static String GODOWN_CODE = "godown_code";
    public static String PRODUCT_ID = "productId";


    public static String MODULE_KEY = "module";
    public static String MODULE_AVAILABLE_CYL = "?module=getAvilableCylinders";
    public static String MODULE_AGENCY_NAME = "getSplashData";
    public static String LOGIN_USERID_KEY = "userId";
    public static String LOGIN_PASSWORD_KEY = "password";
    public static String COMMERCIAL_LOGIN = "isCommercialDeliveryman";
    public static String LOGIN_USERTYPE_KEY = "User_Type";
    public static String LOGIN_ANDROID = "Android";
    public static String JSON_DATA_KEY = "jsonData";
    public static String RESET_TIMER_BROADCAST = "reset_timer_broadcast";
    public static String CONSUMER_BROADCAST = "consumer_broadcast";
    public static String godown_keeper = "Godown Keeper";
    public static String commercial_deliveryman = "Commercial DeliveryMan";
    public static String owner = "Owner";

    /**
     * saveWithSharedPreferences KEY is used to save SharedPreference Value based KEY
     * @param context
     * @param sharedPrefKey
     * @param sharedPrefValue
     */

    public static void saveWithSharedPreferences(Context context, String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
        editor.commit();
    }

    /**
     * getSharedPrefWithKEY is used to fetch stored SharedPreference Value against @param sharedPrefKey
     * @param context
     * @param sharedPrefKey
     * @return String resultModule
     */

    public static String getSharedPrefWithKEY(Context context, String sharedPrefKey) {

        SharedPreferences sharedPref = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        String defaultValue = context.getResources().getString(R.string.sp_default_value);
        String resultModule = sharedPref.getString(sharedPrefKey, defaultValue);
        return resultModule;
    }

    /*private static String currentDateTime() {

        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            date = formatter.parse(new Date().toString());
            simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return simpleDateFormat.format(date);
    }
*/

    public static String currentDateTime() {

        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            date = (Date) formatter.parse(new Date().toString());
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return simpleDateFormat.format(date);
    }

    /**
     * API
     */


    public static int CHECK_STOCK_FLAG = 2;

    public int setStockFlagCount(int stockFlagCount) {
        CHECK_STOCK_FLAG = stockFlagCount;
        return CHECK_STOCK_FLAG;
    }

    public int getStockFlagCount() {
        return CHECK_STOCK_FLAG;
    }

    public static boolean isNetworkAvailable(Context context) {

        boolean isNetworkAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isNetworkAvailable = networkInfo != null && networkInfo.isConnected();

        return isNetworkAvailable;
    }

    public static String getDateTime() {

        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        String dates = null;
        try {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            date = formatter.parse(new Date().toString());
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dates=simpleDateFormat.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dates;

    }

}
