package com.naylorrbc.itemsonsale.saleList;

import org.json.simple.JSONArray;

public class SaleList {

    private Long userId;
    private JSONArray hotDeals;
    private JSONArray recommendedProducts;

    public JSONArray getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(JSONArray recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }

    public JSONArray getHotDeals() {
        return hotDeals;
    }

    public void setHotDeals(JSONArray hotDeals) {
        this.hotDeals = hotDeals;
    }

    public SaleList(long userId, JSONArray hotDeals, JSONArray recommendedProducts) {
        this.userId = userId;
        this.hotDeals = hotDeals;
        this.recommendedProducts = recommendedProducts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SaleList{" +
                "userId=" + userId +
                ", hotDeals=" + hotDeals +
                ", recommendedProducts=" + recommendedProducts +
                '}';
    }
}
