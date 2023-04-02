package fr.trollgun.motcroises;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class MotCroisesController implements Initializable {

    public MenuItem choisirGrille;
    public MenuItem grilleAleatoire;
    public Label nomGrille;
    public Label positionCurseur;
    public Label definition;
    private MotsCroisesTP6 motsCroises;
    private Grille<TextField> textFieldGrille;

    private int selectedCellX = -1;
    private int selectedCellY = -1;

    private boolean showSolution = false;

    @FXML
    private GridPane grilleMotCroisees;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choisirGrille.setOnAction(this::openChoisirGrille);
        setupGrille();
    }

    /*
     * Permet de charger une grille dans l'interface
     */
    private void setupGrille(){
        try {
            //Recupéres le mot croises
            motsCroises = HelloApplication.chargerGrille.extraireGrille(HelloApplication.motCroisesGrille.getNumGrille());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //on génère une grille de textfield
        textFieldGrille = new Grille<>(motsCroises.getHauteur(), motsCroises.getLargeur());
        for (int i = 1; i <= motsCroises.getHauteur(); i++) {
            for (int j = 1; j <= motsCroises.getLargeur(); j++) {
                int finalJ = j;
                int finalI = i;
                //Chaque text field a une taille immense qui est redéfini par des contraintes
                TextField textField = new TextField();
                textFieldGrille.setCellule(i, j, textField);
                textField.setMaxWidth(1000);
                textField.setMaxHeight(1000);
                textField.setFont(new Font(30));
                textField.setAlignment(Pos.CENTER);
                if (motsCroises.estCaseNoire(i, j)) textField.setDisable(true);
                else {
                    //Text formatter pour eviter de mettre plus d'un caractère
                    textField.textProperty().bindBidirectional(motsCroises.getStringProperty(i,j));
                    textField.setTextFormatter(new TextFormatter<>((TextFormatter.Change change) -> {
                        String newText = change.getControlNewText();
                        if (newText.length() > 1) {
                            return null;
                        } else {
                            return change;
                        }
                    }));
                    //Permet la naviguation avec les touches de claviers
                    textField.setOnKeyPressed(this::onPressedKey);
                    //Permet de savoir la case selectionné pour affiché les information de la case
                    if(selectedCellX == -1 && selectedCellY == -1){
                        selectedCellX = j;
                        selectedCellY = i;
                    }
                    //Debug
                    if(showSolution) textField.setText(motsCroises.getSolution(i, j) + "");
                    textField.setTooltip(new Tooltip("Ligne : " + j + " || Colonne : " + i + "\n" +
                            motsCroises.getVerboseDefinition(i, j)));
                    //Permet la mise a jour des coordonnées de la case selectionné
                    textField.setOnMouseClicked(mouseEvent -> onClickedCell(mouseEvent, finalJ, finalI));
                }
                grilleMotCroisees.add(textField, j - 1, i - 1);
            }
        }
        //contraintes des textfields
        for (int i = 0; i < motsCroises.getHauteur(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            grilleMotCroisees.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < motsCroises.getLargeur(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            grilleMotCroisees.getColumnConstraints().add(columnConstraints);
        }
        nomGrille.setText(motsCroises.getNomGrille());
        updateDefinition();
        updatePosition();
    }

    private void clearGrille() {
        grilleMotCroisees.getChildren().clear();
        selectedCellX = -1;
        selectedCellY = -1;
    }

    private void openChoisirGrille(ActionEvent actionEvent) {
        HelloApplication.stage.close();
        HelloApplication.stageChoisirGrille.showAndWait();
        clearGrille();
        setupGrille();

    }

    private void onClickedCell(MouseEvent mouseEvent, int x, int y) {
        selectedCellY = y;
        selectedCellX = x;
        updatePosition();
        updateDefinition();
        if (mouseEvent.getButton().equals(MouseButton.MIDDLE)) {
            System.out.println("MACRON ENCULé");
            motsCroises.setProposition(y, x, motsCroises.getSolution(y,x));
        }
    }


    public void onPressedKey(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP -> moveCursor(0, -1);
            case DOWN -> moveCursor(0, 1);
            case LEFT -> moveCursor(-1, 0);
            case RIGHT -> moveCursor(1, 0);
        }
    }

    public void moveCursor(int x, int y) {
        int posX = selectedCellX + x;
        int posY = selectedCellY + y;
        if (posX > motsCroises.getLargeur()) posX = 1;
        else if (posX == 0) posX = motsCroises.getLargeur();
        if (posY > motsCroises.getHauteur()) posY = 1;
        else if (posY == 0) posY = motsCroises.getHauteur();
        TextField textField = textFieldGrille.getCellule(posY, posX);
        if (!textField.isDisable()) {
            textField.requestFocus();
            selectedCellX = posX;
            selectedCellY = posY;
            updateDefinition();
            updatePosition();
        } else {
            if (x > 0) x++;
            else if (x < 0) x--;
            if (y > 0) y++;
            else if (y < 0) y--;
            moveCursor(x, y);
        }
    }

    public void updateDefinition() {
        definition.setText(motsCroises.getVerboseDefinition(selectedCellY, selectedCellX));
    }

    public void updatePosition() {
        positionCurseur.setText("Ligne : " + selectedCellY + " || Colonne : " + selectedCellX);
    }

}