package fr.trollgun.motcroises;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class ChoisirGrille implements Initializable {
    public ComboBox<MotCroisesGrille> comboGrille;
    public Button buttonValider;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<MotCroisesGrille> list = FXCollections.observableArrayList();
            Map<Integer, String> map = HelloApplication.chargerGrille.grillesDisponibles();
            for (Map.Entry<Integer, String> i : map.entrySet()) {
                list.add(new MotCroisesGrille(i.getKey(), i.getValue()));
            }
            comboGrille.setItems(list);
            buttonValider.setOnMouseClicked(this::onClickedMouse);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void onClickedMouse(MouseEvent mouseEvent) {
        HelloApplication.motCroisesGrille = comboGrille.getValue();
        if(HelloApplication.stage != null && !HelloApplication.stage.isShowing()) HelloApplication.stage.show();
        HelloApplication.stageChoisirGrille.close();
    }


}
