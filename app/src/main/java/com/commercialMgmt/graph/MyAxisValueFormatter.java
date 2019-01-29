package com.commercialMgmt.graph;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat decimalFormat;

    public  MyAxisValueFormatter (){
        decimalFormat=new DecimalFormat("######.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return decimalFormat.format(value);
    }
}
