package fr.ensicaen.genielogiciel.project.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteGamePresenter;
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

    }

    public void updateDashboard( double windDir, double boatDir, double speed, String time ) {
        windArrow.setRotate(windDir);
        headingArrow.setRotate(boatDir);
        speedLabel.setText(String.format("%.1f", speed));
        timeLabel.setText(time);
    }


    @Override
    public void addBuoys( double x, double y ) {
        ImageView buoy = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource(
                        "/fr/ensicaen/genielogiciel/project/images/buoy.png")).toExternalForm())
        );

        buoy.setFitWidth(32);
        buoy.setFitHeight(32);
        buoy.setPreserveRatio(true);

        buoy.setLayoutX(x - buoy.getFitWidth() / 2);
        buoy.setLayoutY(y - buoy.getFitHeight() / 2);

        _base.getChildren().add(buoy);

        _buoys.add(buoy);
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
