package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private String type;
    private String model;
    private Brand brand;
    private Map<Site, Double> price;

    public Product(String type, String model, Brand brand) {
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.price = new HashMap<>();
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

    public Map<Site, Double> getPrice() {
        return price;
    }

    public void setPrice(Map<Site, Double> price) {
        this.price = price;
    }
}
