package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WDListener;



public class ApplicationManager {

    //private WebDriver driver;
    private EventFiringWebDriver driver;

    public WebDriver getDriver(){
        return driver;
    }

    Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        //driver = new ChromeDriver();
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.manage().window().maximize();
        logger.info("Start testing");
        driver.register(new WDListener());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        if(driver != null)
            driver.quit();
    }
}
