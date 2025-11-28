package fr.ensicaen.genielogiciel.project.view;

import java.io.File;
import java.io.IOException;

import fr.ensicaen.genielogiciel.project.presenter.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameConfigurationView {


    // Text nodes
    @FXML
    private Text _boatConfigurationPanelText;
    @FXML
    private Text _configurationFilePanelText;
    @FXML
    private Text _crewFeedbackText;
    @FXML
    private Text _sailFeedbackText;
    @FXML
    private Text _boatFeedbackText;


    private static Stage _stage;
    private SailType _sailType = null;
    private CrewType _crewType = null;
    private BoatType _boatType = null;

    private ConfigurationPresenter _configurationPresenter;

    @FXML
    private void onSingleCrewClicked() {
        _crewType = CrewType.SINGLE;
        _crewFeedbackText.setText(_crewFeedbackText.getText() + " Single.");
    }

    @FXML
    private void onDoubleCrewClicked() {
        _crewType = CrewType.DOUBLE;
        _crewFeedbackText.setText(_crewFeedbackText.getText() + " Double.");
    }

    @FXML
    private void onLaunchGameClicked() {
        _configurationPresenter.launchGame(_sailType, _crewType, _boatType);
    }

    public void setConfigurationPresenter( ConfigurationPresenter configurationPresenter ) {
        _configurationPresenter = configurationPresenter;
    }

    @FXML
    private void onMediumSailClicked() {
        _sailType = SailType.MEDIUM;
        _sailFeedbackText.setText(_sailFeedbackText.getText() + " Medium.");
    }

    @FXML
    private void onLargeSailClicked() {
        _sailType = SailType.LARGE;
        _sailFeedbackText.setText(_sailFeedbackText.getText() + " Large ");
    }

    @FXML
    private void onOceanisClicked() {
        _boatType = BoatType.OCEANIS37;
        _boatFeedbackText.setText(_boatFeedbackText.getText() + " Oceanis37.");
    }


    @FXML
    private void onFigaroClicked() {
        _boatType = BoatType.FIGARO;
        _boatFeedbackText.setText(_boatFeedbackText.getText() + " Figaro.");
    }

    public void displayError( String message ) {
        // TODO: implement
        System.out.println("some user sleection is missing");
    }

    public static class GameConfigurationViewFactory {
        private GameConfigurationViewFactory() {
        }

        public static GameConfigurationView createConfigurationView() throws IOException {
            FXMLLoader loader = new FXMLLoader(GameConfigurationView.class.getResource(
                    "/fr/ensicaen/genielogiciel/project/view/ConfigurationPanel.fxml"));
            Parent root = loader.load();
            GameConfigurationView gameConfigurationView = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            _stage = stage;
            return gameConfigurationView;
        }
    }

    public void show() {
        _stage.show();
    }

    public void close() {
        _stage.close();
    }


}