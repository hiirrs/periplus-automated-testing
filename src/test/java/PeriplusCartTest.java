import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class PeriplusCartTest {
    WebDriver driver;
    WebDriverWait wait;
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
        Thread.sleep(1000);

        searchProduct("Atomic Habits");
        Thread.sleep(1000);

        clickFirstProduct();
        Thread.sleep(1000);

        addToCart();
        Thread.sleep(1000);

        boolean added = verifyProductInCart("Atomic Habits");
        Assert.assertTrue(added, "Product was not added to cart successfully");
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

        Thread.sleep(1000);
    }

    private void searchProduct(String productName) throws InterruptedException {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='filter_name']")));
        searchInput.sendKeys(productName + Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-area")));
        Thread.sleep(1000);
    }

    private void clickFirstProduct() throws InterruptedException {
        waitForPreloader();
        WebElement productTitle = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("h3 > a")));
        productTitle.click();
        Thread.sleep(1000);
    }

    private void addToCart() throws InterruptedException {
        waitForPreloader();

        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-add-to-cart")));
        addToCartBtn.click();

        // wait for cart to update (or optionally navigate)
        try {
            wait.until(ExpectedConditions.urlContains("/checkout/cart"));
        } catch (TimeoutException e) {
            System.out.println("Still on product page. Redirect might be delayed.");
        }

        Thread.sleep(1000);
    }

    private boolean verifyProductInCart(String productName) {
        driver.get("https://www.periplus.com/checkout/cart");
        waitForPreloader();

        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("p.product-name.limit-lines")));
        return productElement.getText().toLowerCase().contains(productName.toLowerCase());
    }

    private void waitForPreloader() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.preloader")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
