package es.boostreator.dao.imp;

import es.boostreator.dao.CoffeeMachineSiteDao;
import es.boostreator.domain.model.SiteProduct;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.domain.model.enums.Type;
import es.boostreator.util.AppLogger;
import es.boostreator.util.Driver;
import es.boostreator.util.DriverFactory;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CoffeeMachineFnacDaoImp implements CoffeeMachineSiteDao {

    private Driver driver = DriverFactory.get();
    private Site site = Site.FNAC;

    private List<SiteProduct> siteProducts;
    private int maxRs;

    @Override
    public List<SiteProduct> getSiteProductList(String product, Brand brand, int maxRs) {
        this.siteProducts = new ArrayList<>();
        this.maxRs = maxRs;

        driver.get(site.getUrl());

        WebElement closeCookies = driver.findElement(By.className("js-Cnilbar-close"));
        if (closeCookies != null) closeCookies.click();

        WebElement searchInput = driver.findElement(By.id("Fnac_Search"));
        if (searchInput == null) return error("Search input not found");
        driver.search(searchInput, product);
        this.waitPageLoad();

        WebElement visualButton = driver.findElement(By.className("mosaicButton"));
        if (visualButton != null) {
            visualButton.click();
            this.waitPageLoad();
        }

        if (brand != null && !this.selectBrand(brand)) return error("Filter brand not found");

        this.extractProducts();
        WebElement next = driver.findElement(By.className("actionNext"));
        while (next != null && next.isDisplayed() && !limit()) {
            next.click();
            this.waitPageLoad();
            this.extractProducts();
            next = driver.findElement(By.className("actionNext"));
        }

        return siteProducts;
    }

    private boolean selectBrand(Brand brand) {
        WebElement filterButton = driver.findElement(By.className("toggleFilters"));
        driver.scroll(0, 450);
        if (filterButton != null) {
            try {
                filterButton.click();
            } catch (WebDriverException ex) {
                return false;
            }
            this.waitPageLoad();
        }

        List<WebElement> brands = driver.findElements(By.className("Filters-choice"));
        WebElement brandFound = this.searchBrand(brands, brand);

        if (brandFound == null) return false;

        brandFound.click();
        this.waitPageLoad();
        return true;
    }

    private WebElement searchBrand(List<WebElement> brands, Brand brand) {
        for (WebElement i : brands) {
            if (brand.isEqualTo(i.getAttribute("title"))) return i;
        }
        return null;
    }

    private void extractProducts() {
        List<WebElement> elements = driver.findElements(By.className("Article-mosaicItem"));

        for (WebElement element : elements) {
            String model = "";
            String priceT;
            List<Type> types;

            if (limit()) break;

            model = element.findElement(By.className("thumbnail-titleLink")).getText();
            types = Type.getTypeListByProductTitle(model);

            try {
                priceT = element.findElement(By.className("thumbnail-price")).getText();
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
        String title = element.findElement(By.className("thumbnail-titleLink")).getText();

        try {
            String brandName = element
                    .findElement(By.className("thumbnail-sub"))
                    .findElement(By.xpath("./descendant::span/descendant::a")).getText();
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

    private void waitPageLoad() {
        try {
            while (true) {
                Thread.sleep(1000);
                if ((Boolean) ((JavascriptExecutor) driver.getDriver())
                        .executeScript("return jQuery.active == 0")) break;
            }
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
