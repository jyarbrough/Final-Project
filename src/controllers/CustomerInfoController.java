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
import stages.PickupDeliveryStage;
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
    public Button placeOrderButton;
    public Button backButton;
    public Button logOutButton;

    public ImageView goBackIcon;
    public ImageView logOutIcon;
    public ImageView takeOrderIcon;
    public Text goBackIconTitle;
    public Text takeOrderIconTitle;
    public AnchorPane customerInfoAnchor;
    public Pane alertBackground;
    public Button yesButton;
    public Button noButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayEmployeeName();
        displayDateAndTime();
        limitPhoneFieldInput();
        displayPickupOrDelivery();
        placeOrderActionHandler();
        backButtonAction();

        SetAllStages setAllStages = new SetAllStages();
        iconClickHandlers(setAllStages);
        logOutHandler();
    }

    private void logOutHandler() {

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                logOutAlertMessage();
            }
        });

        logOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                logOutAlertMessage();

            }
        });
    }

    private void logOutAlertMessage() {
        alertBackground.setVisible(true);
        customerInfoAnchor.setOpacity(0.30);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "customer-info");
            }
        });
    }

    private void iconClickHandlers(final SetAllStages setAllStages) {
        goBackIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
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

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        logOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");

            }
        });
    }

    private void displayEmployeeName() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        employeeNameField.setText(loggedInEmployee.getName());
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }

    private void limitPhoneFieldInput() {
        phoneNumberField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > 10) phoneNumberField.setText(oldValue);
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

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainInterfaceStage mainInterfaceStage = new MainInterfaceStage();
                ApplicationContext applicationContext = ApplicationContext.getInstance();
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
                mainInterfaceStage.stage(placeOrderButton);
            }
        });

//        takeOrderIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                SetAllStages setAllStages = new SetAllStages();
//                setAllStages.stageByButton(placeOrderButton, "main-interface");
//            }
//        });
//
//        takeOrderIconTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                MainInterfaceStage mainInterfaceStage = new MainInterfaceStage();
//                mainInterfaceStage.stage(placeOrderButton);
//            }
//        });
    }

    private void backButtonAction() {

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);

                PickupDeliveryStage pickupDeliveryStage = new PickupDeliveryStage();
                pickupDeliveryStage.stage(backButton);
            }
        });
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
        placeOrderButton.setDisable(true);

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
                placeOrderButton.setDisable(false);
            }
        });
    }


    private void  sendPickup() {

        phoneNumberField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() == 10){
                        placeOrderButton.setDisable(false);
                    }
                }
        );
    }
}