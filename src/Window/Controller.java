package Window;

import Memento.MementoTextField;
import Parser.HTMLParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import СhangeHTML.Substitution;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
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
    @FXML
    public TextField testTextField; // [delete][delete][delete][delete][delete][delete]
    @FXML
    public Button buttonСheck;

    // Thread
    private ScheduledExecutorService timer;
    private final long TIMER_PERIOD = 500L;

    // Configuration
    Properties props = new Properties();

    // Parser
    private HTMLParser htmlParser;

    // Dynamic Objects
    private MementoTextField mementoTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        htmlParser = new HTMLParser();
        mementoTextField = new MementoTextField();

//        timer = Executors.newSingleThreadScheduledExecutor();
//        startTimer();
    }

    @FXML
    public void actionButtonUpdate(ActionEvent actionEvent) {
        vBox.getChildren().clear();
        mementoTextField.clear();

        Set<String> elemSet = htmlParser.parsing(htmlEditor.getHtmlText());

        elemSet.forEach((s)->{
            if (!mementoTextField.containsKey(s)){
                TextField textField = new TextField();
                textField.setPromptText(s);
                mementoTextField.put(s, textField);
                vBox.getChildren().add(textField);
            }
        });
    }

    @FXML
    public void actionButtonСheck(ActionEvent actionEvent) {
        System.out.println(htmlEditor.getHtmlText()); //                [Test][Test][Test][Test][Test]
        Substitution.replaceAll(htmlEditor, mementoTextField);
    }

    // NoFXML
    private void actionHtmlEditor() {
        Platform.runLater(() -> {

            // ------------------------------------------------------ delete \
            vBox.getChildren().clear();
            mementoTextField.clear();

            Set<String> elemSet = htmlParser.parsing(htmlEditor.getHtmlText());

            elemSet.forEach((s)->{
                if (!mementoTextField.containsKey(s)){
                    TextField textField = new TextField();
                    textField.setPromptText(s);
                    mementoTextField.put(s, textField);
                    vBox.getChildren().add(textField);
                }
            });
            // ------------------------------------------------------ delete /

            System.out.println(htmlEditor.getHtmlText());
        });
    }

    private void startTimer(){
        Runnable runnable = () -> {
            try {
                actionHtmlEditor();
            } catch (Exception ex) {
                updateStatus(ex.getMessage());
            }
        };

        timer.scheduleAtFixedRate(runnable, 3000L, TIMER_PERIOD, TimeUnit.MILLISECONDS);
    }

    private void stopTimer(){
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

    public void stopApplication(){
        stopTimer();
    }
}