package com.commercialMgmt.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;

import java.io.Serializable;

@DatabaseTable(tableName = "AreaModel")
public class AreaModel implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @DatabaseField
    public int AreaID;

    @DatabaseField
    public String AreaName;

    public AreaModel() { }

    public AreaModel(JSONObject jsonObject){
        if(jsonObject != null){
            AreaID = jsonObject.optInt("ID");
            AreaName = jsonObject.optString("AreaName");
        }
    }
}
