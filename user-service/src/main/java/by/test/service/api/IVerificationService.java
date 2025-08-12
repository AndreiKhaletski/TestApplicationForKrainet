package by.test.service.api;

public interface IVerificationService {

    public void create(String email, String title, String text);

    String get(String email);

}
