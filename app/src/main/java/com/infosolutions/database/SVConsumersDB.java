package com.infosolutions.database;

import com.infosolutions.network.Constants;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONObject;

import java.io.Serializable;

@DatabaseTable(tableName = "SVConsumers_Table")
public class SVConsumersDB implements Serializable {

    public SVConsumersDB() {
    }

    public SVConsumersDB(int SVConsumerId, String ConsumerNo, String SvNo,
                         String ConsumerName, String SVDate,int CylQty,int ProductID) {
        this.SVConsumerId = SVConsumerId;
        this.ConsumerNo = ConsumerNo;
        this.SvNo = SvNo;
        this.ConsumerName = ConsumerName;
        this.SVDate = SVDate;
        this.CylQty = CylQty;
        this.ProductID = ProductID;
    }

    @DatabaseField(generatedId = true, columnName = "SVConsumerId")
    public int SVConsumerId;

    @DatabaseField
    public String ConsumerNo;
    @DatabaseField
    public String SvNo;
    @DatabaseField
    public String ConsumerName;
    @DatabaseField
    public String SVDate;
    @DatabaseField
    public int CylQty;
    @DatabaseField
    public int ProductID;

    public SVConsumersDB(JSONObject joDomestic) {
        if (joDomestic != null) {
            ConsumerNo = joDomestic.optString("ConsumerNo");
            SvNo = joDomestic.optString("SvNo");
            ConsumerName = joDomestic.optString("ConsumerName");
            SVDate = joDomestic.optString("SVDate");
            CylQty = joDomestic.optInt("CylQty");
            ProductID = joDomestic.optInt("ProductID");
            }
    }

    @Override
    public String toString() {
        return "SVConsumersDB{" +
                "SVConsumerId=" + SVConsumerId +
                ", ConsumerNo='" + ConsumerNo + '\'' +
                ", SvNo='" + SvNo + '\'' +
                ", ConsumerName='" + ConsumerName + '\'' +
                ", SVDate='" + SVDate + '\'' +
                ", CylQty='" + CylQty + '\'' +
                ", ProductID='" + ProductID + '\'' +
                '}';
    }

}
