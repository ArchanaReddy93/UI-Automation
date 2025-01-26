package stepDefinition;

import Pages.AdminPage;
import Pages.HomePage;
import Utilities.CommonUtility;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import static stepDefinition.Hooks.driver;

public class stepDef {

    public static Logger Log= LogManager.getLogger(stepDef.class);
    CommonUtility commonUtility=new CommonUtility();
    HomePage homePage=new HomePage(driver);
    AdminPage adminPage=new AdminPage(driver);

    @Given("user able to access the site")
    public void user_able_to_access_the_site() throws IOException {
        commonUtility.getSiteURL();
    }

    @When("user gets title as {string}")
    public void userGetsTitleAs(String pageTitle) {
        homePage.checkPageTitle(pageTitle);
    }

    @Then("verify homepage is displayed")
    public void verifyHomepageIsDisplayed() {
        homePage.verifyHomePageIsDisplayed();
    }

    @And("click on the {string} link from homepage")
    public void clickOnTheLinkFromHomepage(String adminPanel) {
        homePage.clickOnTheAdminPanelLinkFromHomePage(adminPanel);
    }

    @Then("login to the account using username and password")
    public void loginToTheAccountUsingUsernameAndPassword() {
        homePage.loginInToAdminPanel();
    }

    @And("logout from the admin page")
    public void logoutFromTheAdminPage() {
        homePage.logoutFromAdminPage();
    }

    @When("user enters below details to create a room and then room created")
    public void userEntersBelowDetailsToCreateARoomAndThenRoomCreated(DataTable dataTable) {
        adminPage.createRoom(dataTable);
    }

    @And("scroll to the Rooms and check {string} is displayed")
    public void scrollToTheRoomsAndCheckIsDisplayed(String RoomName) {
        homePage.checkRoomIsAvailable(RoomName);
    }

    @Then("click on {string} button for the room {string}")
    public void clickOnButtonForTheRoom(String BookARoomButton, String RoomName) {
        homePage.clickBookThisRoomButton(BookARoomButton,RoomName);
    }

    @And("scroll to the form and update the below fields")
    public void scrollToTheFormAndUpdateTheBelowFields(DataTable dataTable) {
        homePage.UpdateContactForm(dataTable);
    }

    @Then("submit the form and validate the success message {string}")
    public void submitTheFormAndValidateTheSuccessMessage(String SuccessMessage) {
        homePage.submitFormAndVerifyMessage(SuccessMessage);
    }

    @When("user enter below details for booking")
    public void userEnterBelowDetailsForBooking(DataTable dataTable) {
        homePage.enterRoomBookingDetails(dataTable);

    }

    @Then("verify user is not able to login with invalid input")
    public void verifyUserIsNotAbleToLoginWithInvalidInput(DataTable dataTable) {
        homePage.loginWithInvalidInputData(dataTable);
    }

    @And("check all links present on homepage are accessible and responsive")
    public void checkAllLinksPresentOnHomepageAreAccessibleAndResponsive(DataTable dataTable) {
        homePage.verifyLinksAreVisibleAndResponsive(dataTable);
    }

    @Then("verify {string} message on page")
    public void verifyMessageOnPage(String Message) {
        homePage.verifyBookingSuccessfulMessage(Message);
    }
}
