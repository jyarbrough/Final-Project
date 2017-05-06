package controllers;

import contexts.ApplicationContext;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.ReceiptModel;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        HashMap<Integer, ReceiptModel> receipts = applicationContext.getReceipts();
        Collection<ReceiptModel> allReceipts = receipts.values();

        double totalToDisplay = 0;

        for (ReceiptModel receiptModel : receipts.values()) {
            double grandTotal = receiptModel.getGrandTotal();

            totalToDisplay += grandTotal;
        }

        totalField.setText(String.valueOf(totalToDisplay));


    }

}
