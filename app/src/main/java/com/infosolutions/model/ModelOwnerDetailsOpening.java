package com.infosolutions.model;

public class ModelOwnerDetailsOpening {

    private String DESCRIPTION;
    private String DEFECTIVE;
    private String OPENING_FULL;
    private String OPENING_EMPTY;

    public ModelOwnerDetailsOpening() {
    }

    public ModelOwnerDetailsOpening(String DESCRIPTION, String DEFECTIVE, String OPENING_FULL, String OPENING_EMPTY) {
        this.DESCRIPTION = DESCRIPTION;
        this.DEFECTIVE = DEFECTIVE;
        this.OPENING_FULL = OPENING_FULL;
        this.OPENING_EMPTY = OPENING_EMPTY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getDEFECTIVE() {
        return DEFECTIVE;
    }

    public void setDEFECTIVE(String DEFECTIVE) {
        this.DEFECTIVE = DEFECTIVE;
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
}
