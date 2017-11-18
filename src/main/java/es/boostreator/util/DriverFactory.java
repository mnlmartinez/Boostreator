package es.boostreator.util;

import es.boostreator.BoostreatorApp;
import org.apache.commons.exec.OS;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;

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
        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1366,768");
        return new Driver(new ChromeDriver(options));
    }

    private static String getChromeDriverPath() {
        String filePath = "/driver/" + getSO() + "/chromedriver" + ("win".equals(getSO()) ? ".exe" : "");
        URL location = DriverFactory.class.getResource(filePath);

        String[] parts = location.getPath().split("file:");
        String path = parts.length == 2 ? parts[1] : parts[0];

        if (path.contains(".jar!")){
            return path.replace("Boostreator-1.0.jar!/", "");
        } else {
            System.out.println(path);
            return path;
        }
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
