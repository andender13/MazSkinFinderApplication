package andender13.mazskinfinderapplication.seleniumSearch;

import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.enums.CSSLibrary;
import andender13.mazskinfinderapplication.enums.StatTrack;
import andender13.mazskinfinderapplication.telegram.TelegramNotificationBot;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class TradeItGGSearch {
    @Autowired
    TelegramNotificationBot telegramNotification;

    public void startSearch(Weapon weapon) {
        String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = getWebDriver();
        driver.manage().window().maximize();
        System.out.println("Starting search for " + weapon.getWeaponType().getStringType() + " " + weapon.getSkinName());
        try {
            String TradeItGGUrl = "https://tradeit.gg/ru/csgo/trade";
            driver.get(TradeItGGUrl);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> buttons = driver.findElements(By.cssSelector(CSSLibrary.MIDDLE_BUTTONS_WIDGETS_CSS));
            for (WebElement button : buttons) {
                if (button.getText().contains("StatTrak")) {
                    button.click();
                    List<WebElement> statTrackButtons = driver.findElements(By.cssSelector(CSSLibrary.MIDDLE_BUTTON_INPUT_CSS));
                    if (weapon.getStatTrack() == StatTrack.NO) {
                        statTrackButtons.getFirst().click();
                    } else {
                        statTrackButtons.get(1).click();
                    }

                }
                if (button.getText().contains("Флоат")) {
                    button.click();
                    WebElement leftFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CSSLibrary.LEFT_FLOAT_FIELD_INPUT_XPATH)));
                    leftFloatField.click();
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    leftFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    leftFloatField.sendKeys(String.valueOf(weapon.getWeaponFloat().getLeftFloatBorder()));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                    WebElement rightFloatField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CSSLibrary.RIGHT_FLOAT_FIELD_INPUT_XPATH)));
                    rightFloatField.click();
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
                    rightFloatField.sendKeys(org.openqa.selenium.Keys.DELETE);
                    rightFloatField.sendKeys(String.valueOf(weapon.getWeaponFloat().getRightFloatBorder()));
                    break;
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }


            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSSLibrary.BOT_INVENTORY_SEARCH_CSS)));
            searchField.click();

            searchField.sendKeys(weapon.getWeaponType().getStringType() + " " + weapon.getSkinName());
            searchField.sendKeys(org.openqa.selenium.Keys.RETURN);


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS)));


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }


            List<WebElement> widgets = driver.findElements(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS));

            for (WebElement widget : widgets) {
                try {
                    WebElement openField = widget.findElement(By.cssSelector(CSSLibrary.OPEN_BUTTON_CSS));
                    openField.click();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                    List<WebElement> foundedGuns = driver.findElements(By.cssSelector(CSSLibrary.GUN_INFO_WIDGET_CSS));
                    if (!foundedGuns.getFirst().getText().contains(weapon.getSkinName())) {
                        System.out.println("\nNothing found for:" + weapon.getWeaponType().getStringType() + " " + weapon.getSkinName());
                        return;
                    }
                    for (WebElement foundedGun : foundedGuns) {
                        String gun;
                        if (foundedGun.equals(foundedGuns.getFirst())) {
                            gun = "Founded " + weapon.getWeaponType().getStringType() + " " + foundedGun.getText();
                        } else {
                            gun = "Founded " + weapon.getWeaponType().getStringType() + " " + weapon.getSkinName() + "\n" + foundedGun.getText();
                        }
                        System.out.println(gun);
                        telegramNotification.sendMessage(gun + "\n" + TradeItGGUrl, "685337904");
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                    break;


                } catch (org.openqa.selenium.NoSuchElementException e) {
                    System.out.println("Element not found in widget.");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            driver.quit();
        }
    }

    private static WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }
}
