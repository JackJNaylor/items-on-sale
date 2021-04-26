package com.naylorrbc.itemsonsale.mockAPIs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MockHotDealsAPI {

    public static JSONObject get(Integer userId) {
        JSONObject object = new JSONObject();
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/com/naylorrbc/itemsonsale/mockAPIs/hot_deals.json";

//        switch (userId){
//            case 1:
//                String filePath = "my_orders_mock_payload_1.json";
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//        }
        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = parser.parse(reader);

            JSONObject hotDeals = (JSONObject) obj;
            return hotDeals;
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
