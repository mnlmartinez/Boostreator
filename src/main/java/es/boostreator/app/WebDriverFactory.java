package es.boostreator.app;

import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class WebDriverFactory {

    static final int CHROME = 0;
    static final int FIREFOX = 1;
    static final int IE = 2;

    static WebDriver get(int type){
        WebDriver webDriver = null;

        switch (type) {
            case CHROME:
                webDriver = getChromeDriver();
                break;
        }

        return webDriver;
    }

    private static WebDriver getChromeDriver(){
        String path = "src/main/resources/" + getSO() + "/chromedriver" + ("win".equals(getSO()) ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static String getSO(){
        if(OS.isFamilyMac()) { return "mac"; }
        else if(OS.isFamilyUnix()) { return "nix"; }
        else { return "win"; }
    }

}
