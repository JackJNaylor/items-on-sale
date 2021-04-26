package com.naylorrbc.itemsonsale.saleList;

import com.naylorrbc.itemsonsale.mockAPIs.MockCategoriesAPI;
import com.naylorrbc.itemsonsale.mockAPIs.MockMyOrdersAPI;
import com.naylorrbc.itemsonsale.mockAPIs.MockProductsOnSaleAPI;
import com.naylorrbc.itemsonsale.mockAPIs.MockWishListAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleListService {

    public SaleList getSaleList(Integer userId){
        // APIs Stubbed out to mock microservice architecture
        // Get user orders
        JSONObject myOrders = MockMyOrdersAPI.get(userId);

        // Get all categories
        ArrayList<String> categories = MockCategoriesAPI.get();

        // Get user Wishlist
        JSONObject wishlist = MockWishListAPI.get(userId);

        // Get Products on Sale
        JSONObject saleProducts = MockProductsOnSaleAPI.get(userId);

        // Get average category ratings for my orders items
        HashMap<String, Double> ordersCategoryAverages = getCategoryRatings(myOrders, categories);

        // get average category ratings for wishlist items
        HashMap<String, Double> wishlistCategoryTotals = getWishlistTotals(wishlist, categories);

        // Filter top 5 hottest deals based on other users ratings
        JSONArray hotDeals = filterHotDeals(saleProducts);

        // Combine orders and wishlist category ratings maps and sort by highest rated category
        HashMap<String, Double> ordersWishlistCategories = combineHashMaps(ordersCategoryAverages, wishlistCategoryTotals);

        // Sort Categories from most to least relevant
        LinkedHashMap<String, Double> sortedCategories = sortByValue(ordersWishlistCategories);

        // Generate recommended products list in order of relevant categories
        JSONArray recommendedProducts = getRecommendations(sortedCategories, saleProducts);

        SaleList saleList = new SaleList(
                userId.longValue(),
                hotDeals,
                recommendedProducts);

        return saleList;

    }

    // Get recommendations by adding sale products to json array in order of most relevant categories
    private JSONArray getRecommendations(LinkedHashMap<String, Double> sortedCategories, JSONObject saleProducts) {
        JSONArray products = (JSONArray) saleProducts.get("products");
        JSONArray recommendedProducts = new JSONArray();

        for (Map.Entry<String, Double> entry : sortedCategories.entrySet()) {
            String key = entry.getKey();
            for ( Object product : products) {
                JSONObject obj = (JSONObject) product;
                String cat = (String) obj.get("category");
                if (cat.equals(key)) {
                    recommendedProducts.add(obj);
                }
            }
        }

        return recommendedProducts;
    }

    // combine two hashmaps to get average rating between wishlist and previous orders
    private HashMap<String, Double> combineHashMaps(HashMap<String, Double> ordersCategoryAverages, HashMap<String, Double> wishlistCategoryTotals) {
        HashMap<String, Double> combinedMap = new HashMap<>();
        HashSet<String> combinedKeys = new HashSet<>();

        for (Map.Entry<String, Double> entry : ordersCategoryAverages.entrySet()) {
            combinedKeys.add(entry.getKey());
        }

        for (Map.Entry<String, Double> entry : wishlistCategoryTotals.entrySet()) {
            combinedKeys.add(entry.getKey());
        }

        for (String key : combinedKeys) {
            Double myOrdersAvg = ordersCategoryAverages.getOrDefault(key, 0.0);
            Double wishListAvg = wishlistCategoryTotals.getOrDefault(key, 0.0);
            Double avg = ( myOrdersAvg + wishListAvg ) / 2.0;
            if (avg > 0.0) {
                combinedMap.put(key, avg);
            }
        }

        return combinedMap;
    }

    // Get Top 5 Hot Deals from Full Product List
    private JSONArray filterHotDeals(JSONObject saleProducts) {
        JSONArray products = (JSONArray) saleProducts.get("products");
        JSONArray hotDeals = new JSONArray();
        try {
            Integer productSize = products.size();
            int hotDealsLength;

            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            for (int i = 0; i < products.size(); i++) {
                jsonList.add((JSONObject) products.get(i));
            }

            Collections.sort(jsonList, new Comparator<JSONObject>() {

                public int compare(JSONObject a, JSONObject b) {

                    String valA = a.get("rating").toString();
                    String valB = b.get("rating").toString();


                    return valB.compareTo(valA);
                }
            });

            // Get top 5 hot deals, or all deals if less than 5
            if (productSize >= 5) {
                hotDealsLength = 5;
            } else {
                hotDealsLength = productSize;
            }

            for (int i = 0; i < hotDealsLength; i++) {
                hotDeals.add(jsonList.get(i));
            }

            return hotDeals;
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return new JSONArray();
    }

    // Get Total Ratings for each Wishlist Category
    private HashMap<String, Double> getWishlistTotals(JSONObject wishlist, ArrayList<String> categories) {
        HashMap<String, Integer> wishlistCategoryTotals = new HashMap<>();
        JSONArray products = (JSONArray) wishlist.get("products");
        try {
            int length = products.size();

            for (String category : categories) {
                wishlistCategoryTotals.put(category, 0);
            }

            for (Object product : products) {
                JSONObject obj = (JSONObject) product;
                String cat = (String) obj.get("category");
                int count = wishlistCategoryTotals.containsKey(cat) ? wishlistCategoryTotals.get(cat) : 0;
                wishlistCategoryTotals.put(cat, count + 1);
            }

            HashMap<String, Double> wishlistCategoryAverages = getWishlistAverages(wishlistCategoryTotals, length);

            return wishlistCategoryAverages;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new HashMap<String, Double>();
    }

    // change count of each category in wishlist to a scale of 0-5 for even combination with my orders ratings (also 0-5)
    private HashMap<String, Double> getWishlistAverages(HashMap<String, Integer> wishlistCategoryTotals, int length) {
        HashMap<String, Double> wishlistCategoryAverages = new HashMap<>();

        for (HashMap.Entry<String, Integer> entry : wishlistCategoryTotals.entrySet()) {
            String key = entry.getKey();
            double average = wishlistCategoryTotals.get(key) / (double) length;
            double averageScaled = average * 5;
            wishlistCategoryAverages.put(key, averageScaled);
        }

        return wishlistCategoryAverages;
    }

    // Get Ratings for each Category in My Orders
    private HashMap<String, Double> getCategoryRatings(JSONObject myOrders, ArrayList<String> categories){
        try {
            JSONArray products = (JSONArray) myOrders.get("products");
            HashMap<String, Integer> categoryRatings = new HashMap<>();
            HashMap<String, Integer> categoryLengths = new HashMap<>();

            // Total Rating for each category
            for (Object product : products) {
                JSONObject obj = (JSONObject) product;
                String cat = (String) obj.get("category");
                Integer rating = ((Long) obj.get("rating")).intValue();
                int count = categoryRatings.containsKey(cat) ? categoryRatings.get(cat) : 0;
                categoryRatings.put(cat, count + rating);
                int length = categoryLengths.containsKey(cat) ? categoryLengths.get(cat) : 0;
                categoryLengths.put(cat, length + 1);
            }

            HashMap<String, Double> categoryAverages = getCategoryAverages(categoryRatings, categoryLengths);

            return categoryAverages;
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return new HashMap<String, Double>();
    }

    // Get Category Average Ratings from My Orders
    private HashMap<String, Double> getCategoryAverages(HashMap<String, Integer> categoryRatings, HashMap<String, Integer> categoryLengths) {
        HashMap<String, Double> categoryAverages = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry : categoryRatings.entrySet()) {
            String key = entry.getKey();
            double average = categoryRatings.get(key) / (double) categoryLengths.get(key);
            categoryAverages.put(key, average);
        }
        return categoryAverages;
    }

    // Sort Hash Map by Value
    public static LinkedHashMap<String, Double> sortByValue(HashMap<String, Double> map) {

        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
        list.forEach(e -> sortedMap.put(e.getKey(), e.getValue()));
        return sortedMap;
    }
}
