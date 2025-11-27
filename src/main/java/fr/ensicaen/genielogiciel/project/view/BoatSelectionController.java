package fr.ensicaen.genielogiciel.project.view;

import java.io.IOException;

import fr.ensicaen.genielogiciel.project.presenter.ConcreteGamePresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BoatSelectionController {

    @FXML
    private Button chooseFigaro1Button;
    @FXML
    private Button chooseFigaro2Button;
    @FXML
    private Button chooseOceanis1Button;
    @FXML
    private Button chooseOceanis2Button;
    @FXML
    private Button onePlayerButton;
    @FXML
    private Button twoPlayersButton;
    @FXML
    private Button escapeButton;
    @FXML
    private Button playButton;

    private Stage _stage;

    private ConcreteGamePresenter _presenter;

    private String _nickName;

    public void initWithNickName( String nickName ) {
        _nickName = nickName;
        System.out.println("NickName reçu : " + _nickName);
    }

    public void setStage( Stage stage ) {
        _stage = stage;
    }

    public void setPresenter( ConcreteGamePresenter presenter ) {
        _presenter = presenter;
    }

    @FXML
    private void initialize() {
        System.out.println("BoatSelectionController initialisé");
    }

    @FXML
    private void onChooseFigaro1() {
        System.out.println("Figaro (1 voile) choisi");
        // TODO : stocker le choix dans le modèle / presenter
    }

    @FXML
    private void onChooseFigaro2() {
        System.out.println("Figaro (2 voiles) choisi");
    }

    @FXML
    private void onChooseOceanis1() {
        System.out.println("Oceanis (1 voile) choisi");
    }

    @FXML
    private void onChooseOceanis2() {
        System.out.println("Oceanis (2 voiles) choisi");
    }

    @FXML
    private void onOnePlayer() {
        System.out.println("Mode 1 joueur sélectionné");
    }

    @FXML
    private void onTwoPlayers() {
        System.out.println("Mode 2 joueurs sélectionné");
    }

    @FXML
    private void onEscape() {
        System.out.println("ECHAP cliqué");
        if (_stage != null) {
            _stage.close();
        }
    }

    @FXML
    private void onPlay() {
        System.out.println("PLAY ! lancé");
        if (_stage == null) {
            _stage = (Stage) playButton.getScene().getWindow();
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SpotMap.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            _stage.setScene(scene);
            _stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
