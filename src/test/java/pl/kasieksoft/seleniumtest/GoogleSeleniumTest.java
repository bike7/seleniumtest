package pl.kasieksoft.seleniumtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing Google and Youtube")
class GoogleSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void beforeEach() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
    }

    @DisplayName("Driver loads correctly and opens Google main page")
    @Test
    void driverLoadsGooglePage() {
        driver.get("http://google.com");
    }

    @DisplayName("Google search bar is visible after loading the page")
    @Test
    void searchBarVisible() {
        driver.get("http://google.com");
        driver.findElement(By.name("q"));
    }

    @DisplayName("Search results appear after typing in search query and clicking on search button")
    @Test
    @Disabled("Fix the non visible button")
    void searchResultsAppearAfterClickingSearch() {
        String searchQuery = "Selenium HQ";

        driver.get("http://google.com");
        WebElement queryInput = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));
        WebElement main = driver.findElement(By.id("main"));
        queryInput.sendKeys(searchQuery);
        main.click();
        searchButton.click();

        assertTrue(driver.getTitle().contains(searchQuery));
    }

    @DisplayName("Search results appear after typing in search query and pressing enter")
    @Test
    void searchResultsAppearAfterPressingEnter() {
        String searchQuery = "Selenium HQ";

        driver.get("http://google.com");
        WebElement queryInput = driver.findElement(By.name("q"));
        queryInput.sendKeys(searchQuery);
        queryInput.sendKeys(Keys.RETURN);

        assertTrue(driver.getTitle().contains(searchQuery));
    }

    @DisplayName("Youtube finds exact video specified in search query and plays it")
    @Test
    void youtubeFindsVideo() {
        String searchQuery = "Zalipie - najpiękniejsza polska wieś";

        driver.get("http://youtube.com");
        WebElement queryInput = driver.findElement(By.id("search"));
        queryInput.sendKeys(searchQuery);
        queryInput.sendKeys(Keys.RETURN);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.titleContains(searchQuery));

        assertTrue(driver.getCurrentUrl().contains("Zalipie"));

        WebElement firstLink = driver.findElement(By.xpath("(//div[@id='content']//a[contains(.,'Zalipie')]) [1]"));
        firstLink.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("player")));
        assertTrue(driver.getCurrentUrl().contains("s7qDbhEu28Y"));
    }


    @AfterEach
    void afterEach() {
        driver.quit();
    }
}