package fr.ensicaen.genielogiciel.project.view;

import java.io.IOException;
import java.util.Objects;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteGamePresenter;
import fr.ensicaen.genielogiciel.project.presenter.IGameView;
import fr.ensicaen.genielogiciel.project.presenter.UserAction;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class GameView implements IGameView {

    private static Stage _stage;
    private ConcreteGamePresenter _gamePresenter;
    private Ellipse _boat;
    private javafx.scene.shape.Line _currentBuoyLine;


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


    public void setGamePresenter( ConcreteGamePresenter gamePresenter ) {
        _gamePresenter = gamePresenter;
    }

    public void rotate( Ellipse boat, double val ) {
        boat.setRotate(val);
    }

    public void move( Ellipse boat, double dx, double dy ) {
        boat.setLayoutX(boat.getLayoutX() + dx);
        boat.setLayoutY(boat.getLayoutY() + dy);
    }

    public void updateBoat( double dx, double dy, double angle ) {
        if (_boat != null) {
            rotate(_boat, angle);
            move(_boat, dx, dy);
        }
    }

    public void updateBoatCenter( double x, double y, double angle ) {
        _boat.setRotate(angle);
        _boat.setLayoutX(x);
        _boat.setLayoutY(y);
    }


    @Override
    public void addBoat( double x, double y ) {
        Ellipse boatHitbox = new Ellipse(0, 0, 12, 24);
        boatHitbox.setLayoutX(x);
        boatHitbox.setLayoutY(y);

        boatHitbox.setFill(Color.TRANSPARENT);
        boatHitbox.setStroke(Color.TRANSPARENT);

        this._boat = boatHitbox;

        ImageView boatSprite = new ImageView();
        try {
            boatSprite.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/boat.png")).toExternalForm()
            ));
        } catch (Exception e) {
            System.err.println("Error loading boat image: " + e.getMessage());
            boatHitbox.setFill(Color.YELLOW);
            boatHitbox.setStroke(Color.RED);
        }

        double spriteWidth = 40;
        double spriteHeight = 56;
        boatSprite.setFitWidth(spriteWidth);
        boatSprite.setFitHeight(spriteHeight);
        boatSprite.setPreserveRatio(true);

        boatSprite.setManaged(false);

        boatSprite.layoutXProperty().bind(boatHitbox.layoutXProperty().subtract(spriteWidth / 2));
        boatSprite.layoutYProperty().bind(boatHitbox.layoutYProperty().subtract(spriteHeight / 2));

        boatSprite.rotateProperty().bind(boatHitbox.rotateProperty());

        _base.getChildren().addAll(boatSprite, boatHitbox);
    }

    public void handleKeyPressed( KeyCode code ) {
        if (_gamePresenter == null) return;

        if (code == KeyCode.SPACE) {
            _gamePresenter.handleUserAction(UserAction.START);
        } else if (code == KeyCode.LEFT) {
            _gamePresenter.handleUserAction(UserAction.LEFT);
        } else if (code == KeyCode.RIGHT) {
            _gamePresenter.handleUserAction(UserAction.RIGHT);
        } else if (code == KeyCode.ESCAPE) {
            _gamePresenter.handleUserAction(UserAction.PAUSE);
        } else if (code == KeyCode.F) {
            _gamePresenter.launchEndGame();
        }

    }

    public static final class GameViewFactory {

        public static GameView createView() throws IOException {
            FXMLLoader loader = new FXMLLoader(
                    GameView.class.getResource("SpotMap.fxml"), Main.getMessageBundle()
            );

            Parent root = loader.load();
            GameView view = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(Main.getMessageBundle().getString("project.title"));
            stage.setScene(scene);
            root.requestFocus();
            stage.setMaximized(true);
            _stage = stage;

            scene.addEventHandler(
                    KeyEvent.KEY_PRESSED, event -> view.handleKeyPressed(event.getCode())
            );

            return view;
        }
    }

    public void show() {
        _stage.show();
    }

    public void close() {
        _stage.close();
    }

    public Pane getRoot() {
        return _base;
    }

    @FXML
    private void initialize() {
        try {
            windIcon.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/wind.png")).toExternalForm(), 18, 18, true, true
            ));
            headingIcon.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/heading.png")).toExternalForm(), 18, 18, true, true
            ));
            windArrow.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowDown.png")).toExternalForm()
            ));
            headingArrow.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowUp.png")).toExternalForm()
            ));
        } catch (NullPointerException e) {
            System.err.println("Erreur chargement images : " + e.getMessage());
        }
        windIcon.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/wind.png")).toExternalForm(), 18, 18, true, true
        ));
        headingIcon.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/heading.png")).toExternalForm(), 18, 18, true, true
        ));
        windArrow.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowDown.png")).toExternalForm()
        ));

        headingArrow.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowUp.png")).toExternalForm()
        ));

    }

    public void updateDashboard( double windDir, double boatDir, double speed, String time ) {
        if (windArrow != null) windArrow.setRotate(windDir);
        if (headingArrow != null) headingArrow.setRotate(boatDir);
        if (speedLabel != null) speedLabel.setText(String.format("%.1f", speed));
        if (timeLabel != null) timeLabel.setText(time);
    }


    @Override
    public void addBuoys( double x, double y ) {
        try {
            ImageView buoy = new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResource(
                            "/fr/ensicaen/genielogiciel/project/images/buoy.png")).toExternalForm())
            );

            buoy.setFitWidth(48);
            buoy.setFitHeight(48);
            buoy.setPreserveRatio(true);
            buoy.setLayoutX(x - buoy.getFitWidth() / 2);
            buoy.setLayoutY(y - buoy.getFitHeight() / 2);

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.CORNFLOWERBLUE);  //color(20, 68, 102));
            buoy.setEffect(dropShadow);

            TranslateTransition bobbing = new TranslateTransition(Duration.seconds(1.5), buoy);
            bobbing.setByY(-3);
            bobbing.setCycleCount(Timeline.INDEFINITE);
            bobbing.setAutoReverse(true);

            RotateTransition rotation = new RotateTransition(Duration.seconds(3.0), buoy);
            rotation.setByAngle(8);
            rotation.setCycleCount(Timeline.INDEFINITE);
            rotation.setAutoReverse(true);

            ParallelTransition animation = new ParallelTransition(bobbing, rotation);
            animation.play();

            _base.getChildren().add(buoy);
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image de la bouée.");
        }
    }

    public void addBuoyLines( double s_x, double s_y, double e_x, double e_y ) {
        if (_currentBuoyLine != null) {
            _base.getChildren().remove(_currentBuoyLine);
        }
        javafx.scene.shape.Line fxLine = new javafx.scene.shape.Line(
                s_x, s_y, e_y, e_x
        );

        fxLine.setStroke(Color.RED);
        fxLine.setStrokeWidth(2);
        _base.getChildren().add(fxLine);

        TranslateTransition bobbing = new TranslateTransition(Duration.seconds(1.5), fxLine);
        bobbing.setByY(-3);
        bobbing.setCycleCount(Timeline.INDEFINITE);
        bobbing.setAutoReverse(true);
        bobbing.play();
        _currentBuoyLine = fxLine;
    }

    public void removeCurrentBuoyLine() {
        if (_currentBuoyLine != null) {
            _base.getChildren().remove(_currentBuoyLine);
            _currentBuoyLine = null;
        }
    }

    @Override
    public void addCoast( double start_x, double start_y, double end_x, double end_y ) {
        javafx.scene.shape.Line coastLine = new javafx.scene.shape.Line(start_x, start_y, end_x, end_y);

        coastLine.setStroke(Color.DARKGREEN);
        coastLine.setStrokeWidth(6);
        coastLine.setOpacity(0.8);

        _base.getChildren().add(coastLine);
    }

    public Stage getStage() {
        return _stage;
    }

}
