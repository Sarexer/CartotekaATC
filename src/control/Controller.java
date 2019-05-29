package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Service;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller extends Application {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private Button btnRecharge;

    @FXML
    private TextField txtAmount;

    @FXML
    private ComboBox<Service> cbServices;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnRenewal;

    @FXML
    private Button btnCheckDebt;

    @FXML
    void initialize() {
        DBController controller = new DBController();

        ObservableList<Service> services = controller.loadServices();
        cbServices.setItems(services);

        btnRecharge.setOnAction(event -> {
            controller.recharge(txtPhoneNumber.getText(), Double.parseDouble(txtAmount.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Пополнение баланса");
            alert.setHeaderText("Баланс успешно пополнен");
            alert.setContentText("Баланс пополнен на " + txtAmount.getText() + " руб.");

            alert.showAndWait();
        });

        btnConnect.setOnAction(event -> {
            controller.connect(txtPhoneNumber.getText(), cbServices.getValue());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Подключение услуги");
            alert.setHeaderText("Услуга "+cbServices.getValue().getName() + " успешно подключена");

            alert.showAndWait();
        });

        btnRenewal.setOnAction(event -> {
            controller.renewal(txtPhoneNumber.getText(), cbServices.getValue());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Продление услуги");
            alert.setHeaderText("Услуга "+cbServices.getValue().getName() + " успешно продлена");

            alert.showAndWait();
        });

        btnCheckDebt.setOnAction(event -> {
            int result = controller.checkDebt(txtPhoneNumber.getText());
            if(result == 1){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Проверка задолженности");
                alert.setHeaderText("У вас есть задолженность!!");

                alert.showAndWait();
            }else if(result == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Проверка задолженности");
                alert.setHeaderText("Задолженность не найдена");

                alert.showAndWait();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../boundary/sample.fxml"));
        primaryStage.setTitle("Картотека АТС");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

