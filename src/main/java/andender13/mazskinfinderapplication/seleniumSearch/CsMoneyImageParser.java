package andender13.mazskinfinderapplication.seleniumSearch;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static andender13.mazskinfinderapplication.seleniumSearch.TradeItGGSearch.getWebDriver;

@Component
public class CsMoneyImageParser {
    public List<String> startImageParse(String url) {
        System.out.println("Start test");
        String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = getWebDriver();
        driver.manage().window().maximize();

        driver.get(url);

        while (true) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, window.innerHeight);");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> elements = driver.findElements(By.cssSelector(".kxmatkcipwonxvwweiqqdoumxg"));
            boolean allImagesLoaded = true;

            for (WebElement element : elements) {
                WebElement imgElement = element.findElement(By.cssSelector(".lnzmlhggcutlgtmffsmpefalne"));
                String imgSrc = imgElement.getAttribute("src");
                if (imgSrc.startsWith("data:image/gif;base64,")) {
                    allImagesLoaded = false;
                    break;
                }
            }

            if (allImagesLoaded) {
                break;
            }
        }
        List<String> imgList = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.cssSelector(".kxmatkcipwonxvwweiqqdoumxg"));
        for (WebElement element : elements) {
            WebElement el = element.findElement(By.cssSelector(".lnzmlhggcutlgtmffsmpefalne"));
            imgList.add(el.getAttribute("src"));
        }

        driver.quit();
        return imgList;
    }
}
