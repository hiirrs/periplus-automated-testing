package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public String expectedPrice;
    public String expectedProductName;

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void searchProduct(String name) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='filter_name']")));
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

