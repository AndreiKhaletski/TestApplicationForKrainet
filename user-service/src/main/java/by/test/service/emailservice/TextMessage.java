package by.test.service.emailservice;

public class TextMessage {

    //Верификация
    public String WELCOME_TITLE = "Подтверждение почтового адреса в сервисе.";
    public String WELCOME_TEXT = "Дорогой друг! Добро пожаловать!" +
            " Введи код верификации для подтверждения и активации аккаунта! Code: ";

    //Уведомление о регистрации пользователей администратору(-ам) по адрес электронной почты
    public String REGISTRATION_NOTIFICATION_TITLE = "Регистрация пользователя";
    public String REGISTRATION_NOTIFICATION_TEXT = "На сервисе зарегистрировался пользователь: ";
}
