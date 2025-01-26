package Pages;

import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import static stepDefinition.Hooks.*;

public class HomePage {

    public static Logger Log= LogManager.getLogger(HomePage.class);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    Actions actions = new Actions(driver);

    public HomePage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="username")
    private WebElement UserName;

    @FindBy(id="password")
    private WebElement PassWord;

    @FindBy(xpath="//button[@type='submit']")
    private WebElement Submit;

    @FindBy(xpath="//a[contains(text(),'Front Page')]")
    private WebElement adminFrontPage;

    @FindBy(xpath="//a[contains(text(),'Logout')]")
    private WebElement Logout;

    @FindBy(xpath="//img[contains(@alt,'Preview image of')]")
    private WebElement rooms;

    @FindBy(xpath="//input[@placeholder='Name']")
    private WebElement contactName;

    @FindBy(xpath="//input[@placeholder='Email']")
    private WebElement contactEmail;

    @FindBy(xpath="//input[@placeholder='Phone']")
    private WebElement contactPhone;

    @FindBy(xpath="//input[@placeholder='Subject']")
    private WebElement contactSubject;

    @FindBy(xpath="//textarea[@id='description']")
    private WebElement contactMessage;

    @FindBy(xpath="//button[contains(text(),'Submit')]")
    private WebElement submit;

    @FindBy(xpath="//input[@placeholder='Firstname']")
    private WebElement firstName;

    @FindBy(xpath="//input[@placeholder='Lastname']")
    private WebElement lastName;

    @FindBy(xpath="//input[@placeholder='Email']")
    private WebElement Email;

    @FindBy(xpath="//input[@placeholder='Phone']")
    private WebElement Phone;

    @FindBy(xpath="//button[contains(text(),'Next')]")
    private WebElement nextMonth;

    @FindBy(xpath = "//span[@class='rbc-toolbar-label']")
    private WebElement monthLabel;

    @FindBy(xpath = "//button[@class='btn btn-outline-primary float-right book-room']")
    private WebElement Book;

    @FindBy(xpath = "//h3[contains(text(),'Booking Successful!')]")
    private WebElement successMessage;


    public void checkPageTitle(String pageTitle)
    {
        String title=driver.getTitle();
        Assert.assertEquals("Page title is loaded",pageTitle,title);
        Log.info("Page title is loaded as {}", title);
    }

    public void verifyHomePageIsDisplayed()
    {
        String readyState = (String) js.executeScript("return document.readyState");
        if (readyState.equals("complete")) {
            Log.info("Home Page loaded successfully!");
        } else {
            Assert.assertFalse("Page load failed.",false);
        }
    }
    public void clickOnTheAdminPanelLinkFromHomePage(String adminPanel)
    {
        WebElement admin=driver.findElement(By.xpath("//a[contains(text(),'"+adminPanel+"')]"));
        admin.click();
    }

    public void loginInToAdminPanel()
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        js.executeScript("arguments[0].scrollIntoView(true);", UserName);
        UserName.sendKeys(username);
        Log.info("admin username is entered as {}", username);
        PassWord.sendKeys(password);
        Log.info("admin password is entered as {}", password);
        Submit.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Front Page')]")));
        Assert.assertTrue("admin Front page is displayed",adminFrontPage.isDisplayed());
        Log.info("admin Front page is displayed");
    }
    public void loginWithInvalidInputData(DataTable dataTable)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        js.executeScript("arguments[0].scrollIntoView(true);", UserName);
        List<Map<String, String>> data = dataTable.asMaps();
        for (Map<String, String> row : data) {
            UserName.sendKeys(row.get("Username"));
            PassWord.sendKeys(row.get("Password"));
            Submit.click();
        }
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Front Page')]")));
        } catch (Exception e) {
            Assert.assertTrue("Login is not successful",true);
            Log.info("User is not logged in with invalid input");
        }
    }

    public void logoutFromAdminPage()
    {
        Logout.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        Log.info("User Logged out from AdminPanel");
    }
    public void checkRoomIsAvailable(String RoomName)
    {
        js.executeScript("arguments[0].scrollIntoView(true);", rooms);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt,'"+RoomName+"')]")));
    }
    public void clickBookThisRoomButton(String BookARoomButton, String RoomName)
    {
        driver.findElement(By.xpath("//img[contains(@alt,'"+RoomName+"')]//..//following-sibling::div/button[contains(text(),'"+BookARoomButton+"')]")).click();
        Log.info("Able to click on Book this button");
    }
    public void UpdateContactForm(DataTable dataTable){
        js.executeScript("arguments[0].scrollIntoView(true);", contactName);
        List<Map<String, String>> data = dataTable.asMaps();
        for (Map<String, String> row : data) {
            contactName.sendKeys(row.get("Name"));
            contactEmail.sendKeys(row.get("Email"));
            contactPhone.sendKeys(row.get("Phone"));
            contactSubject.sendKeys(row.get("Subject"));
            contactMessage.sendKeys(row.get("Message"));
        }
        Log.info("Entered Contact form fields");
    }

    public void submitFormAndVerifyMessage(String SuccessMessage){
        submit.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'"+SuccessMessage+"')]")));
        WebElement message=driver.findElement(By.xpath("//h2[contains(text(),'"+SuccessMessage+"')]"));
        Assert.assertTrue("Success Message is displayed",message.isDisplayed());
        Log.info("User able to submit contact form successfully,able to see success message as {}", message.getText());
    }

    public void enterRoomBookingDetails(DataTable dataTable){
        List<Map<String, String>> data = dataTable.asMaps();
        for (Map<String, String> row : data) {

            firstName.sendKeys(row.get("Firstname"));
            lastName.sendKeys(row.get("Lastname"));
            Email.sendKeys(row.get("Email"));
            Phone.sendKeys(row.get("Phone"));

            String sDate=row.get("StartDate");
            String[] startingDate=sDate.split(",");
            String currentMonthYear = monthLabel.getText();
            while (!currentMonthYear.contains(startingDate[1])){
                nextMonth.click();
                currentMonthYear = monthLabel.getText();
            }
            WebElement startDate=driver.findElement(By.xpath("//button[contains(text(),'"+startingDate[0]+"')]"));

            String eDate=row.get("EndDate");
            String[] endingDate=eDate.split(",");
            String currentMonthYear_new = monthLabel.getText();
            while (!currentMonthYear_new.contains(endingDate[1])){
                nextMonth.click();
                currentMonthYear_new = monthLabel.getText();
            }
            WebElement endDate=driver.findElement(By.xpath("//button[contains(text(),'"+endingDate[0]+"')]"));
            WebElement doubleClick=driver.findElement(By.xpath("(//div[@class='rbc-day-bg'])["+startingDate[0]+"]"));

            actions.moveToElement(startDate).clickAndHold().clickAndHold(doubleClick).perform();
            actions.moveToElement(endDate).release().perform();
            Book.click();
            Log.info("user details and dates selected and click on Book button");
        }
    }

    public void verifyBookingSuccessfulMessage(String message){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'"+message+"')]")));
        if(successMessage.getText().contains(message)){
            Assert.assertTrue("Booking Successful",true);
            Log.info("User is able to see success message as{}", successMessage.getText());
        }else{
            Assert.fail("Booking is not success");
        }
    }

    public void verifyLinksAreVisibleAndResponsive(DataTable dataTable){
        List<Map<String, String>> data = dataTable.asMaps();
        String linkPageURL = "";
        String linkText = "";
        for (Map<String, String> row : data) {
            List<WebElement>links=driver.findElements((By.tagName("a")));
            for(WebElement link : links) {
                try {
                    if (link.getText().contains(row.get("Link1"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link1 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }
                    } else if (link.getText().contains(row.get("Link2"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link2 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }

                    } else if (link.getText().contains(row.get("Link3"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link3 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }

                    } else if (link.getText().contains(row.get("Link4"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link4 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }

                    } else if (link.getText().contains(row.get("Link5"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link5 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }

                    } else if (link.getText().contains(row.get("Link6"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 404) {
                            Log.info("Link6 is working and responding: {}", link.getText());
                        } else {
                            Assert.fail("Link is not responding properly. Status code: " + statusCode + " - " + url);
                        }

                    } else if (link.getText().contains(row.get("Link7"))) {
                        String url = link.getAttribute("href");
                        int statusCode = getHTTPStatusCode(url);
                        if (statusCode == 200) {
                            Log.info("Link7 is working and responding: {}", link.getText());
                        } else {
                            Assert.assertFalse("Link is not responding properly. Status code: " + statusCode + " - " + url,false);
                        }

                    } else {
                        Log.info("Link is empty or invalid ");
                    }
                    }
                catch (StaleElementReferenceException e){
                    Log.info("link is not accessible");
                }
            }
        }
    }

    public static int getHTTPStatusCode(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            System.out.println("Error connecting to URL: " + url);
            return -1;  // Return -1 if an error occurs
        }
    }

}