package com.naylorrbc.itemsonsale.saleList;

import org.json.simple.JSONArray;

public class SaleList {

    private Long id;
    private Long userId;
    private JSONArray hotDeals;

    public JSONArray getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(JSONArray recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }

    private JSONArray recommendedProducts;

    public JSONArray getHotDeals() {
        return hotDeals;
    }

    public void setHotDeals(JSONArray hotDeals) {
        this.hotDeals = hotDeals;
    }

    public SaleList(Long id, Long userId, JSONArray recommendedProducts) {
        this.id = id;
        this.userId = userId;
        this.recommendedProducts = recommendedProducts;
    }

    public SaleList(Long userId, JSONArray recommendedProducts) {
        this.userId = userId;
        this.recommendedProducts = recommendedProducts;
    }

    public SaleList(long id, long userId, JSONArray hotDeals, JSONArray recommendedProducts) {
        this.id = id;
        this.userId = userId;
        this.hotDeals = hotDeals;
        this.recommendedProducts = recommendedProducts;
    }

//    public SaleList(long id, long userId) {
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", userId=" + userId +
                ", hotDeals=" + hotDeals +
                ", recommendedProducts=" + recommendedProducts +
                '}';
    }
}
