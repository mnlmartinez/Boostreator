package es.boostreator.dao.imp;

import es.boostreator.dao.CoffeeMachineSiteDao;
import es.boostreator.domain.model.SiteProduct;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.model.enums.Type;
import es.boostreator.util.AppLogger;
import es.boostreator.util.Driver;
import es.boostreator.util.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CoffeeMachineECIDaoImp implements CoffeeMachineSiteDao {

    private Driver driver = DriverFactory.get();
    private Site site = Site.ELCORTEINGLES;

    private List<SiteProduct> siteProducts;
    private int maxRs;

    @Override
    public List<SiteProduct> getSiteProductList(String product, Brand brand, int maxRs) {
        this.siteProducts = new ArrayList<>();
        this.maxRs = maxRs;

        driver.get(site.getUrl());

        WebElement searchInput = driver.findElement(By.id("search-box"));
        if (searchInput == null) return error("Search input not found");
        driver.search(searchInput, product);
        this.waitPageLoad(1);

        if (brand != null && !this.selectBrand(brand)) return error("Filter brand not found");

        this.extractProducts();
        WebElement next = driver.findElement(By.xpath("//a[@rel='next']"));
        while (next != null && next.isDisplayed() && !limit()) {
            next.click();
            this.waitPageLoad(1);
            this.extractProducts();
            next = driver.findElement(By.xpath("//a[@rel='next']"));
        }

        return siteProducts;
    }

    private boolean selectBrand(Brand brand) {
        List<WebElement> brands = driver.findElements(By.cssSelector("ul.dimensions > li > a.event.facet-popup"));
        WebElement brandFound = searchBrand(brands, brand);

        if (brandFound == null) return false;

        String brandName = brandFound.getAttribute("title");

        if (!brandFound.isDisplayed()) {
            driver.findElement(By.cssSelector("#filters > li > ul > li > a.more")).click();
            this.waitPageLoad(2);
            driver.findElement(By.id("mdl-input")).sendKeys(brandName);
            this.waitPageLoad(1);

            brandFound = driver.findElement(By.cssSelector("#mdl-result-search > div.abc-box-e > a"));
            if (brandFound == null) return false;

            brandFound.click();
            this.waitPageLoad(1);
            driver.findElement(By.id("mdl-url-filter")).click();
        } else {
            brandFound.click();
        }

        this.waitPageLoad(1);
        return true;
    }

    private WebElement searchBrand(List<WebElement> brands, Brand brand) {
        for (WebElement i : brands) {
            if (brand.isEqualTo(i.getAttribute("title"))) return i;
        }
        return null;
    }

    private void extractProducts() {
        List<WebElement> elements = driver.
                findElements(By.cssSelector("div#product-list> ul > li > div.product-preview"));

        for (WebElement element : elements) {
            String priceT;
            String model = "";
            List<Type> types;

            if(limit()) break;

            model = element.findElement(By.className("js-product-click")).getAttribute("title");
            types = Type.getTypeListByProductTitle(model);

            try {
                priceT = element.findElement(By.cssSelector("div.info > div.product-price > span.current")).getText();
            } catch (NoSuchElementException ex) {
                AppLogger.log(Level.WARNING, "Error with " + model + ", price not found -- Not added to list");
                continue;
            }
            Brand brand = extractBrand(element);
            Double price = Double.valueOf(priceT
                    .split("€")[0]
                    .replace(".", "")
                    .replace(",", ".")
            );

            siteProducts.add(new SiteProduct(model, brand, types, price, this.site));
        }
    }

    private Brand extractBrand(WebElement element) {
        String title = element.findElement(By.className("js-product-click")).getAttribute("title");

        try {
            String brandName = element.findElement(By.className("brand")).getText();
            Brand brand = Brand.getByName(brandName);
            if (brand != null) return brand;
        } catch (NoSuchElementException ignored) {
        }

        try {
            Brand brand = Brand.getByProductTitle(title);
            if (brand != null) return brand;
        } catch (NoSuchElementException ignored) {
        }

        return null;
    }

    private void waitPageLoad(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<SiteProduct> error(String err) {
        AppLogger.log(Level.SEVERE, "Error in " + this.getClass().getName() + " : " + err);
        return new ArrayList<>();
    }

    private boolean limit() {
        return siteProducts.size() >= maxRs && maxRs > 0;
    }

}
