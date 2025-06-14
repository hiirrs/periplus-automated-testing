package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void login(String email, String password) {
        try {
            WebElement loginIcon = driver.findElement(By.cssSelector("a[href*='account/Your-Account']"));
            if (loginIcon.isDisplayed()) {
                loginIcon.click();
            } else {
                throw new ElementNotInteractableException("Hidden in desktop");
            }
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sign"))).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='email']"))).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("input#button-login")).click();

        wait.until(ExpectedConditions.urlContains("account"));
    }
}
