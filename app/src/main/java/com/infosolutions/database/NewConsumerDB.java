package com.infosolutions.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "Consumer_DB")
public class NewConsumerDB implements Serializable {

    public NewConsumerDB(){
    }

    public NewConsumerDB(int consumer_id, String ConsumerNo,String is_sync) {
        this.consumer_id = consumer_id;
        this.ConsumerNo = ConsumerNo;
        this.is_sync = is_sync;
    }

    @DatabaseField(generatedId = true, columnName = "consumer_id")
    public int consumer_id;
    @DatabaseField
    public String ConsumerNo;
    @DatabaseField
    public String is_sync;


    @Override
    public String toString() {
        return "NewConsumerDB{" +
                "consumer_id=" + consumer_id +
                ",ConsumerNo='" + ConsumerNo + '\'' +
                ",is_sync='" + is_sync + '\'' +
                '}';
    }

}
