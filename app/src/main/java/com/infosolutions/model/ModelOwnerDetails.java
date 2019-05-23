package com.infosolutions.model;

public class ModelOwnerDetails {

    private String SV;
    private String DBC;
    private String DEFECTIVE;
    private String DESCRIPTION;
    private String LOST;
    private String DELIVERY;
    private String DisplayName;
    private String CREDIT;
    private String DELIVERY_EMPTY;
    private String DELIVERY_FULL;
    private String OPENING_FULL;
    private String OPENING_EMPTY;
    private String CLOSING_EMPTY;
    private String CLOSING_FULL;
    private String SOUND;
    private String LOST_TRUCK_RECEVING;
    private String TRUCK_DEFECTIVE;
    private String CYLINDER;
    private String ON_FIELD;
    private String LOST_DELIVERY_CYLINDERS;


    public ModelOwnerDetails() {
    }

    public static ModelOwnerDetails setOther(String DESCRIPTION, String CREDIT, String LOST, String ON_FIELD){

        ModelOwnerDetails modelOwnerDetails = new ModelOwnerDetails();
        modelOwnerDetails.DESCRIPTION = DESCRIPTION;
        modelOwnerDetails.CREDIT = CREDIT;
        modelOwnerDetails.LOST = LOST;
        modelOwnerDetails.ON_FIELD = ON_FIELD;

        return modelOwnerDetails;
    }

    public static ModelOwnerDetails setSend(String DESCRIPTION, String SOUND, String DEFECTIVE){

        ModelOwnerDetails modelOwnerDetails = new ModelOwnerDetails();
        modelOwnerDetails.DESCRIPTION = DESCRIPTION;
        modelOwnerDetails.SOUND = SOUND;
        modelOwnerDetails.DEFECTIVE = DEFECTIVE;

        return modelOwnerDetails;
    }

    public ModelOwnerDetails(String DESCRIPTION, String OPENING_FULL, String OPENING_EMPTY, String DEFECTIVE) {
        this.DESCRIPTION = DESCRIPTION;
        this.OPENING_FULL = OPENING_FULL;
        this.OPENING_EMPTY = OPENING_EMPTY;
        this.DEFECTIVE = DEFECTIVE;
    }

    public ModelOwnerDetails(String DESCRIPTION, String DELIVERY, String SV, String DBC, String DEFECTIVE, String LOST) {
        this.DESCRIPTION = DESCRIPTION;
        this.DELIVERY = DELIVERY;
        this.SV = SV;
        this.DBC = DBC;
        this.DEFECTIVE = DEFECTIVE;
        this.LOST = LOST;
    }

    public ModelOwnerDetails(String DESCRIPTION, String SOUND, String LOST) {
        this.DESCRIPTION = DESCRIPTION;
        this.SOUND = SOUND;
        this.LOST = LOST;
    }

    public String getSV() {
        return SV;
    }

    public void setSV(String SV) {
        this.SV = SV;
    }

    public String getDBC() {
        return DBC;
    }

    public void setDBC(String DBC) {
        this.DBC = DBC;
    }

    public String getDEFECTIVE() {
        return DEFECTIVE;
    }

    public void setDEFECTIVE(String DEFECTIVE) {
        this.DEFECTIVE = DEFECTIVE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOST() {
        return LOST;
    }

    public void setLOST(String LOST) {
        this.LOST = LOST;
    }

    public String getDELIVERY() {
        return DELIVERY;
    }

    public void setDELIVERY(String DELIVERY) {
        this.DELIVERY = DELIVERY;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getCREDIT() {
        return CREDIT;
    }

    public void setCREDIT(String CREDIT) {
        this.CREDIT = CREDIT;
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

    public String getSOUND() {
        return SOUND;
    }

    public void setSOUND(String SOUND) {
        this.SOUND = SOUND;
    }

    public String getLOST_TRUCK_RECEVING() {
        return LOST_TRUCK_RECEVING;
    }

    public void setLOST_TRUCK_RECEVING(String LOST_TRUCK_RECEVING) {
        this.LOST_TRUCK_RECEVING = LOST_TRUCK_RECEVING;
    }

    public String getTRUCK_DEFECTIVE() {
        return TRUCK_DEFECTIVE;
    }

    public void setTRUCK_DEFECTIVE(String TRUCK_DEFECTIVE) {
        this.TRUCK_DEFECTIVE = TRUCK_DEFECTIVE;
    }

    public String getCYLINDER() {
        return CYLINDER;
    }

    public void setCYLINDER(String CYLINDER) {
        this.CYLINDER = CYLINDER;
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
}
