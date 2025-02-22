package tests;

import dto.ContactDtoLombok;
import dto.UserDto;
import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TakeScreenShot;
import utils.TestNGListener;

import static utils.PropertiesReader.*;
import static utils.TakeScreenShot.*;

@Listeners(TestNGListener.class)

public class AddContactTests extends ApplicationManager {

    SoftAssert softAssert = new SoftAssert();
    //UserDto user = new UserDto("qa_mail@mail.com", "Qwerty123!");
    UserDto user = new UserDto(getProperty("login.properties", "email"),
                               getProperty("login.properties", "password"));
    AddPage addPage;

    @BeforeMethod(alwaysRun = true)
    public void login(){
        new HomePage(getDriver()).clickBtnLoginHeader();
        new LoginPage(getDriver()).typeLoginForm(user);
        new ContactsPage(getDriver()).clickBtnAdd();
        addPage = new AddPage(getDriver());
    }

    @Test(invocationCount = 1, groups = "smoke")
    public void addNewContactPositiveTest(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("Name123")
                .lastName("Last name123")
                .phone("1234567890")
                .email("lastname@mail.com")
                .address("address st.1")
                .description("description")
                .build();
        addPage.typeContactForm(contact);
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(new ContactsPage(getDriver())
                .validateLastElementContactList(contact));
    }

    @Test
    public void addNewContactNegativeTest_emptyName(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("")
                .lastName("Last name123")
                .phone("1234567890")
                .email("lastname@mail.com")
                .address("address st.1")
                .description("description")
                .build();
        addPage.typeContactForm(contact);
        Assert.assertFalse(addPage.validateUrlContacts());
    }

    @Test
    public void addNewContactNegativeTest_wrongPhone(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("name 123")
                .lastName("Last name123")
                .phone("1qwwwerer")
                .email("lastname@mail.com")
                .address("address st.1")
                .description("description")
                .build();
        addPage.typeContactForm(contact);

        String message = addPage.closeAlertAndReturnText();
        System.out.println(message);

        softAssert.assertTrue(message.contains("Phone number must contain only digits! And length min 10, max 15!"));

        System.out.println("code after assert");

        softAssert.assertFalse(addPage.validateUrlContacts());

        softAssert.assertAll();

    }

    @Test
    public void addNewContactNegativeTest_wrongEmail(){
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("name 123")
                .lastName("Last name123")
                .phone("1234567890")
                .email("lastname")
                .address("address st.1")
                .description("description")
                .build();
        addPage.typeContactForm(contact);
        String message = addPage.closeAlertAndReturnText();
        System.out.println(message);
        softAssert.assertTrue(message.contains("Email not valid"));
        System.out.println("code after assert");
        softAssert.assertFalse(addPage.validateUrlContacts());
        softAssert.assertAll();
    }
}
