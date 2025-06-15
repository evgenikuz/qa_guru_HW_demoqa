import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests {
    @BeforeAll()
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

    }

    @Test
    void fillFormTest() {
        open("/text-box");
        $("#userName").setValue("Alex");
        $("#userEmail").setValue("alex@ya.ru");
        $("#currentAddress").setValue("Moscow");
        $("#permanentAddress").setValue("Kazan");
        $("#submit").click();

        $("#output #name").shouldHave(text("Alex"));
        $("#output #email").shouldHave(text("alex@ya.ru"));
        $("#output #currentAddress").shouldHave(text("Moscow"));
        $("#output #permanentAddress").shouldHave(text("Kazan"));

    }
}
