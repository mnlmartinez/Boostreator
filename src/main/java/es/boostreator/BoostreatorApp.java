package es.boostreator;


import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.service.CoffeeMachineService;
import es.boostreator.util.AppLogger;

import java.util.ArrayList;
import java.util.List;

public class BoostreatorApp {


    public static void main(String[] args) {
        AppLogger.debugMode();

        CoffeeMachineService coffeeMachineService = new CoffeeMachineService();

        String product = "Cafetera";
        List<Site> sites = new ArrayList<>();
        List<Brand> brands = new ArrayList<>();

        sites.add(Site.ELCORTEINGLES);
        sites.add(Site.FNAC);
        brands.add(Brand.DELONGHI);
        //brands.add(Brand.PHILIPS);

        coffeeMachineService.getProductList(product, brands, sites, 20).forEach(System.out::println);
    }
}
