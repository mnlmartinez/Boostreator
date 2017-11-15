package es.boostreator.util;

import org.apache.commons.exec.OS;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static Driver driver = null;

    public static Driver get() {
        if (driver == null) {
            driver = getChromeDriver();
        }
        return driver;
    }

    public static void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static Driver getChromeDriver() {
        String path = "src/main/resources/" + getSO() + "/chromedriver" + ("win".equals(getSO()) ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1366,768");
        return new Driver(new ChromeDriver(options));
    }

    private static String getSO() {
        if (OS.isFamilyMac()) {
            return "mac";
        } else if (OS.isFamilyUnix()) {
            return "nix";
        } else {
            return "win";
        }
    }

}
