package ru.netology.web;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Keys;

public class DeliveryTest {
  LocalDate date = LocalDate.now();
  LocalDate correctDate = date.plusDays(3);
  LocalDate incorrectDate = date.plusDays(2);
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  @BeforeEach
  public void setUp() {
    open("http://localhost:9999/");
  }

  @Test
  void testCorrectFilling() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(15))
            .should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']").should(visible)
            .should(Condition.text("Встреча успешно забронирована на " + formatter.format(correctDate)));
  }

  @Test
  void testInCorrectFillingCity() {
    $x("//input[@placeholder='Город']").setValue("Чагода");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  @Test
  void testInCorrectFillingCityLatin() {
    $x("//input[@placeholder='Город']").setValue("Vologda");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Доставка в выбранный город недоступна')]")
            .shouldBe(Condition.text("Доставка в выбранный город недоступна"));
  }

  @Test
  void testInCorrectFillingCityEmpty() {
    $x("//input[@placeholder='Город']").setValue("");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void testInCorrectFillingDate() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(incorrectDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Заказ на выбранную дату невозможен')]")
            .shouldBe(Condition.text("Заказ на выбранную дату невозможен"));
  }

  @Test
  void testInCorrectFillingDateEmpty() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Неверно введена дата')]")
            .shouldBe(Condition.text("Неверно введена дата"));
  }

  @Test
  void testCorrectFillingNameWithHyphen() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров-Иванов Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//div[@class='notification__title']").should(visible, Duration.ofSeconds(15))
            .should(Condition.text("Успешно"));
    $x("//div[@class='notification__content']").should(visible)
            .should(Condition.text("Встреча успешно забронирована на " + formatter.format(correctDate)));
  }

  @Test
  void testInCorrectFillingNameLatin() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Petrov Fedor Alekseevich");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  void testInCorrectFillingNameEmpty() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void testInCorrectFillingNameUnderline() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров_Федор_Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  public void testInCorrectFillingNameLettersWithUmlaut() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Фёдор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
            .shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
  }

  @Test
  void testInCorrectFillingPhoneEmpty() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Поле обязательно для заполнения')]")
            .shouldBe(Condition.text("Поле обязательно для заполнения"));
  }

  @Test
  void testInCorrectFillingPhoneWithoutPlus() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("81234567890");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]")
            .shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
  }

  @Test
  void testInCorrectFillingPhoneMoreDigits() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+712345678900");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]")
            .shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
  }

  @Test
  void testInCorrectFillingPhoneFewerDigits() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+712345678");
    $x("//*[@data-test-id='agreement']").click();
    $x("//*[@class='button__text']").click();
    $x("//span[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]")
            .shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
  }

  @Test
  void testCheckboxEmptyChecked() {
    $x("//input[@placeholder='Город']").setValue("Вологда");
    $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
    $x("//input[@placeholder='Дата встречи']").sendKeys(formatter.format(correctDate));
    $x("//input[@name='name']").setValue("Петров Федор Алексеевич");
    $x("//input[@name='phone']").setValue("+71234567890");
    $x("//*[@class='button__text']").click();
    $x("//*[@role='presentation']")
            .shouldBe(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
  }
}
