package es.boostreator.domain.model;

import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.model.enums.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {

    private String model;
    private Brand brand;
    private List<Type> types;
    private Map<Site, Double> price;

    public Product(String model, Brand brand, List<Type> types) {
        this.model = model;
        this.brand = brand;
        this.types = types;
        this.price = new HashMap<>();
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

    public Map<Site, Double> getPrice() {
        return price;
    }

    public void setPrice(Map<Site, Double> price) {
        this.price = price;
    }

    public String toStringPrice(Site site) {
        return this.getPrice().get(site).toString();
    }

    public String toStringTypes() {
        String str = "";
        for (int i = 0; i < types.size(); i++) {
            if (i == 0) str = types.get(0).name();
            else str += ", " + types.get(i).name();
        }
        return str;
    }

    @Override
    public String toString() {
        return "Product{" +
                "model='" + model + '\'' +
                ", brand=" + brand +
                ", types=" + types +
                ", price=" + price +
                '}';
    }
}
