package es.boostreator.domain.service;

import es.boostreator.dao.CoffeeMachineDao;
import es.boostreator.dao.imp.CoffeeMachineDaoImp;
import es.boostreator.domain.model.Product;
import es.boostreator.domain.model.ProductMapper;
import es.boostreator.domain.model.SiteProduct;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeMachineService {

    private CoffeeMachineDao dao = new CoffeeMachineDaoImp();

    public List<Product> getProductList(String product, List<Brand> brands, List<Site> sites, int maxRs) {
        Map<Site, List<SiteProduct>> siteProductsMap = new HashMap<>();
        List<Product> products = new ArrayList<>();

        for (Site site : sites) {
            siteProductsMap.put(site, new ArrayList<>());
            if (brands.size() == 0)
                siteProductsMap
                        .get(site)
                        .addAll(dao.getCoffeeMachineDao(site).getSiteProductList(product, null, maxRs));
            for (Brand brand : brands) {
                siteProductsMap
                        .get(site)
                        .addAll(dao.getCoffeeMachineDao(site).getSiteProductList(product, brand, maxRs));
            }
        }

        siteProductsMap.forEach((site, siteProducts) ->
                products.addAll(ProductMapper.siteProductList2ProductList(siteProducts)));

        return products;
    }

}
