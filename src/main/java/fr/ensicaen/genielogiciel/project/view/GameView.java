package fr.ensicaen.genielogiciel.project.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteGamePresenter;
import fr.ensicaen.genielogiciel.project.presenter.IPresenter;
import fr.ensicaen.genielogiciel.project.presenter.UserAction;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

public final class GameView implements IGameView {

    private static Stage _stage;
    private ConcreteGamePresenter _gamePresenter;
    private Ellipse _boat;
    private List<ImageView> _buoys = new ArrayList<>();
    private javafx.scene.shape.Line _currentBuoyLine;

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

    public void setGamePresenter( ConcreteGamePresenter gamePresenter ) {
        _gamePresenter = gamePresenter;
        System.out.println("GameView : Présentateur connecté.");
    }

    public void setPresenter( IPresenter presenter ) {
        if (presenter instanceof ConcreteGamePresenter) {
            this._gamePresenter = (ConcreteGamePresenter) presenter;
        }
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

    public void updateBoat( double dx, double dy, double angle ) {
        if (_boat != null) {
            rotate(_boat, angle);
            move(_boat, dx, dy);
        }
    }

    public void show() {
        if (_stage != null) _stage.show();
    }

    @Override
    public void addBoat( double x, double y ) {
        _boat = drawBoat(x, y, 6, 16);
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
        }
    }

    public static final class GameViewFactory {
        private GameViewFactory() {
        }

        public static GameView createView() throws IOException {
            FXMLLoader loader = new FXMLLoader(
                    GameView.class.getResource("SpotMap.fxml"), Main.getMessageBundle()
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
        try {
            windIcon.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/wind.png")).toExternalForm(), 18, 18, true, true
            ));
            headingIcon.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/heading.png")).toExternalForm(), 18, 18, true, true
            ));
            windArrow.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowUp.png")).toExternalForm()
            ));
            headingArrow.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/fr/ensicaen/genielogiciel/project/images/arrowUp.png")).toExternalForm()
            ));
        } catch (NullPointerException e) {
            System.err.println("Erreur chargement images : " + e.getMessage());
        }
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
            animation.play(); // Inicia as duas animações simultaneamente

            _base.getChildren().add(buoy);
            _buoys.add(buoy);
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
        javafx.scene.shape.Line coastLine = new javafx.scene.shape.Line(
                start_x, start_y, end_x, end_y
        );

        coastLine.setStroke(Color.SADDLEBROWN);
        coastLine.setStrokeWidth(10);

        _base.getChildren().add(coastLine);
        coastLine.toBack();
    }
}
