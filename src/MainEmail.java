import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class MainEmail {
    public static void main(String[] args) throws IOException, MessagingException {
//        final Properties properties = new Properties();
//        properties.load(new FileInputStream(new File("mail.properties")));
//
//        Session mailSession = Session.getDefaultInstance(properties);
//        MimeMessage message = new MimeMessage(mailSession);
//
//        message.setFrom(new InternetAddress("helloworld25231")); // от кого
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress("helloworld2314@yandex.ru")); // кому отпровляем
//        message.setSubject("hello"); // Тема
//        message.setText("text"); // Текст
//
//        Transport transport = mailSession.getTransport();
//        transport.connect("helloworld25231@gmail.com", "@helloworld25231!");
//        transport.sendMessage(message, message.getAllRecipients());
//        transport.close();

        String from = "helloworld25231@gmail.com";
        String to = "helloworld25231@gmail.com";
        String subject = "Your PDF";
        String text = "Here there is your <b>PDF</b> file!";

        JavaMailService.send(from, Collections.singleton(to), subject, text);
    }
}
