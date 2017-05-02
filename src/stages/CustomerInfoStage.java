package stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerInfoStage {

    public void stage(Button button) {

        Stage stage;
        Parent root = null;

        stage = (Stage) button.getScene().getWindow();

        try {
            root = FXMLLoader.load(getClass().getResource("../views/customer-info.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 1300, 900);
        stage.setScene(scene);
        stage.show();
    }

}
