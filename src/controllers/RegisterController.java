package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.OrderModel;
import stages.SetAllStages;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public Button backButton;
    public ImageView goBackIcon;
    public Text goBackIconTitle;
    public Button logOutButton;
    public ImageView logOutIcon;
    public TextField totalField;
    public Pane alertBackground;
    public Button yesButton;
    public Button noButton;

    SetAllStages setAllStages = new SetAllStages();
    ApplicationContext applicationContext = ApplicationContext.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        HashMap<Integer, OrderModel> receipts = applicationContext.getReceipts();
        Collection<OrderModel> allReceipts = receipts.values();

        double totalToDisplay = 0;

        for (OrderModel orderModel : receipts.values()) {


            double grandTotal = orderModel.getGrandTotal();

            totalToDisplay += grandTotal;
        }

        totalField.setText(String.valueOf(totalToDisplay));

        backButtonHandler();

    }

    private void backButtonHandler() {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });

        goBackIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });

        goBackIconTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });
    }

}
