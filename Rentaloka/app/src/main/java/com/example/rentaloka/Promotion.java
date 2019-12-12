package com.example.rentaloka;

public class Promotion {
    private String promoID;
    private String promoName;
    private String promoDesc;
    private int promoDisc;

    public Promotion(){

    }

    public Promotion(String pName, String pDesc, String pID, int pDisc){
        promoName = pName;
        promoDesc = pDesc;
        promoID = pID;
        promoDisc = pDisc;
    }
    public Integer getPromoDisc() {
        return promoDisc;
    }

    public String getPromotionName() {
        return promoName;
    }

    public String getPromotionDesc() {
        return promoDesc;
    }

    public void setPromotionName(String promotionName) {
        this.promoName = promotionName;
    }

    public void setPromoDisc(int promoDisc) {
        this.promoDisc = promoDisc;
    }

    public String getPromoID() {
        return promoID;
    }

    public void setPromoID(String promoID) {
        this.promoID = promoID;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promoDesc = promotionDesc;
    }

}