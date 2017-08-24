
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.*;

import javafx.concurrent.*;

import com.mathworks.toolbox.javabuilder.*;
import myFunction.*;


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
                
                // Progress bar ?? 
                connection = new Thread(task);
                connection.start();
                  
            }
        });

        menuScreen.getThermalButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Object[] result;
                String uri = uri.getPath();
                // "C:\\Users\\jaspe\\Documents\\University Work\\Bristol\\COMSM3201 - MSc Project Computer Science\\Thermal Imaging\\MATLAB Program\\test2.jpg";
                MWCharArray param = new MWCharArray(uri);
                try {
                    Class1 myFunction = new Class1();
                    result = myFunction.myFunction(1, uri);
                    MWNumericArray number = (MWNumericArray)result[0];
                    System.out.println(number.getInt());
                } catch (MWException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Change if login screen is needed
        primaryStage.setTitle("Survey Program");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Thermal Image Finder");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

