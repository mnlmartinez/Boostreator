package es.boostreator.util;

import org.openqa.selenium.*;

import java.util.List;

public class Driver {

    private WebDriver driver;

    Driver(WebDriver driver) {
        this.driver = driver;
    }

    public void waitPageLoad() {
        try {
            while (true) {
                Thread.sleep(1000);
                if ((Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return jQuery.active == 0")) break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void search(WebElement element, String text) {
        element.sendKeys(text);
        element.submit();
    }

    public void get(String url) {
        this.driver.get(url);
    }

    public List<WebElement> findElements(By by) {
        return this.driver.findElements(by);
    }

    public WebElement findElement(By by) {
        try {
            return this.driver.findElement(by);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public void close() {
        this.driver.close();
    }

    public void quit() {
        this.driver.quit();
    }

}
