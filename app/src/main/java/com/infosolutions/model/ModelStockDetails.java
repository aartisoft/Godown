package com.infosolutions.model;

public class ModelStockDetails {

    private String SOUND;
    private String DESCRIPTION;
    private String LOST_TRUCK_RECEVING;
    private String CREDIT;
    private String ON_FIELD;
    private String LOST_DELIVERY_CYLINDERS;
    private String DEFECTIVE;
    private String CLOSING_EMPTY;
    private String CLOSING_FULL;
    private String TRUCK_DEFECTIVE;
    private String OPENING_FULL;
    private String OPENING_EMPTY;
    private String SV;
    private String DELIVERY_EMPTY;
    private String DELIVERY_FULL;
    private String TV;

    public static ModelStockDetails setOpening(String DESCRIPTION, String DEFECTIVE, String OPENING_FULL, String OPENING_EMPTY){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.DEFECTIVE = DEFECTIVE;
        modelStockDetails.OPENING_FULL = OPENING_FULL;
        modelStockDetails.OPENING_EMPTY = OPENING_EMPTY;

        return modelStockDetails;
    }

    public static ModelStockDetails setClosing(String DESCRIPTION, String DEFECTIVE, String CLOSING_FULL, String CLOSING_EMPTY){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.DEFECTIVE = DEFECTIVE;
        modelStockDetails.CLOSING_FULL = CLOSING_FULL;
        modelStockDetails.CLOSING_EMPTY = CLOSING_EMPTY;

        return modelStockDetails;
    }

    public static ModelStockDetails setReceived(String DESCRIPTION, String SOUND, String LOST_TRUCK_RECEVING){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.SOUND = SOUND;
        modelStockDetails.LOST_TRUCK_RECEVING = LOST_TRUCK_RECEVING;

        return modelStockDetails;
    }

    public static ModelStockDetails setSend(String DESCRIPTION, String SOUND, String TRUCK_DEFECTIVE){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.SOUND = SOUND;
        modelStockDetails.TRUCK_DEFECTIVE = TRUCK_DEFECTIVE;

        return modelStockDetails;
    }

    public static ModelStockDetails setDelivery(String DESCRIPTION, String DEFECTIVE, String SV,
                                                String TV, String DELIVERY_FULL, String DELIVERY_EMPTY){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.DEFECTIVE = DEFECTIVE;
        modelStockDetails.SV = SV;
        modelStockDetails.TV = TV;
        modelStockDetails.DELIVERY_FULL = DELIVERY_FULL;
        modelStockDetails.DELIVERY_EMPTY = DELIVERY_EMPTY;

        return modelStockDetails;
    }

    public static ModelStockDetails setOther(String DESCRIPTION, String CREDIT, String ON_FIELD, String LOST_DELIVERY_CYLINDERS){

        ModelStockDetails modelStockDetails = new ModelStockDetails();
        modelStockDetails.DESCRIPTION = DESCRIPTION;
        modelStockDetails.CREDIT = CREDIT;
        modelStockDetails.ON_FIELD = ON_FIELD;
        modelStockDetails.LOST_DELIVERY_CYLINDERS = LOST_DELIVERY_CYLINDERS;

        return modelStockDetails;
    }

    public String getSOUND() {
        return SOUND;
    }

    public void setSOUND(String SOUND) {
        this.SOUND = SOUND;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOST_TRUCK_RECEVING() {
        return LOST_TRUCK_RECEVING;
    }

    public void setLOST_TRUCK_RECEVING(String LOST_TRUCK_RECEVING) {
        this.LOST_TRUCK_RECEVING = LOST_TRUCK_RECEVING;
    }

    public String getCREDIT() {
        return CREDIT;
    }

    public void setCREDIT(String CREDIT) {
        this.CREDIT = CREDIT;
    }

    public String getON_FIELD() {
        return ON_FIELD;
    }

    public void setON_FIELD(String ON_FIELD) {
        this.ON_FIELD = ON_FIELD;
    }

    public String getLOST_DELIVERY_CYLINDERS() {
        return LOST_DELIVERY_CYLINDERS;
    }

    public void setLOST_DELIVERY_CYLINDERS(String LOST_DELIVERY_CYLINDERS) {
        this.LOST_DELIVERY_CYLINDERS = LOST_DELIVERY_CYLINDERS;
    }

    public String getDEFECTIVE() {
        return DEFECTIVE;
    }

    public void setDEFECTIVE(String DEFECTIVE) {
        this.DEFECTIVE = DEFECTIVE;
    }

    public String getCLOSING_EMPTY() {
        return CLOSING_EMPTY;
    }

    public void setCLOSING_EMPTY(String CLOSING_EMPTY) {
        this.CLOSING_EMPTY = CLOSING_EMPTY;
    }

    public String getCLOSING_FULL() {
        return CLOSING_FULL;
    }

    public void setCLOSING_FULL(String CLOSING_FULL) {
        this.CLOSING_FULL = CLOSING_FULL;
    }

    public String getTRUCK_DEFECTIVE() {
        return TRUCK_DEFECTIVE;
    }

    public void setTRUCK_DEFECTIVE(String TRUCK_DEFECTIVE) {
        this.TRUCK_DEFECTIVE = TRUCK_DEFECTIVE;
    }

    public String getOPENING_FULL() {
        return OPENING_FULL;
    }

    public void setOPENING_FULL(String OPENING_FULL) {
        this.OPENING_FULL = OPENING_FULL;
    }

    public String getOPENING_EMPTY() {
        return OPENING_EMPTY;
    }

    public void setOPENING_EMPTY(String OPENING_EMPTY) {
        this.OPENING_EMPTY = OPENING_EMPTY;
    }

    public String getSV() {
        return SV;
    }

    public void setSV(String SV) {
        this.SV = SV;
    }

    public String getDELIVERY_EMPTY() {
        return DELIVERY_EMPTY;
    }

    public void setDELIVERY_EMPTY(String DELIVERY_EMPTY) {
        this.DELIVERY_EMPTY = DELIVERY_EMPTY;
    }

    public String getDELIVERY_FULL() {
        return DELIVERY_FULL;
    }

    public void setDELIVERY_FULL(String DELIVERY_FULL) {
        this.DELIVERY_FULL = DELIVERY_FULL;
    }

    public String getTV() {
        return TV;
    }

    public void setTV(String TV) {
        this.TV = TV;
    }
}
