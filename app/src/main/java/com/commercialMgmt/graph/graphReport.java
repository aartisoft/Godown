package com.commercialMgmt.graph;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.infosolutions.database.DatabaseHelper;
import com.infosolutions.database.TruckDetailsDB;
import com.infosolutions.database.VehicleDB;
import com.infosolutions.evita.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class graphReport extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private List<TruckDetailsDB> ConsumerList;
    private String[] ConsumerArr;

//-------------------------- Group Bar Chart Data ------------------------
    BarChart mChart;

    private int valOne ;
    private int valTwo ;
    private int valThree ;

//--------------------------
BarChart singleBarChart;

    @BindView(R.id.et_vehicleDetails)
    AutoCompleteTextView et_vehicleDetails;

    private ArrayList<String> listTruckNumber;
    private ArrayAdapter<String> vehicleAdapter;
    private TruckDetailsDB truckDetailsDB;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_report_commerccial);

        ButterKnife.bind(this);
        groupBarChart();
        getVehicle();
    }

    // get product list....................
    private void getVehicle() {

        RuntimeExceptionDao<VehicleDB, Integer> vehicleDB = getHelper().getVehicleRTExceptionDao();
        final List<VehicleDB> vehicleDBList = vehicleDB.queryForAll();
        int vehSize = vehicleDBList.size();

        if (vehSize > 0) {

            listTruckNumber = new ArrayList<>();
            for (VehicleDB truckNum : vehicleDB) {
                listTruckNumber.add(truckNum.vehicle_number);
            }
        }

        vehicleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listTruckNumber);
        et_vehicleDetails.setAdapter(vehicleAdapter);

        et_vehicleDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int truckID=vehicleDBList.get(position).vehicle_id;

                singleBarChart(truckID);
            }
        });
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    // -----------------------------------------------  Group Bar chart started  ---------------------------------------

    private void groupBarChart() {
        mChart = (BarChart) findViewById(R.id.chart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(true);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        getConsumerList();

        IAxisValueFormatter xAxisFormatter = new LabelFormatter(mChart, ConsumerArr);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(8, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();

        RuntimeExceptionDao<TruckDetailsDB, Integer> comProductDB = getHelper().getTruckDetailRTExceptionDao();
        ConsumerList = comProductDB.queryForAll();
        int productSize = ConsumerList.size();

       // if (productSize>0){
        for(int i = 0; i < productSize; i++) {
            valOne = ConsumerList.get(i).Quantity;
            valTwo = ConsumerList.get(i).LostCylinder;
            valThree = ConsumerList.get(i).LostCylinder;

            barOne.add(new BarEntry(i, valOne));
            barTwo.add(new BarEntry(i, valTwo));
            barThree.add(new BarEntry(i, valThree));
        }
        //  }

        BarDataSet set1 = new BarDataSet(barOne, "Quantity");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "LostCylinder");
        set2.setColor(Color.MAGENTA);
        BarDataSet set3 = new BarDataSet(barThree, "Defective");
        set3.setColor(Color.RED);

        set1.setHighlightEnabled(true);
        set1.setDrawValues(true);
        set2.setHighlightEnabled(true);
        set2.setDrawValues(true);
        set3.setHighlightEnabled(true);
        set3.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        BarData data = new BarData(dataSets);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.16f;
        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(ConsumerArr.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(true);
        mChart.setVisibleXRangeMaximum(2f);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();

    }

    @SuppressLint("LongLogTag")
    private void getConsumerList() {

        RuntimeExceptionDao<TruckDetailsDB, Integer> comProductDB = getHelper().getTruckDetailRTExceptionDao();
        ConsumerList = comProductDB.queryForAll();
        int productSize = ConsumerList.size();

        //----------------------------------------------------------------------------------
        //  product positions

        try {
            ConsumerArr = new String[ConsumerList.size()];
            for (int i = 0; i < ConsumerList.size(); i++) {
                ConsumerArr[i] = String.valueOf(ConsumerList.get(i).vehicleId);
                // Log.e("Consumer Names....",String.valueOf(ConsumerList.get(i).prodname));
            }
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e("Array Index out of bound Exception",e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class LabelFormatter implements IAxisValueFormatter {

        String[] labels;
        BarLineChartBase<?> chart;

        LabelFormatter(BarLineChartBase<?> chart, String[] labels) {
            this.chart = chart;
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }
//-------------------------------------------------------------------------------------
// --------------------------------- Group Bar chart ends -----------------------------
//-------------------------------------------------------------------------------------

    private void singleBarChart(int truckID){
        singleBarChart=(BarChart)findViewById(R.id.chart1);
        singleBarChart.setMaxVisibleValueCount(30);

        setData(truckID);
    }

    private void setData(int truckID) {

        ArrayList<BarEntry> yValues=new ArrayList<>();

        //TruckDetailsDB truckDetailsDB;
        RuntimeExceptionDao<TruckDetailsDB, Integer> comProductDB = getHelper().getTruckDetailRTExceptionDao();
        try {
            ConsumerList = comProductDB.queryBuilder().where().eq("vehicleId",truckID).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int productSize = ConsumerList.size();

            for(int i = 0; i < productSize; i++) {
                valOne = ConsumerList.get(i).Quantity;
                valTwo = ConsumerList.get(i).LostCylinder;
                valThree  = ConsumerList.get(i).Defective;

                yValues.add(new BarEntry(i,new float[]{
                        valOne,valTwo,valThree
                }, "du"));
            }

        int[] colors = {Color.RED, Color.GREEN,Color.BLUE};
        BarDataSet set1;

        set1=new BarDataSet(yValues,"TRUCK DETAILS");
        set1.setStackLabels(new String[]{"Quantity","LostCylinder","Defective"});
        set1.setColors(colors);

        BarData data=new BarData(set1);

        data.setValueFormatter(new MyValueFormatter());

        singleBarChart.setData(data);
        singleBarChart.setFitBars(true);
        singleBarChart.invalidate();

    }

}