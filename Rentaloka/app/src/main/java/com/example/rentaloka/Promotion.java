package com.example.rentaloka;

public class Promotion {
    private String promoID;
    private String promoName;
    private String promoDesc;

    public Promotion(){

    }

    public Promotion(String pName, String pDesc, String pID){
        promoName = pName;
        promoDesc = pDesc;
        promoID = pID;
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
