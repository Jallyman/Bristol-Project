
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.concurrent.*;


public class Program extends Application {

    // Sets up all the different screens that will be used
    private Login loginScreen = new Login();
    private Menu menuScreen = new Menu();

    // rename connection
    private Connection connector = new Connection();

    // Sets the different available scenes in the program
    private Scene loginScene = new Scene(loginScreen.getGrid(), 550, 600);
    private Scene menuScene = new Scene(menuScreen.getGrid(), 550, 600);

    private Thread connection;

    @Override
    public void start(Stage primaryStage) {

        

        // Action events for all the buttons in the program
        // Heard there were ways to combine them and make it more efficient, 
        // but decided to spend more time on other areas
        loginScreen.getButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (e.getSource() == loginScreen.getButton()) {
                    if (loginScreen.loginOccurred()) {
                        primaryStage.setScene(menuScene);
                    }
                }
            }
        });

        menuScreen.getLogoutButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.setScene(loginScene);
            }
        });

        menuScreen.getConnectionButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Runs a new task in a separate thread
                if(connection != null){
                    connection.interrupt();
                }

                Task<Void> task = new Task<Void>() {
                    @Override protected Void call() throws Exception {
                        System.out.println(connector.connection(menuScreen.connectionOccurred()));
                        return null;
                    }
                };

                connection = new Thread(task);
                connection.start();
                  
            }
        });

        // Change if login screen is needed
        primaryStage.setTitle("Survey Program");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

