package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.model.enums.Type;

import java.util.List;

public class SiteProduct {

    private String model;
    private Brand brand;
    private List<Type> types;
    private Double price;
    private Site site;

    public SiteProduct(String model, Brand brand, List<Type> types, Double price, Site site) {
        this.model = model;
        this.brand = brand;
        this.types = types;
        this.price = price;
        this.site = site;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

}
