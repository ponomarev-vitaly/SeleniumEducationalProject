import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.stream.Collectors;

public class GitHubSearchTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void checkGitHubSearch(){


        driver.get("https://github.com/");

        WebElement searchInput = driver.findElement(By.cssSelector("[name='q']"));

        String searchPhrase = "selenium";

        searchInput.sendKeys(searchPhrase);
        searchInput.sendKeys(Keys.ENTER);

        List<String> actualItems = driver.findElements(By.cssSelector(".repo-list-item"))
                .stream()
                .map(element -> element.getText().toLowerCase())
                .collect(Collectors.toList());
        List<String> expectedItems = actualItems.stream()
                .filter(item -> item.contains(searchPhrase))
                .collect(Collectors.toList());

//        System.out.println(actualItems); // Print out actualItems and expectedItems into the console.
//        System.out.println("=============================>");
//        System.out.println(expectedItems);

        Assertions.assertEquals(expectedItems, actualItems);


    }

    @AfterAll
    public static void tearDownDriver(){
        driver.quit();
    }
}
