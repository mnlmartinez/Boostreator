package es.boostreator.dao;

import es.boostreator.domain.model.SiteProduct;
import es.boostreator.domain.model.enums.Brand;

import java.util.List;

public interface CoffeeMachineSiteDao {

    List<SiteProduct> getSiteProductList(String product, Brand brand);

}
