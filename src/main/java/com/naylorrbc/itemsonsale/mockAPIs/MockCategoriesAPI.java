package com.naylorrbc.itemsonsale.mockAPIs;

import java.util.ArrayList;

public class MockCategoriesAPI {

    public static ArrayList<String> get(){
         ArrayList<String> categories = new ArrayList<String>();
         categories.add("Sports");
         categories.add("Kitchen");
         categories.add("Electronics");
         categories.add("Board Games");
         categories.add("Music");

         return categories;
    }
}
