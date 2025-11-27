package fr.ensicaen.genielogiciel.project.view;

import java.io.File;
import java.io.IOException;

import fr.ensicaen.genielogiciel.project.model.FigaroFactory;
import fr.ensicaen.genielogiciel.project.model.IBoat;
import fr.ensicaen.genielogiciel.project.model.IBoatFactory;
import fr.ensicaen.genielogiciel.project.model.IDecoratorEquipment;
import fr.ensicaen.genielogiciel.project.model.IDecoratorSail;
import fr.ensicaen.genielogiciel.project.model.MediumSail;
import fr.ensicaen.genielogiciel.project.model.Oceanis37Factory;
import fr.ensicaen.genielogiciel.project.presenter.BoatType;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteGamePresenter;
import fr.ensicaen.genielogiciel.project.presenter.CrewType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BoatSelectionController {

    @FXML
    private Button playButton;
    @FXML
    private Button escapeButton;

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

    private Stage _stage;

    private BoatType _boatType = null;
    private CrewType _crewType = CrewType.SINGLE;
    private String _nickName;

    private final String STYLE_SELECTED = "-fx-background-color: rgba(0, 0, 0, 0.4); -fx-border-color: #d19b4bff; -fx-border-width: 3px; -fx-text-fill: transparent;";
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: transparent;";

    public void initWithNickName( String nickName ) {
        _nickName = nickName;
        System.out.println("NickName reçu : " + _nickName);
    }

    public void setStage( Stage stage ) {
        _stage = stage;
    }

    @FXML
    public void initialize() {
        updateButtonStyles(null, chooseFigaro1Button, chooseFigaro2Button, chooseOceanis1Button, chooseOceanis2Button, onePlayerButton, twoPlayersButton, escapeButton, playButton);
    }

    private void updateButtonStyles( Button selected, Button... others ) {
        if (selected != null) {
            selected.setStyle(STYLE_SELECTED);
        }
        for (Button b : others) {
            if (b != null) {
                b.setStyle(STYLE_NORMAL);
            }
        }
    }

    @FXML
    private void onChooseFigaro1() {
        System.out.println("Figaro 1 voile choisi");
        _boatType = BoatType.FIGARO;
        updateButtonStyles(chooseFigaro1Button, chooseFigaro2Button, chooseOceanis1Button, chooseOceanis2Button);
    }

    @FXML
    private void onChooseFigaro2() {
        System.out.println("Figaro 2 voiles choisi");
        _boatType = BoatType.FIGARO;
        updateButtonStyles(chooseFigaro2Button, chooseFigaro1Button, chooseOceanis1Button, chooseOceanis2Button);
    }

    @FXML
    private void onChooseOceanis1() {
        System.out.println("Oceanis 1 voile choisi");
        _boatType = BoatType.OCEANIS37;
        updateButtonStyles(chooseOceanis1Button, chooseFigaro1Button, chooseFigaro2Button, chooseOceanis2Button);
    }

    @FXML
    private void onChooseOceanis2() {
        System.out.println("Oceanis 2 voiles choisi");
        _boatType = BoatType.OCEANIS37;
        updateButtonStyles(chooseOceanis2Button, chooseFigaro1Button, chooseFigaro2Button, chooseOceanis1Button);
    }

    @FXML
    private void onOnePlayer() {
        System.out.println("Mode 1 joueur sélectionné");
        _crewType = CrewType.SINGLE;
        updateButtonStyles(onePlayerButton, twoPlayersButton);
    }

    @FXML
    private void onTwoPlayers() {
        System.out.println("Mode 2 joueurs sélectionné");
        _crewType = CrewType.DOUBLE;
        updateButtonStyles(twoPlayersButton, onePlayerButton);
    }

    @FXML
    private void onEscape() {
        System.out.println("Annulation / Retour");
        if (_stage == null && playButton != null && playButton.getScene() != null) {
            _stage = (Stage) playButton.getScene().getWindow();
        }
        if (_stage != null) {
            _stage.close();
        }
    }

    @FXML
    private void onPlay() {
        System.out.println("PLAY !");

        if (_boatType == null) {
            System.err.println("Erreur : aucun bateau sélectionné !");
            return;
        }

        if (_stage == null) {
            _stage = (Stage) playButton.getScene().getWindow();
        }

        try {
            IDecoratorSail defaultSail = new MediumSail();
            IDecoratorEquipment defaultEquipment = null;

            IBoatFactory factory;
            if (_boatType == BoatType.FIGARO) {
                factory = new FigaroFactory(defaultSail, defaultEquipment);
            } else {
                factory = new Oceanis37Factory(defaultSail, defaultEquipment);
            }

            IBoat boat = factory.createBoat(200.0, 200.0);
            System.out.println("Bateau créé : " + boat);

            String configPath = "src/main/resources/fr/ensicaen/genielogiciel/project/model/default_configuration.json";

            File configFile = new File(configPath);
            if (!configFile.exists()) {
                System.err.println("CRITICAL ERROR: Configuration file NOT found at: " + configFile.getAbsolutePath());
                return;
            }

            String mapPath = "/fr/ensicaen/genielogiciel/project/view/SpotMap.fxml";

            ConcreteGamePresenter presenter = new ConcreteGamePresenter(_nickName, configPath);

            presenter.setBoat(boat);

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(mapPath)
            );
            Parent root = loader.load();
            GameView gameView = loader.getController();

            presenter.setGameView(gameView);
            gameView.setGamePresenter(presenter);

            Scene scene = new Scene(root);

            scene.setOnKeyPressed(event -> {
                gameView.handleKeyPressed(event.getCode());
            });

            _stage.setScene(scene);
            _stage.show();

            root.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}