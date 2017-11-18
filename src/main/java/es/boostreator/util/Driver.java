package es.boostreator.util;

import org.openqa.selenium.*;

import java.util.List;

public class Driver {

    private WebDriver driver;

    Driver(WebDriver driver) {
        this.driver = driver;
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

    public WebDriver getDriver() {
        return this.driver;
    }

    public void scroll(int x, int y){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(" + x + "," + y + ")", "");
    }

    void quit() {
        this.driver.quit();
    }

}
