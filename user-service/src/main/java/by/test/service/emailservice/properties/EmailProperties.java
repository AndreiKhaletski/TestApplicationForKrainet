package by.test.service.emailservice.properties;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailProperties {

    private final Properties props = new Properties();
    private final String username;
    private final String password;

    public EmailProperties(EmailConfiguration emailConfigurationl) {
        this.username = emailConfigurationl.getRecipient();
        this.password = emailConfigurationl.getPassword();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", "smtp.mail.ru");
        props.put("mail.debug", "true");
    }

    public Properties getProps() {
        return props;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
