package fr.ensicaen.genielogiciel.project.view;

import java.io.IOException;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.GamePresenter;
import fr.ensicaen.genielogiciel.project.presenter.IGameView;
import fr.ensicaen.genielogiciel.project.presenter.UserAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public final class GameView implements IGameView {

    private static Stage _stage;
    private GamePresenter _gamePresenter;
    private Ellipse _boat;

    @FXML
    private Button pauseButton;
    @FXML
    private AnchorPane _base;

    @FXML
    private ImageView windIcon;

    @FXML
    private ImageView headingIcon;

    @FXML
    private Label timeLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private ImageView windArrow;

    @FXML
    private ImageView headingArrow;


    public void setGamePresenter( GamePresenter gamePresenter ) {
        _gamePresenter = gamePresenter;
    }


    public void rotate( Ellipse boat, double val ) {
        boat.setRotate(val);
    }

    public Ellipse drawBoat( double x, double y, double rx, double ry ) {
        Ellipse boat = new Ellipse(x, y, rx, ry);
        boat.setFill(Color.YELLOW);
        boat.setStroke(Color.RED);
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setSpread(0.6);
        shadow.setColor(Color.rgb(50, 50, 50, 0.6));
        boat.setEffect(shadow);
        _base.getChildren().add(boat);
        return boat;
    }

    public void move( Ellipse boat, double dx, double dy ) {
        boat.setLayoutX(boat.getLayoutX() + dx);
        boat.setLayoutY(boat.getLayoutY() + dy);
    }

    public void update( double dx, double dy, double angle ) {
        rotate(_boat, angle);
        move(_boat, dx, dy);
    }

    public void show() {
        _stage.show();
    }

    public void addBoat( double x, double y ) {
        _boat = drawBoat(x, y, 6, 16);
    }

    private void handleKeyPressed( KeyCode code ) {
        if (code == KeyCode.SPACE) {
            _gamePresenter.handleUserAction(UserAction.START);
        } else if (code == KeyCode.LEFT) {
            _gamePresenter.handleUserAction(UserAction.LEFT);
        } else if (code == KeyCode.RIGHT) {
            _gamePresenter.handleUserAction(UserAction.RIGHT);
        } else if (code == KeyCode.ESCAPE) {
            _gamePresenter.handleUserAction(UserAction.PAUSE);
        }
    }

    public static final class GameViewFactory {
        private GameViewFactory() {
        }

        public static GameView createView() throws IOException {
            FXMLLoader loader = new FXMLLoader(
                    LoginView.class.getResource("SpotMap.fxml"), Main.getMessageBundle()
            );

            Parent root = loader.load();
            GameView view = loader.getController();

            Scene scene = new Scene(root, 800, 600);
            Stage stage = new Stage();
            stage.setTitle(Main.getMessageBundle().getString("project.title"));
            stage.setScene(scene);
            stage.show();
            root.requestFocus();
            stage.setMaximized(true);
            _stage = stage;

            scene.addEventHandler(
                    KeyEvent.KEY_PRESSED, event -> {
                        view.handleKeyPressed(event.getCode());
                    }
            );

            return view;
        }
    }

    @FXML
    private void initialize() {
        windIcon.setImage(new Image(
                getClass().getResource("/fr/ensicaen/genielogiciel/project/images/wind.png").toExternalForm(), 18, 18, true, true
        ));
        headingIcon.setImage(new Image(
                getClass().getResource("/fr/ensicaen/genielogiciel/project/images/heading.png").toExternalForm(), 18, 18, true, true
        ));
        windArrow.setImage(new Image(
                getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowRight.png").toExternalForm()
        ));

        headingArrow.setImage(new Image(
                getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowUp.png").toExternalForm()
        ));

    }

    public void updateDashboard( double windDir, double boatDir, double speed, double time ) {
        windArrow.setRotate(windDir);
        headingArrow.setRotate(boatDir);
        speedLabel.setText(String.format("%.1f", speed));
        timeLabel.setText(String.format("%.1f s", time));
    }


}
