package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Hooks {

    public static Logger Log= LogManager.getLogger(Hooks.class);
    public static WebDriver driver;
    public static String AppURL;
    public static String username;
    public static String password;
    static final String ZAP_ADDRESS = "localhost";
    static final int ZAP_PORT = 8080;
    private static final String ZAP_API_KEY = "1tvlbuc32qf9gr6f7h1mgpg9t2"; //insert your API key
    static ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

    @Before
    public void beforeScenario(Scenario scenario) throws IOException  {
        Log.info("Starting the scenario: ", scenario.getName());
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\LENOVO\\IdeaProjects\\AutomationTesting\\src\\main\\resources\\config.properties");
            prop.load(fis);
            String proxyServerUrl = ZAP_ADDRESS+":"+ZAP_PORT;
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyServerUrl);
            proxy.setSslProxy(proxyServerUrl);

            AppURL = prop.getProperty("appURL");
            username=prop.getProperty("username");
            password=prop.getProperty("password");
            String browser= prop.getProperty("browser");
            Log.info("App URL: {}", AppURL);
            if(browser.equals("chrome"))
            {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.setProxy(proxy);
                driver=new ChromeDriver(chromeOptions);
                api=new ClientApi(ZAP_ADDRESS,ZAP_PORT,ZAP_API_KEY);
            }else if (browser.equals("firefox"))
            {
                WebDriverManager.firefoxdriver().setup();
                driver=new FirefoxDriver();
            }else if(browser.equals("edge"))
            {
                WebDriverManager.edgedriver().setup();
                driver=new EdgeDriver();
            }else
            {
                Assert.assertFalse("browser not found",false);
            }
            Log.info("Opening the browser: {}", browser);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Log.info("Scenario Failed: Capturing response details...");
            TakesScreenshot ts=(TakesScreenshot) driver;
            byte[] src =ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src,"image/png", "screenshot");
        }
        if(api !=null){
            String title="Restful booker platform ZAP security report";
            String template="traditional-html";
            String description="This is Restful booker platform ZAP security test report";
            String reportFilename="Restful-booker-platform-ZAP-report.html";
            String targetFolder=System.getProperty("user.dir");
            try {
            ApiResponse response= api.reports.generate(title,template,null,description,null,null,null,
                        null,null,reportFilename,null,targetFolder,null);
                System.out.println("ZAP report generated at this location "+response.toString());

                } catch (ClientApiException e) {
                e.printStackTrace();
            }

            }
        driver.quit();
            }

}
