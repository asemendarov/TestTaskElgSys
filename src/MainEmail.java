import Email.JavaMailService;
import Tool.Configuration;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Модуля для тестирования отправки SMTP сообщения
 */
public class MainEmail {
    public static void main(String[] args) {
        String from = "helloworld25231@gmail.com";
        String to = "helloworld25231@gmail.com";
        String subject = "Hello World";
        String text = "Hello World";

        try {
            JavaMailService.send(from, Arrays.asList(to), subject, text);
        } catch (MessagingException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
