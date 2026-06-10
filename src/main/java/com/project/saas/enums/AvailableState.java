package com.project.saas.enums;


public enum AvailableState {

    ANDHRA_PRADESH("AP"),
    ARUNACHAL_PRADESH("AC"),
    ASSAM("AS"),
    BIHAR("BI"),
    CHANDIGARH("CH"),
    CHHATTISGARH("CG"),
    DELHI("DL"),
    GOA("GO"),
    GUJARAT("GU"),
    HARYANA("HA"),
    HIMACHAL_PRADESH("HP"),
    JHARKHAND("JH"),
    KARNATAKA("KA"),
    KERALA("KE"),
    MADHYA_PRADESH("MP"),
    MAHARASHTRA("MH"),
    MANIPUR("MN"),
    MEGHALAYA("ME"),
    MIZORAM("MZ"),
    NAGALAND("NA"),
    ODISHA("OD"),
    PUNJAB("PB"),
    RAJASTHAN("RJ"),
    SIKKIM("SI"),
    TAMIL_NADU("TN"),
    TELANGANA("TG"),
    TRIPURA("TR"),
    UTTAR_PRADESH("UP"),
    UTTARAKHAND("UA"),
    WEST_BENGAL("WB");

    private final String code;

    public String getCode(){return code; }

    AvailableState(String s) {
        this.code =s;
    }
}
