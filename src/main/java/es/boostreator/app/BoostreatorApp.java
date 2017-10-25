package es.boostreator.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BoostreatorApp {

    public static void main(String[] args) {

        WebDriver driver = WebDriverFactory.get(WebDriverFactory.CHROME);

        driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name("q"));
        System.out.println(element.getText());
        element.sendKeys("Universidad Politécnia de Valencia");
        System.out.println(element.getText());

        element.submit();
    }
}
