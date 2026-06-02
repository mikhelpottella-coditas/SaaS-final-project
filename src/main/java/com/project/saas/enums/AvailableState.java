package com.project.saas.enums;

import org.springframework.security.crypto.password.PasswordEncoder;

public enum AvailableState {

    ANDHRA_PRADESH(code = "AP"),
    ARUNACHAL_PRADESH(code = "AC"),
    ASSAM(code = "AS"),
    BIHAR(code = "BI"),
    CHANDIGARH(code = "CH"),
    CHHATTISGARH(code = "CG"),
    DELHI(code = "DL"),
    GOA(code = "GO"),
    GUJARAT(code = "GU"),
    HARYANA(code = "HA"),
    HIMACHAL_PRADESH(code = "HP"),
    JHARKHAND(code = "JH"),
    KARNATAKA(code = "KA"),
    KERALA(code = "KE"),
    MADHYA_PRADESH(code = "MP"),
    MAHARASHTRA(code = "MH"),
    MANIPUR(code = "MN"),
    MEGHALAYA(code = "ME"),
    MIZORAM(code = "MZ"),
    NAGALAND(code = "NA"),
    ODISHA(code = "OD"),
    PUNJAB(code = "PB"),
    RAJASTHAN(code = "RJ"),
    SIKKIM(code = "SI"),
    TAMIL_NADU(code = "TN"),
    TELANGANA(code = "TG"),
    TRIPURA(code = "TR"),
    UTTAR_PRADESH(code = "UP"),
    UTTARAKHAND(code = "UA"),
    WEST_BENGAL(code = "WB");

    private static String code;

    public String getCode(){return code; }

    AvailableState(String s) {
    }
}
