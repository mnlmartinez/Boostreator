package es.boostreator;

import es.boostreator.util.AppLogger;
import es.boostreator.util.DriverFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BoostreatorApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String resourcePath = "/view/SearchView.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(location);

        Scene scene = new Scene(loader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();

        // Threads - close events
        stage.setOnCloseRequest(we -> DriverFactory.close());
        Runtime.getRuntime().addShutdownHook(new Thread(DriverFactory::close));
    }


    public static void main(String[] args) {
        AppLogger.debugMode();
        launch(args);
    }
}
