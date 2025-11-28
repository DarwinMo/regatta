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
    private Text _crewFeedBackText;
    @FXML
    private Text _sailFeedBackText;
    @FXML
    private Text _boatFeedBackText;

    // Buttons
    @FXML
    private Button _singleCrew;
    @FXML
    private Button _doubleCrew;
    @FXML
    private Button _lunchGameBtn;
    @FXML
    private Button _mediumSail;
    @FXML
    private Button _largeSail;
    @FXML
    private Button _oceanisBtn;
    @FXML
    private Button _figaroBtn;

    private static Stage _stage;
    private SailType _sailType = null;
    private CrewType _crewType = null;
    private BoatType _boatType = null;

    private ConcreteGamePresenter _gamePresenter;
    private ConfigurationPresenter _configurationPresenter;

    @FXML
    private void onSingleCrewClicked() {
        _crewType = CrewType.SINGLE;
        _crewFeedBackText.setText("Crew type changed to single");
    }

    @FXML
    private void onDoubleCrewClicked() {
        _crewType = CrewType.DOUBLE;
        _crewFeedBackText.setText("Crew type changed to double");
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
        _sailFeedBackText.setText("Sail type changed to medium");
    }

    @FXML
    private void onLargeSailClicked() {
        _sailType = SailType.LARGE;
        _sailFeedBackText.setText("Sail type changed to large");
    }

    @FXML
    private void onOceanisClicked() {
        _boatType = BoatType.OCEANIS37;
        _boatFeedBackText.setText("Boat type changed to Oceanis37");
    }


    @FXML
    private void onFigaroClicked() {
        _boatType = BoatType.FIGARO;
        _boatFeedBackText.setText("Boat type changed to Figaro");
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