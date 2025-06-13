import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PeriplusCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String expectedPrice;
    private String expectedProductName;

    final String BASE_URL = "https://www.periplus.com/";
    final String EMAIL = "zahiradina2303@gmail.com";
    final String PASSWORD = "p3r1p455";

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\IdeaProjects\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        driver.get(BASE_URL);
        login(EMAIL, PASSWORD);
        Thread.sleep(100);

        searchProduct("Atomic Habits");
        Thread.sleep(100);

        clickFirstProduct();
        Thread.sleep(100);

        addToCart();
        Thread.sleep(100);

        boolean added = verifyProductInCart();
        Assert.assertTrue(added, "Product was not added to cart successfully");

        verifyQuantityAndPrice("1", expectedProductName);
    }

    private void login(String email, String password) throws InterruptedException {
        WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sign")));
        loginLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='email']")));

        WebElement emailField = driver.findElement(By.cssSelector("input[name='email']"));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.cssSelector("input[name='password']"));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.cssSelector("input#button-login"));
        loginButton.click();

        try {
            wait.until(ExpectedConditions.urlContains("account"));
            System.out.println("Login successful");
        } catch (TimeoutException e) {
            System.out.println("Login timeout, check credentials or page");
        }

        Thread.sleep(100);
    }

    private void searchProduct(String productName) throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='filter_name']")));
        searchInput.sendKeys(productName + Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-area")));
        Thread.sleep(100);
    }

    private void clickFirstProduct() throws InterruptedException {
        waitForPreloader();

        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-price")));
        expectedPrice = priceElement.getText().trim();
        System.out.println("Product price based on search result: " + expectedPrice);

        WebElement productTitle = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("h3 > a")));
        productTitle.click();

        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2")));
        expectedProductName = nameElement.getText().trim();
        System.out.println("Clicked product name: " + expectedProductName);

        Thread.sleep(1000);
    }


    private void addToCart() throws InterruptedException {
        waitForPreloader();

        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-add-to-cart")));
        addToCartBtn.click();

        try {
            wait.until(ExpectedConditions.urlContains("/checkout/cart"));
        } catch (TimeoutException e) {
            System.out.println("Still on product page. Redirect might be delayed.");
        }

        Thread.sleep(100);
    }

    private boolean verifyProductInCart() {
        driver.get("https://www.periplus.com/checkout/cart");
        waitForPreloader();

        java.util.List<WebElement> productNames = driver.findElements(
                By.cssSelector("p.product-name.limit-lines"));

        for (WebElement element : productNames) {
            String cartName = element.getText().trim();
            if (cartName.equalsIgnoreCase(expectedProductName)) {
                System.out.println("Found same product in cart: " + cartName);
                return true;
            }
        }

        System.out.println("Product " + expectedProductName + " not found in cart.");
        return false;
    }


    private void verifyQuantityAndPrice(String expectedQty, String expectedProductName) {
        java.util.List<WebElement> productContainers = driver.findElements(By.cssSelector(".row-cart-product"));

        for (WebElement container : productContainers) {
            String name = container.findElement(By.cssSelector("p.product-name.limit-lines")).getText().trim();

            if (name.equalsIgnoreCase(expectedProductName)) {
                //quantity checking
                WebElement qtyInput = container.findElement(By.cssSelector("input.input-number.text-center"));
                String actualQty = qtyInput.getAttribute("value");
                Assert.assertEquals(actualQty, expectedQty, "Quantity mismatch");

                //price checking
                WebElement priceText = container.findElement(By.xpath(".//div[contains(text(), 'Rp')]"));
                String actualPrice = priceText.getText().trim();
                Assert.assertTrue(actualPrice.contains(expectedPrice),
                        "Price mismatch. Expected: " + expectedPrice + ", but found: " + actualPrice);

                return;
            }
        }

        Assert.fail("Product " + expectedProductName + " not found in cart for verification.");
    }

    private void waitForPreloader() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.preloader")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
