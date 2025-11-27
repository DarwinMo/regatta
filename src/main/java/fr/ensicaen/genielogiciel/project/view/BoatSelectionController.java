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

    private Stage _stage;

    private BoatType _boatType = null;
    private CrewType _crewType = CrewType.SINGLE;
    private String _nickName;

    public void initWithNickName( String nickName ) {
        _nickName = nickName;
        System.out.println("NickName reçu : " + _nickName);
    }

    public void setStage( Stage stage ) {
        _stage = stage;
    }

    @FXML
    private void onChooseFigaro1() {
        System.out.println("Figaro 1 voile choisi");
        _boatType = BoatType.FIGARO;
    }

    @FXML
    private void onChooseFigaro2() {
        System.out.println("Figaro 2 voiles choisi");
        _boatType = BoatType.FIGARO;
    }

    @FXML
    private void onChooseOceanis1() {
        System.out.println("Oceanis 1 voile choisi");
        _boatType = BoatType.OCEANIS37;
    }

    @FXML
    private void onChooseOceanis2() {
        System.out.println("Oceanis 2 voiles choisi");
        _boatType = BoatType.OCEANIS37;
    }

    @FXML
    private void onOnePlayer() {
        System.out.println("Mode 1 joueur sélectionné");
        _crewType = CrewType.SINGLE;
    }

    @FXML
    private void onTwoPlayers() {
        System.out.println("Mode 2 joueurs sélectionné");
        _crewType = CrewType.DOUBLE;
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

            IBoat boat = factory.createBoat(100.0, 100.0);

            String configPath = "src/main/resources/fr/ensicaen/genielogiciel/project/model/default_configuration.json";
            File configFile = new File(configPath);
            if (!configFile.exists()) {
                System.err.println("CRITICAL ERROR: Config file not found.");
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