import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class PracticeFormRemoteTest {
    @BeforeAll()
    static void onSetUpConfigurations() {
        Configuration.browserSize = System.getProperty("resolution", "1980x1080");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version", "138");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
//        Configuration.remote = "https://user1:1234@" + System.getProperty("wdhost") + "/wd/hub";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        //видео
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void  addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    @Tag("demoqa")
    void fillPracticeFormTest() {
        step("open form", () -> {
            open("/automation-practice-form");
        });
        step("remove ads", () -> {
            executeJavaScript("$('footer').remove();");
            executeJavaScript("$('#fixedban').remove();");
        });

        step("fill name", () -> {
            $("#firstName").setValue("Alex");
            $("#lastName").setValue("Potapov");
        });
        step("fill contact info", () -> {
            $("#userEmail").setValue("alex@ya.ru");
            $(byText("Other")).click();
            $("#userNumber").setValue("8800345645");
        });
        step("fill date of birth", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-container .react-datepicker__year-select").selectOption("1912");
            $(".react-datepicker__month-container .react-datepicker__month-select").selectOption("September");
            $(".react-datepicker__month-container .react-datepicker__day--007").click();
        });
        step("fill subjects",() -> {
            $("#subjectsInput").setValue("m");
            $(byText("Maths")).click();
            $("#subjectsInput").setValue("h");
            $(byText("Hindi")).click();
        });
        step("fill hobbies",() -> {
            $(byText("Reading")).click();
            $(byText("Music")).click();
        });
        step("upload picture",() -> {
            $("#uploadPicture").uploadFromClasspath("Ob1aUY2U.jpeg");
        });
        step("fill address",() -> {
            $("#currentAddress").setValue("123409, Russia, Moscow");
            $("#state .css-1wy0on6").click();
            $(byText("Rajasthan")).click();
            $("#city .css-1wy0on6").click();
            $(byText("Jaipur")).click();
        });
        step("submit form",() -> {
            $("#submit").click();
        });
        step("check modal is opened",() -> {
            $(".modal-content #example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        });

        step("verify results in modal", () -> {
            $(".modal-body tbody tr:nth-child(1)").shouldHave(text("Student Name Alex Potapov"));
            $(".modal-body tbody tr:nth-child(2)").shouldHave(text("Student Email alex@ya.ru"));
            $(".modal-body tbody tr:nth-child(3)").shouldHave(text("Gender Other"));
            $(".modal-body tbody tr:nth-child(4)").shouldHave(text("Mobile 8800345645"));
            $(".modal-body tbody tr:nth-child(5)").shouldHave(text("Date of Birth 07 September,1912"));
            $(".modal-body tbody tr:nth-child(6)").shouldHave(text("Subjects Maths, Hindi"));
            $(".modal-body tbody tr:nth-child(7)").shouldHave(text("Hobbies Reading, Music"));
            $(".modal-body tbody tr:nth-child(8)").shouldHave(text("Picture Ob1aUY2U.jpeg"));
            $(".modal-body tbody tr:nth-child(9)").shouldHave(text("Address 123409, Russia, Moscow"));
            $(".modal-body tbody tr:nth-child(10)").shouldHave(text("State and City Rajasthan Jaipur"));
        });
        step("close modal", () -> {
            $("#closeLargeModal").click();
            $("h1").shouldHave(text("Practice Form"));
        });
        step("check modal is closed",() -> {
            sleep(1000);
            $("h1").shouldHave(text("Practice Form"));
        });

    }
}
