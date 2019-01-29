package com.commercialMgmt.graph;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter implements IValueFormatter, IAxisValueFormatter {

    private DecimalFormat decimalFormat;
    //private  String value;

    public  MyValueFormatter (){
        decimalFormat=new DecimalFormat("######");
    }

    /*public MyValueFormatter (String value){
        this.value=value;
    }*/

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return decimalFormat.format(value);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return null;
    }
}
