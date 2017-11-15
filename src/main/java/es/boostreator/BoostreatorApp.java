package es.boostreator;

import es.boostreator.util.AppLogger;
import es.boostreator.view.SearchViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BoostreatorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String resourcePath = "/view/SearchView.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(location);

        Scene scene = new Scene(loader.load(), 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        AppLogger.debugMode();
        launch(args);
    }
}
