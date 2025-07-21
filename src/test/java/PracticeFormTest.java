import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTest {
    @BeforeAll()
    static void onSetUpConfigurations() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

    }

    @Test
    void fillPracticeFormTest() {
        open("/automation-practice-form");
        // убирает рекламу
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('#fixedban').remove();");
        //заполняем форму
        $("#firstName").setValue("Alex");
        $("#lastName").setValue("Potapov");
        $("#userEmail").setValue("alex@ya.ru");
        $(byText("Other")).click();
        $("#userNumber").setValue("8800345645");
        // календарь
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-container .react-datepicker__year-select").selectOption("1912");
        $(".react-datepicker__month-container .react-datepicker__month-select").selectOption("September");
        $(".react-datepicker__month-container .react-datepicker__day--007").click();
        // предметы
        $("#subjectsInput").setValue("m");
        $(byText("Maths")).click();
        $("#subjectsInput").setValue("h");
        $(byText("Hindi")).click();
        // хобби
        $(byText("Reading")).click();
        $(byText("Music")).click();

        $("#uploadPicture").uploadFromClasspath("Ob1aUY2U.jpeg");
        $("#currentAddress").setValue("123409, Russia, Moscow");
        $("#state .css-1wy0on6").click();
        $(byText("Rajasthan")).click();
        $("#city .css-1wy0on6").click();
        $(byText("Jaipur")).click();


        $("#submit").click();

        $(".modal-content #example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        // проверка таблицы
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


        $("#closeLargeModal").click();
        $("h1").shouldHave(text("Practice Form"));


    }
}
