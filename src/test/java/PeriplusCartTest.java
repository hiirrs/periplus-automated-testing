import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;
import java.time.Duration;

public class PeriplusCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String BASE_URL;
    private String expectedProductName;
    private String expectedPrice;

    @BeforeMethod
    public void setup() {
        BASE_URL = ConfigReader.get(("baseUrl"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(390, 844));
        driver.get(BASE_URL);
    }

    @Test
    public void testAddToCart() {
        String email = ConfigReader.get("email");
        String password = ConfigReader.get("password");
        String product = ConfigReader.get("productName");
        String expectedQty = ConfigReader.get("expectedQty");

        new LoginPage(driver, wait).login(email, password);

        ProductPage page = new ProductPage(driver, wait);
        page.searchProduct(product);
        page.clickFirstProduct();
        page.addToCart();

        expectedProductName = page.expectedProductName;
        expectedPrice = page.expectedPrice;

        Assert.assertTrue(verifyProductInCart());
        verifyQuantityAndPrice(expectedQty, expectedProductName);
    }

    private boolean verifyProductInCart() {
        driver.get(BASE_URL + "checkout/cart");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.preloader")));
        return driver.findElements(By.cssSelector("p.product-name.limit-lines"))
                .stream().anyMatch(el -> el.getText().trim().equalsIgnoreCase(expectedProductName));
    }

    private void verifyQuantityAndPrice(String expectedQty, String expectedProductName) {
        for (WebElement container : driver.findElements(By.cssSelector(".row-cart-product"))) {
            String name = container.findElement(By.cssSelector("p.product-name.limit-lines")).getText().trim();
            if (name.equalsIgnoreCase(expectedProductName)) {
                String qty = container.findElement(By.cssSelector("input.input-number.text-center")).getAttribute("value");
                Assert.assertEquals(qty, expectedQty, "Quantity mismatch");

                String actualPrice = container.findElement(By.xpath(".//div[contains(text(), 'Rp')]")).getText().trim();
                Assert.assertTrue(actualPrice.contains(expectedPrice), "Price mismatch");
                return;
            }
        }
        Assert.fail("Product not found in cart.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
