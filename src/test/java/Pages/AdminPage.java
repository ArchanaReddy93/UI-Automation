package Pages;

import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static stepDefinition.Hooks.driver;

public class AdminPage {
    public static Logger Log= LogManager.getLogger(AdminPage.class);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public AdminPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath="//input[@id='roomName']")
    private WebElement roomName;

    @FindBy(xpath="//select[@id='type']")
    private WebElement roomType;

    @FindBy(xpath="//select[@id='accessible']")
    private WebElement accessible;

    @FindBy(xpath="//input[@id='roomPrice']")
    private WebElement roomPrice;

    @FindBy(xpath="//button[contains(text(),'Create')]")
    private WebElement createButton;

    public void createRoom(DataTable dataTable)
    {
        List<Map<String, String>> data = dataTable.asMaps();
        for (Map<String, String> row : data) {
            roomName.sendKeys(row.get("Room"));
            Select select = new Select(roomType);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if(option.getText().equals(row.get("Type"))){
                    option.click();
                    Log.info("Room Type is selected as {}", option.getText());
                }
            }
            Select select1 = new Select(accessible);
            List<WebElement> accessibleOptions = select1.getOptions();
            for (WebElement option : accessibleOptions) {
                if(option.getText().equals(row.get("Accessible"))){
                    option.click();
                    Log.info("Accessible is selected as {}", option.getText());
                }
            }
            roomPrice.sendKeys(row.get("Price"));
            String str=row.get("RoomDetails");
            String[] features=str.split(",");
            for (String feature : features) {
                WebElement roomDetails = driver.findElement(By.xpath("//input[@value='" + feature + "']"));
                roomDetails.click();
            }
            createButton.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'"+row.get("Room")+"')]")));
            Log.info("Room created successfully");
        }
    }
}
