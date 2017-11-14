package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductMapper {

    public static List<Product> siteProductList2ProductList(List<SiteProduct> siteProducts) {
        List<Product> products = new ArrayList<>();
        siteProducts.forEach(siteProduct -> products.add(siteProduct2Product(siteProduct)));
        return products;
    }

    public static Product siteProduct2Product(SiteProduct siteProduct) {
        Product product = new Product(siteProduct.getModel(), siteProduct.getBrand(), siteProduct.getTypes());
        product.getPrice().put(siteProduct.getSite(), siteProduct.getPrice());
        return product;
    }

//    public static List<Product> siteProductMap2ProductList(Map<Site, List<SiteProduct>> siteProductsMap) {
//        List<Product> products = new ArrayList<>();
//        List<SiteProduct> fnac = siteProductsMap.get(Site.FNAC);
//        List<SiteProduct> eci = siteProductsMap.get(Site.ELCORTEINGLES);
//
//
//        for (SiteProduct p1 : fnac) {
//            int max = 2;
//            for (SiteProduct p2 : eci) {
//
//            }
//        }
//
//        return products;
//    }
//
//    public static Product siteProduct2Product(SiteProduct siteProduct) {
//        Product product = new Product(siteProduct.getModel(), siteProduct.getBrand(), siteProduct.getTypes());
//        product.getPrice().put(siteProduct.getSite(), siteProduct.getPrice());
//        return product;
//    }
//
//    private static int puntuation(SiteProduct p1, SiteProduct p2) {
//
//        return 0;
//    }

}
