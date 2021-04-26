package com.naylorrbc.itemsonsale.mockAPIs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MockProductsOnSaleAPI {

    public static JSONObject get(Integer userId) {
        JSONObject object = new JSONObject();
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/com/naylorrbc/itemsonsale/mockAPIs/products_on_sale_mock_payload.json";


        try (FileReader reader = new FileReader(filePath))
        {
            //Read JSON file
            Object obj = parser.parse(reader);

            JSONObject saleItems = (JSONObject) obj;
            return saleItems;
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
