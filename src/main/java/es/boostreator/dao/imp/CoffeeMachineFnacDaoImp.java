package es.boostreator.dao.imp;

import es.boostreator.dao.CoffeeMachineSiteDao;
import es.boostreator.domain.model.SiteProduct;
import es.boostreator.domain.model.enums.Brand;
import es.boostreator.domain.model.enums.Site;
import es.boostreator.util.AppLogger;
import es.boostreator.util.Driver;
import es.boostreator.util.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CoffeeMachineFnacDaoImp implements CoffeeMachineSiteDao {

    private Driver driver;
    private Site site = Site.FNAC;

    @Override
    public List<SiteProduct> getSiteProductList(String product, Brand brand) {
        List<SiteProduct> siteProducts = new ArrayList<>();
        driver = DriverFactory.get();

        driver.get(site.getUrl());
        driver.findElement(By.className("js-Cnilbar-close")).click();
        WebElement searchInput = driver.findElement(By.id("Fnac_Search"));

        if (searchInput != null) {
            driver.search(searchInput, product);
            driver.waitPageLoad();
        } else {
            AppLogger.log(Level.SEVERE, "Fail in FnacDAO, search input not found");
            return siteProducts;
        }

        WebElement visualButton = driver.findElement(By.className("mosaicButton"));
        if (visualButton != null) {
            visualButton.click();
            driver.waitPageLoad();
        }

        if (brand != null) this.selectBrand(brand);
        this.selectItemsPerPage();

        siteProducts.addAll(extractProducts());

        WebElement next = driver.findElement(By.className("actionNext"));

        while (next != null && next.isDisplayed()) {
            next.click();
            driver.waitPageLoad();

            siteProducts.addAll(extractProducts());

            next = driver.findElement(By.className("actionNext"));
        }

        DriverFactory.close();
        return siteProducts;
    }

    private void selectBrand(Brand brand) {
        WebElement filterButton = driver.findElement(By.className("js-toggleFilters-more"));
        if (filterButton != null) {
            filterButton.click();
            driver.waitPageLoad();
        }

        List<WebElement> brands = driver.findElements(By.className("Filters-choice"));
        WebElement brandFound = null;

        for (WebElement i : brands) {
            if (brand.isEqualTo(i.getAttribute("title"))) {
                brandFound = i;
                break;
            }
        }

        if (brandFound != null) {
            brandFound.click();
            driver.waitPageLoad();
        } else {
            AppLogger.log(Level.SEVERE, "Fail in FnacDAO, filter brand not found");
        }
    }

    private void selectItemsPerPage() {
        WebElement comboBox = driver.findElement(By.className("Item-onPage"));
        if (comboBox != null) {
            comboBox.click();
            driver.waitPageLoad();
        }

        WebElement pageSizeElement = driver.findElement(By.xpath("//li[@data-value='100']"));
        if (pageSizeElement == null) pageSizeElement = driver.findElement(By.xpath("//li[@data-value='120']"));
        if (pageSizeElement != null) {
            pageSizeElement.click();
            driver.waitPageLoad();
        }
    }

    private List<SiteProduct> extractProducts() {
        List<SiteProduct> siteProducts = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.className("Article-mosaicItem"));

        for (WebElement element : elements) {
            String type;
            String priceT;
            String model = "";

            try {
                type = element.findElement(By.className("thumbnail-sub")).getText();
                model = element.findElement(By.className("thumbnail-titleLink")).getText();
                priceT = element.findElement(By.className("thumbnail-price")).getText();
            } catch (NoSuchElementException ex) {
                AppLogger.log(Level.WARNING, "Error with " + model + " -- Not added to list");
                continue;
            }

            Brand brand = extractBrand(element);
            Double price = Double.valueOf(priceT
                    .split("€")[0]
                    .replace(".", "")
                    .replace(",", ".")
            );

            siteProducts.add(new SiteProduct(type, model, brand, price, this.site));
        }

        return siteProducts;
    }

    private Brand extractBrand(WebElement element) {
        Brand brand;
        String productTitle = "";

        try {
            String brandName = element
                    .findElement(By.className("thumbnail-sub"))
                    .findElement(By.xpath("./descendant::span/descendant::a")).getText();
            brand = Brand.getByName(brandName);
            if (brand != null) return brand;
        } catch (NoSuchElementException ignored) {

        }

        try {
            productTitle = element.findElement(By.className("thumbnail-titleLink")).getText();
            brand = Brand.getByProductTitle(productTitle);
            if (brand != null) return brand;
        } catch (NoSuchElementException ignored) {

        }

        AppLogger.log(Level.INFO, "Brand not found in \"" + productTitle + "\"");
        return null;
    }


}
