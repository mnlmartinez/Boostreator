package es.boostreator.model;

public class Product {

    private Site site;
    private String type;
    private String model;
    private String brand;
    private Double price;

    public Product(Site site, String type, String model, String brand, Double price) {
        this.site = site;
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.price = price;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
