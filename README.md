# Periplus Test Automation

Automated tests for Periplus bookstore website using Java + Selenium + TestNG.

## What it does

Tests the cart functionality:
1. Login to Periplus website
2. Search for "Atomic Habits" book
3. Add product to cart
4. Verify product is in cart

## Setup

1. **Install Java 8+** and **Chrome browser**
2. **Download ChromeDriver** and put it in `C:\Users\HP\IdeaProjects\drivers\chromedriver.exe`
3. **Create account** at https://www.periplus.com/
4. **Update credentials** in `PeriplusCartTest.java`:
   ```java
   final String EMAIL = "your-email@gmail.com";
   final String PASSWORD = "your-password";
   ```

## Run Tests

### In IDE:
Right-click on test file → Run as TestNG Test

### Command line:
```bash
mvn test
```

## Files

- `OpenChromeDriverTest.java` - Basic Chrome setup test
- `PeriplusCartTest.java` - Main cart functionality test

## Dependencies (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.15.0</version>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.8.0</version>
    </dependency>
</dependencies>
```

## Troubleshooting

- **ChromeDriver error**: Download correct version for your Chrome browser
- **Login fails**: Check email/password and account status
- **Element not found**: Website might have changed, update selectors
