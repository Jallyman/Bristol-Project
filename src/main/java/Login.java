import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.paint.*;

public class Login {

    private GridPane grid = new GridPane();
    private HBox hbLogin = new HBox(10);
    private Text sceneTitle;
    private Label username, pw;
    private TextField userTextField;
    private PasswordField passWBox;
    private Button loginBtn;
    private Text loginText;

        public Login(){
            setup();    
        }

        private void setup(){
            create();
            layout(grid);
            align(grid);
            adjustment(grid);
            hbLogin.getChildren().add(loginBtn);
        }

        // Getters to return grid to main running of the program
        public GridPane getGrid(){
            return grid;
        }

        public Button getButton(){
            return loginBtn;
        }

        // Checks whether a login attempt occurred
        public boolean loginOccurred(){
            if(userTextField.getText().equals("admin") && passWBox.getText().equals("admin")) {
                loginText.setFill(Color.GREEN);
                loginText.setText("Login Success");
                return true;
            } else {
                loginText.setFill(Color.RED);
                loginText.setText("Login Fail");
                return false;
            }
        }

        // Design method of create(), layout() and align() taken from notes
        // Repeated in all of the classes used for screens
        private void create(){

            sceneTitle = new Text("Welcome");
            username = new Label("Username: ");
            userTextField = new TextField();
            pw = new Label("Password: ");
            passWBox = new PasswordField();
            loginBtn = new Button("Login");
            loginText = new Text();
        }

        private void layout(GridPane grid){

            grid.add(sceneTitle, 0, 0, 2, 1);
            grid.add(username, 0, 1);
            grid.add(userTextField, 1, 1);
            grid.add(pw, 0, 2);
            grid.add(passWBox, 1, 2);
            grid.add(hbLogin, 1, 4);
            grid.add(loginText, 1, 5);      
        }

        private void align(GridPane grid) {

            grid.setAlignment(Pos.TOP_CENTER);
            grid.setHgap(25);
            grid.setVgap(25);
            grid.setPadding(new Insets(25, 25, 25, 25));

            grid.setHalignment(loginText, HPos.RIGHT);
            
            hbLogin.setAlignment(Pos.BOTTOM_RIGHT);
        }

        private void adjustment(GridPane grid){
            sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));  
        }
}