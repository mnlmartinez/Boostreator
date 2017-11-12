package es.boostreator.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static List<Product> siteProductList2ProductList(List<SiteProduct> siteProducts){
        List<Product> products = new ArrayList<>();
        siteProducts.forEach(siteProduct -> products.add(siteProduct2Product(siteProduct)));
        return products;
    }

    public static Product siteProduct2Product(SiteProduct siteProduct) {
        Product product = new Product(siteProduct.getType(), siteProduct.getModel(), siteProduct.getBrand());
        product.getPrice().put(siteProduct.getSite(), siteProduct.getPrice());
        return product;
    }

}
