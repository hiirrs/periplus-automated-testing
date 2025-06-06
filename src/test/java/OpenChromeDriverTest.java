import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class OpenChromeDriverTest {

    @Test
    public void startChromeDriver() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\IdeaProjects\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(5000);
        driver.quit();
    }
}
