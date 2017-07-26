
// imports ended up all over the show without an IDE telling me if it is used or not
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import java.util.Collections;

public class Program extends Application {
    // Sets up all the different screens that will be used
    private Login loginScreen = new Login();
    private Menu menuScreen = new Menu();

    // Sets the different available scenes in the program
    private Scene loginScene = new Scene(loginScreen.getGrid(), 550, 600);
    private Scene menuScene = new Scene(menuScreen.getGrid(), 550, 600);

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
                connection();
            }
        });

        primaryStage.setTitle("Survey Program");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public void connection() {
        int timeout = 1000;
        int port = 1234;

        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

            String macAddress = "F8-2F-A8-F5-B8-5F";

            NetworkInterface adapter = null;

            for (NetworkInterface iface : Collections.list(nets)) {
                byte[] mac = iface.getHardwareAddress();

                if (mac == null || !iface.isUp()) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                for (byte b : mac) {
                    sb.append(String.format("%02X%s", b, "-"));
                }

                sb.deleteCharAt(sb.length() - 1);
                if (sb.toString().equals(macAddress)) {
                    adapter = iface;
                    break;
                }
            }

            if (adapter == null) {
                System.out.println("No network adapter found");
                System.exit(1);
            } else {
                System.out.println("Using: " + adapter.getName());
            }

            Enumeration<InetAddress> myAddress = adapter.getInetAddresses();

            String currentIP = myAddress.nextElement().getHostAddress();

            String subnet = getSubnet(currentIP);
            System.out.println("Subnet: " + subnet);

            for (int i = 100; i < 254; i++) {

                String host = subnet + i;
                System.out.println("Checking :" + host);

                InetAddress address = InetAddress.getByName(host);
                if (address.isReachable(timeout)) {
                    System.out.println(host + " is reachable");

                    String hostName;

                    hostName = address.getHostName();

                    System.out.println(hostName);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator + 1, lastSeparator + 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
