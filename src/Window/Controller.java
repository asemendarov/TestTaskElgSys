package Window;

import Email.JavaMailService;
import FileHelper.FileHelper;
import Memento.MementoTextField;
import Parser.HTMLParser;
import Tool.Configuration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import HTML.Substitution;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {

    @FXML
    public HTMLEditor htmlEditor;
    @FXML
    public Label status;
    @FXML
    public VBox vBox;
    @FXML
    public Button buttonUpdate;
    @FXML //--------------------------------\
    public TextField textFieldFrom;
    @FXML
    public TextField textFieldTo;
    @FXML
    public TextField login;
    @FXML
    public PasswordField password; //-------/

    // Thread
    private ScheduledExecutorService timer;
    private final long TIMER_PERIOD = 500L;
    // Parser
    private HTMLParser htmlParser;
    // Dynamic Objects
    private MementoTextField mementoTextField;
    // File Helper
    private FileHelper fileHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        htmlParser = new HTMLParser();
        mementoTextField = new MementoTextField();
        fileHelper = new FileHelper("message.html");

        login.setText(Configuration.getProperties().getProperty("mail.smtp.username"));
        password.setText(Configuration.getProperties().getProperty("mail.smtp.password"));

        try {
            htmlEditor.setHtmlText(fileHelper.read());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        updateData();

//        timer = Executors.newSingleThreadScheduledExecutor();
//        startTimer();
    }

    @FXML
    public void actionButtonUpdate(ActionEvent actionEvent) {
        updateData();
    }

    @FXML
    public void actionButtonSend(ActionEvent actionEvent) {
        if (mementoTextField.size() == 0)
            updateData();

        if (textFieldFrom.getLength() == 0 || textFieldTo.getLength() == 0
                || login.getLength() == 0 || password.getLength() == 0) {

            new Alert(Alert.AlertType.ERROR, "Пожалуйста, обратите внимание на то, что все поля (to, from, login, password) должны быть заполнены").showAndWait();
            return;
        }

        for (TextField textField : mementoTextField.getMap().values()) {
            if (textField.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, String.format("Пожалуйста, заполните поле [%s]", textField.getPromptText())).showAndWait();
                return;
            }
        }

        Substitution.replaceAll(htmlEditor, mementoTextField);

        updateData();

        assert mementoTextField.size() > 0 : "mementoTextField.size() > 0";


        try { //Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
            JavaMailService.send(
                    textFieldFrom.getText(),
                    Arrays.asList(textFieldTo.getText()),
                    "Message",
                    htmlEditor.getHtmlText()
            );
        } catch (MessagingException | IOException e) {
            status.setText("Сообщение не отправленоь, возникла ошибка");
            new Alert(Alert.AlertType.ERROR, String.format("Сообщение не отправлено, возникла ошибка: %s", e.getMessage())).showAndWait();
        }

        status.setText("Сообщение отправлино успешно");
    }

    private void updateData() {
        vBox.getChildren().clear();
        mementoTextField.clear();

        Set<String> elemSet = htmlParser.parsing(htmlEditor.getHtmlText());

        elemSet.forEach((s) -> {
            if (!mementoTextField.containsKey(s)) {
                TextField textField = new TextField();
                textField.setPromptText(s);
                mementoTextField.put(s, textField);
                vBox.getChildren().add(textField);
            }
        });
    }

    // NoFXML
    private void actionHtmlEditor() {
        Platform.runLater(this::updateData);
    }

    private void startTimer() {
        Runnable runnable = () -> {
            try {
                actionHtmlEditor();
            } catch (Exception ex) {
                updateStatus(ex.getMessage());
            }
        };

        timer.scheduleAtFixedRate(runnable, 3000L, TIMER_PERIOD, TimeUnit.MILLISECONDS);
    }

    private void stopTimer() {
        if (timer != null && !timer.isShutdown()) {
            timer.shutdown();

            try {
                timer.awaitTermination(TIMER_PERIOD, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                status.setText(e.getMessage());
            }
        }
    }

    private void updateStatus(String text) {
        Platform.runLater(() -> {
            status.setText(text);
        });
    }

    public void stopApplication() {
        stopTimer();
    }

    @FXML
    public void actionOpen(ActionEvent actionEvent) {
        try {
            htmlEditor.setHtmlText(fileHelper.read());
            updateData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void actionSave(ActionEvent actionEvent) {
        try {
            fileHelper.write(htmlEditor.getHtmlText());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void actionQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void actionAbout(ActionEvent actionEvent) {
        try {
            java.awt.Desktop.getDesktop().browse(new URI("https://github.com/oNoComments/TestTaskElgSys"));
        } catch (URISyntaxException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}