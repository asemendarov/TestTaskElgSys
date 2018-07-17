import Window.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Window/window.fxml"));

        BorderPane rootElement = loader.load();

        primaryStage.setScene(new Scene(rootElement){{
            getStylesheets().add(getClass().getResource("Window/application.css").toExternalForm());
        }});

        // WindowThread
        Controller controller = loader.getController();
        primaryStage.setOnCloseRequest((we -> {
            try {
                controller.stopApplication();
            } catch (Exception e) {
                controller.status.setText(e.getMessage());
            }
        }));

        primaryStage.show();
    }
}