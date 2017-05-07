package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.CustomerModel;
import models.EmployeeModel;
import models.TimeModel;
import stages.MainInterfaceStage;
import stages.SetAllStages;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerInfoController implements Initializable {

    public CheckBox deliveryCheckbox;
    public CheckBox pickupCheckbox;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField addressOneField;
    public TextField addressTwoField;
    public TextField cityField;
    public TextField zipCodeField;
    public TextField stateField;
    public TextField phoneNumberField;
    public TextField employeeNameField;
    public TextField timeField;
    public TextField dayOfTheWeekField;
    public Button takeOrderButton;
    public Button backButton;
    public Button logOutButton;
    public ImageView goBackIcon;
    public ImageView logOutIcon;
    public ImageView takeOrderIcon;
    public Text goBackIconTitle;
    public Text takeOrderIconTitle;
    public Pane alertPane;
    public Button noButton;
    public Button yesButton;
    public AnchorPane mainPane;

    SetAllStages setAllStages = new SetAllStages();
    ApplicationContext applicationContext = ApplicationContext.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayEmployeeName();
        displayDateAndTime();
        limitInputFields();
        displayPickupOrDelivery();
        placeOrderActionHandler();
        initializeBackButton();
        logOutHandler();
    }

    private void displayEmployeeName() {
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        employeeNameField.setText(loggedInEmployee.getName());
    }

    private void displayDateAndTime() {
        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }

    private void limitInputFields() {
        phoneNumberField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > 10) phoneNumberField.setText(oldValue);
                }
        );

        zipCodeField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > 6) zipCodeField.setText(oldValue);
                }
        );

        stateField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > 2) stateField.setText(oldValue);
                }
        );

    }

    private void displayPickupOrDelivery() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        OperationMode operationMode = applicationContext.getOperationMode();

        switch (operationMode) {

            case DELIVERY:
                deliveryCheckbox.setSelected(true);
                pickupCheckbox.setSelected(false);
                disableButtons(applicationContext);
                break;
            case PICKUP:
                pickupCheckbox.setSelected(true);
                deliveryCheckbox.setSelected(false);
                disableButtons(applicationContext);
            default:
                break;
        }
    }

    private void placeOrderActionHandler() {
        takeOrderButton.setOnMouseClicked(storeCustomerProfile());
        takeOrderIcon.setOnMouseClicked(storeCustomerProfile());
        takeOrderIconTitle.setOnMouseClicked(storeCustomerProfile());
    }

    private EventHandler<MouseEvent> storeCustomerProfile() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HashMap<String, CustomerModel> customerProfile = new HashMap<>();

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String addressOne = addressOneField.getText();
                String addressTwo = addressTwoField.getText();
                String city = cityField.getText();
                String zipCode = zipCodeField.getText();
                String state = stateField.getText();
                String phoneNumber = phoneNumberField.getText();
                CustomerModel customerModel = new CustomerModel(firstName, lastName, addressOne, addressTwo, phoneNumber, state, zipCode, city);
                customerProfile.put(customerModel.getLastName(), customerModel);

                applicationContext.setCurrentCustomer(customerModel);
                setAllStages.stageByButton(takeOrderButton, "main-interface");
            }
        };
    }

    private void disableButtons(ApplicationContext applicationContext) {
        firstNameField.setFocusTraversable(true);
        lastNameField.setDisable(true);
        phoneNumberField.setDisable(true);
        addressOneField.setDisable(true);
        addressTwoField.setDisable(true);
        cityField.setDisable(true);
        zipCodeField.setDisable(true);
        stateField.setDisable(true);
        takeOrderButton.setDisable(true);

        if (applicationContext.getOperationMode() == OperationMode.PICKUP) {
            disableLastNameField();
            disablePhoneNumberField();
            sendPickup();
        }

        if (applicationContext.getOperationMode() == OperationMode.DELIVERY) {
            disableLastNameField();
            disablePhoneNumberField();
            disableBothAddressFields();
            disableCityField();
            disableStateField();
            disableZipCodeField();
            disableSendButtonWhenDelivery();
        }
    }

    private void disableLastNameField() {
        firstNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                lastNameField.setDisable(false);
            }
        });
    }

    private void disablePhoneNumberField() {
        lastNameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                phoneNumberField.setDisable(false);
            }
        });
    }

    private void disableBothAddressFields() {
        phoneNumberField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() == 10){
                        addressOneField.setDisable(false);
                        addressTwoField.setDisable(false);
                    }
                }
        );
    }

    private void disableCityField() {
        addressOneField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                cityField.setDisable(false);
            }
        });
    }

    private void disableStateField() {
        cityField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                stateField.setDisable(false);
            }
        });
    }

    private void disableZipCodeField() {
        stateField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                zipCodeField.setDisable(false);
            }
        });
    }

    private void disableSendButtonWhenDelivery() {
        zipCodeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                takeOrderButton.setDisable(false);
            }
        });
    }

    private void  sendPickup() {
        phoneNumberField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() == 10){
                        takeOrderButton.setDisable(false);
                    }
                }
        );
    }

    private void logOutHandler() {
        EventHandler<MouseEvent> value = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logOutAlertMessage();
            }
        };

        logOutButton.setOnMouseClicked(value);
        logOutIcon.setOnMouseClicked(value);
    }

    private void logOutAlertMessage() {
        alertPane.setVisible(true);
        mainPane.setOpacity(0.30);
        yesButton.setOnMouseClicked(getMouseEventEventHandler(yesButton,"log-in-screen"));

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPane.setOpacity(1);
                alertPane.setVisible(false);
            }
        });
    }

    private void initializeBackButton() {
        goBackIcon.setOnMouseClicked(getMouseEventEventHandler(backButton,"pickup-delivery"));
        goBackIconTitle.setOnMouseClicked(getMouseEventEventHandler(backButton,"pickup-delivery"));
        backButton.setOnMouseClicked(getMouseEventEventHandler(backButton, "pickup-delivery"));
    }


    private EventHandler<MouseEvent> getMouseEventEventHandler(Button button ,String stagePath) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setAllStages.stageByButton(button,stagePath);
            }
        };
    }

//    private EventHandler<MouseEvent> getMouseEventEventHandler(OperationMode operationMode,Button button ,String stagePath) {
//        return new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    applicationContext.setOperationMode(operationMode);
//                    setAllStages.stageByButton(button,stagePath);
//                }
//            };
//    }
}