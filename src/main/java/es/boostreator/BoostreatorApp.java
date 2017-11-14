package es.boostreator;


import es.boostreator.domain.model.Product;
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

        sites.add(Site.FNAC);
        sites.add(Site.ELCORTEINGLES);
        brands.add(Brand.DELONGHI);
        brands.add(Brand.PHILIPS);

        List<Product> products = coffeeMachineService.getProductList(product, brands, sites, 10);

        products.forEach(System.out::println);

//        System.out.println("NO RELACIONADOS\n");
//        products.forEach(e -> {
//            if (e.getPrice().size()==1) System.out.println(e);
//        });
//
//        System.out.println("");
//        System.out.println("RELACIONADOS\n");
//        products.forEach(e -> {
//            if (e.getPrice().size()>1) System.out.println(e);
//        });
    }
}
