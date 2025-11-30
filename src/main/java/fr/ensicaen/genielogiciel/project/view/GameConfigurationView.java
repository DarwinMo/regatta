package fr.ensicaen.genielogiciel.project.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.ensicaen.genielogiciel.project.presenter.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @FXML
    private Text _error;

    @FXML
    private TextField _fileInput;


    private static Stage _stage;
    private SailType _sailType = null;
    private CrewType _crewType = null;
    private BoatType _boatType = null;

    private ConfigurationPresenter _configurationPresenter;

    private final String sailFeedbackText = "Sail : ";
    private final String crewFeedbackText = "crew : ";
    private final String boatFeedbackText = "Boat : ";

    @FXML
    private void onSingleCrewClicked() {
        _crewType = CrewType.SINGLE;
        _crewFeedbackText.setText(crewFeedbackText + " Single");
    }

    @FXML
    private void onDoubleCrewClicked() {
        _crewType = CrewType.DOUBLE;
        _crewFeedbackText.setText(crewFeedbackText + " Double");
    }

    @FXML
    private void onLaunchGameClicked() {
        if (!_fileInput.getText().isEmpty()) {
            if (!checkFileInput(_fileInput.getText())) {
                _error.setText("Please enter an existent/valid file path");
            } else {
                _configurationPresenter.launchGameFromFile(_fileInput.getText());
            }
        } else {
            _configurationPresenter.launchGame(_sailType, _crewType, _boatType);
        }
    }

    public boolean checkFileInput( String pathString ) {
        Path path = Paths.get(pathString);
        boolean exists = Files.exists(path);

        boolean isJsonExt = pathString.toLowerCase().endsWith(".json");

        if (exists && isJsonExt) {
            System.out.println("File exists and has .json extension.");
        } else {
            System.out.println("File invalid or missing.");
        }
        return exists;
    }

    public void setConfigurationPresenter( ConfigurationPresenter configurationPresenter ) {
        _configurationPresenter = configurationPresenter;
    }

    @FXML
    private void onMediumSailClicked() {
        _sailType = SailType.MEDIUM;
        _sailFeedbackText.setText(sailFeedbackText + " Medium");
    }

    @FXML
    private void onLargeSailClicked() {
        _sailType = SailType.LARGE;
        _sailFeedbackText.setText(sailFeedbackText + " Large");
    }

    @FXML
    private void onOceanisClicked() {
        _boatType = BoatType.OCEANIS37;
        _boatFeedbackText.setText(boatFeedbackText + " Oceanis37");
    }


    @FXML
    private void onFigaroClicked() {
        _boatType = BoatType.FIGARO;
        _boatFeedbackText.setText(boatFeedbackText + " Figaro");
    }

    public void displayError( String message ) {
        _error.setText(message);
    }

    public static class GameConfigurationViewFactory {
        private GameConfigurationViewFactory() {
        }

        public static GameConfigurationView createConfigurationView() throws IOException {
            final FXMLLoader loader = new FXMLLoader(GameConfigurationView.class.getResource(
                    "/fr/ensicaen/genielogiciel/project/view/ConfigurationPanel.fxml"));
            final Parent root = loader.load();
            final GameConfigurationView gameConfigurationView = loader.getController();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root));
            _stage.setResizable(false);
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