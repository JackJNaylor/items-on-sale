package com.naylorrbc.itemsonsale.saleList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://rbc.shopping.com")
@RequestMapping(path = "recommendations")
public class SaleListController {

    private final SaleListService saleListService;

    //Autowired automatically instantiates saleListService with Dependency Injection
    @Autowired
    public SaleListController(SaleListService saleListService) {
        this.saleListService = saleListService;
    }

    @GetMapping(
            path = "{userId}"
    )
    public SaleList getSaleList(@PathVariable("userId") Integer userId){
        return saleListService.getSaleList(userId);
    }

}
