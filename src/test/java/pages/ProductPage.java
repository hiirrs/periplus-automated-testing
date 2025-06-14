package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public String expectedPrice;
    public String expectedProductName;

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void searchProduct(String name) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.preloader")));
        WebElement searchInput;
        if (driver.manage().window().getSize().getWidth() <= 768) {
            searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div.search-bar.mobilephone input[name='filter_name']")));
        } else {
            searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div.search-bar-top input[name='filter_name']")));
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchInput);
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        searchInput.sendKeys(name + Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-area")));
    }

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.preloader")));

        expectedPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-price"))).getText().trim();
        WebElement productTitle = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("h3 > a")));
        productTitle.click();

        expectedProductName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2"))).getText().trim();
    }

    public void addToCart() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.preloader")));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-add-to-cart"))).click();
        driver.get("https://www.periplus.com/checkout/cart");
    }
}

