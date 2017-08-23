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
    private Button connectionBtn, logoutBtn, thermalBtn;
    private Text menuText, numConnectionsText;
    private TextField routerTextField;
    

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

    public Button getThermalButton(){
        return thermalBtn;
    }

    public Button getLogoutButton(){
        return logoutBtn;
    }
    
    public int connectionOccurred(){
        return Integer.parseInt(routerTextField.getText());
    }

    private void create(){
        connectionBtn = new Button("Connection");
        logoutBtn = new Button("Logout");
        thermalBtn = new Button("Thermal Analysis");
        menuText = new Text("Main Menu");
        numConnectionsText = new Text("");
        routerTextField = new TextField();
    }

    private void layout(GridPane grid){

        grid.add(connectionBtn, 0, 1);
        grid.add(routerTextField, 1, 1);
        grid.add(thermalBtn, 0, 2);
        grid.add(logoutBtn, 0, 3);
        grid.add(menuText, 0, 0);
        grid.add(numConnectionsText, 0, 4);
    }

    private void align(GridPane grid){

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setHalignment(connectionBtn, HPos.CENTER);
        grid.setHalignment(thermalBtn, HPos.CENTER);
        grid.setHalignment(logoutBtn, HPos.CENTER);
        grid.setHalignment(menuText, HPos.CENTER);

    }

    private void adjustment(GridPane grid){
        menuText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));  
    }
}