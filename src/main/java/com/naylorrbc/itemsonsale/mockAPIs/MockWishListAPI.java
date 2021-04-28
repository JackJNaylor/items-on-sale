package com.naylorrbc.itemsonsale.mockAPIs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MockWishListAPI {

    public static JSONObject get(Integer userId) {
        JSONObject object = new JSONObject();
        JSONParser parser = new JSONParser();
        String filePath = new String();

        switch (userId){
            case 1:
                filePath = "src/main/java/com/naylorrbc/itemsonsale/mockAPIs/wishlist_mock_payload_1.json";
                break;
            case 2:
                filePath = "src/main/java/com/naylorrbc/itemsonsale/mockAPIs/wishlist_mock_payload_2.json";
                break;
        }

        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = parser.parse(reader);

            JSONObject wishlist = (JSONObject) obj;
            return wishlist;
            //Iterate over employee array

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

}

