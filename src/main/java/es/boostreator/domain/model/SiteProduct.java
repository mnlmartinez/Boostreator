package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;

public class SiteProduct {

    private String type;
    private String model;
    private Brand brand;
    private Double price;
    private Site site;

    public SiteProduct(String type, String model, Brand brand, Double price, Site site) {
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
