# Periplus Test Automation
Automated tests for Periplus bookstore website using Java + Selenium + TestNG.

## What it does
Tests the cart functionality:
1. Login to Periplus website
2. Search for "Atomic Habits" book
3. Add product to cart
4. Verify product is in cart

## Prerequisites
- **Java 22** (as specified in pom.xml)
- **Chrome browser** (latest version recommended)
- **Maven** (for dependency management and running tests)
- **Active Periplus account** at https://www.periplus.com/

## Setup
### 1. Java Installation
Ensure Java 22 is installed and configured:
```bash
java -version
```

### 2. Maven Installation
Install Maven and verify:
```bash
mvn -version
```

### 3. ChromeDriver Setup
This project uses WebDriverManager which automatically handles ChromeDriver setup. No manual driver download required!

### 4. Account Configuration
Create an account at https://www.periplus.com/ and update your credentials in src/test/resources/`config.properties`:
```java
email=your-email@gmail.com
password=your-password
productName=your-product-preference-name
expectedQty=1
```
This test currently uses expectedQty = 1 to validate the default cart behavior. Although the UI allows quantity adjustments via the plus (+) button, this scenario is not covered in the current test.

## Project Structure
The project follows the Page Object Model (POM) design pattern for better maintainability:

```
src/
├── main/java/
│   ├── pages/
│   │   ├── LoginPage.java      # Login page interactions
│   │   └── ProductPage.java    # Product search and cart operations
│   └── utils/
│       └── ConfigReader.java   # Configuration file reader utility
├── test/java/
│   └── PeriplusCartTest.java   # Main test class
└── test/resources/
    └── config.properties       # Test configuration
```

### Page Classes
- **LoginPage.java**: Handles login functionality with sign-in link clicking and credential input
- **ProductPage.java**: Manages product search, selection, and cart operations. Captures expected product name and price for verification
- **ConfigReader.java**: Utility class to read configuration properties from config.properties file

## Dependencies
The project uses the following key dependencies:
- **Selenium WebDriver** (4.33.0) - Web automation framework
- **TestNG** (7.11.0) - Testing framework
- **WebDriverManager** (5.7.0) - Automatic driver management
- **BouncyCastle** (1.78) - Cryptographic library for security
- **Jackson** - JSON processing (managed versions for compatibility)
- **SLF4J Simple** (2.0.12) - Logging

## Run Tests
### Option 1: IDE (Recommended)
1. Import project into your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Right-click on `PeriplusCartTest.java`
3. Select "Run as TestNG Test"

### Option 2: Maven Command Line
```bash
# Run test
mvn test

# Clean and test
mvn clean test
```

## Files
- `PeriplusCartTest.java` - Main test class with cart functionality test
- `LoginPage.java` - Page object for login operations
- `ProductPage.java` - Page object for product search and cart operations
- `ConfigReader.java` - Utility for reading configuration properties
- `config.properties` - Configuration file with test data and credentials

## Troubleshooting
- **ChromeDriver error**: Download correct version for your Chrome browser
- **Login fails**: Check email/password and account status
- **Element not found**: Website might have changed, update selectors
- **Config not found**: Ensure config.properties is in src/test/resources/ directory

## Configuration Notes
### Jackson Dependencies
The project uses dependency management to resolve Jackson version conflicts with Selenium. The managed versions are:
- jackson-databind: 2.13.4.2
- jackson-core: 2.15.0
- jackson-annotations: 2.15.3

### Security
BouncyCastle libraries are included for enhanced cryptographic support, which may be required for secure connections to the Periplus website.

### Logging
SLF4J Simple is configured for basic logging output during test execution.
