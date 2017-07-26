import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.paint.*;

// Controls the main menu area of the program
public class Menu {

    private GridPane grid = new GridPane();
    private Button connectionBtn, logoutBtn;
    private Text menuText;
    private Text connectionText;

    public Menu(){
        setup();    
    }

    private void setup(){
        create();
        layout(grid);
        align(grid);
        adjustment(grid);
    }

    public GridPane getGrid(){
        return grid;
    }

    public Button getConnectionButton(){
        return connectionBtn;
    }

    public Button getLogoutButton(){
        return logoutBtn;
    }

    // public boolean connectionOccurred(){
    //     if() {
    //         connectionText.setFill(Color.GREEN);
    //         connectionText.setText("Connection Success");
    //         return true;
    //     } else {
    //         connectionText.setFill(Color.RED);
    //         connectionText.setText("Connection Fail");
    //         return false;
    //     }
    // }

    private void create(){
        connectionBtn = new Button("Connection");
        logoutBtn = new Button("Logout");
        menuText = new Text("Main Menu");
        connectionText = new Text(); 
    }

    private void layout(GridPane grid){

        grid.add(connectionBtn, 0, 1);
        grid.add(logoutBtn, 0, 3);
        grid.add(menuText, 0, 0);
        grid.add(connectionText, 0, 0);
    }

    private void align(GridPane grid){

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setHalignment(connectionBtn, HPos.CENTER);
        grid.setHalignment(logoutBtn, HPos.CENTER);
        grid.setHalignment(menuText, HPos.CENTER);

    }

    private void adjustment(GridPane grid){
        menuText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));  
    }
}