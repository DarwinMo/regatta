package fr.ensicaen.genielogiciel.project.view;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.GamePresenter;
import fr.ensicaen.genielogiciel.project.presenter.IGameView;
import fr.ensicaen.genielogiciel.project.presenter.UserAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import java.util.Random;
import java.io.IOException;

public final class GameView implements IGameView {
    private static Stage _stage;
    private GamePresenter _gamePresenter;
    private Ellipse _boat;
    private Circle _buoy1;
    private Circle _buoy2;
    private Circle _buoy3;
    @FXML
    private AnchorPane _base;

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

    public Circle drawBuoy( double x, double y, double radius ) {
        Circle buoy = new Circle(x, y, radius);
        buoy.setFill(Color.TRANSPARENT);
        buoy.setStroke(Color.RED);
        buoy.setStrokeWidth(6);
        _base.getChildren().add(buoy);
        return buoy;
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


    public void addBuoys() {
        double radius = 13;

        _buoy1 = drawBuoy(200, 100, radius);
        _buoy2 = drawBuoy(2 * _base.getWidth() - 100, 500, radius);
        _buoy3 = drawBuoy(_base.getWidth() / 2, _base.getHeight() - 120, radius);

    }


    private void handleKeyPressed( KeyCode code ) {
        if (code == KeyCode.SPACE) {
            _gamePresenter.handleUserAction(UserAction.START);
        } else if (code == KeyCode.LEFT) {
            _gamePresenter.handleUserAction(UserAction.LEFT);
        } else if (code == KeyCode.RIGHT) {
            _gamePresenter.handleUserAction(UserAction.RIGHT);
        }
    }

    public static final class GameViewFactory {
        private GameViewFactory() {
            // Factory class as Utility class where the constructor is private
        }

        public static GameView createView() throws IOException {
            final FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("SpotMap.fxml"), Main.getMessageBundle());
            final Parent root = loader.load();
            final GameView view = loader.getController();
            final Scene scene = new Scene(root, 800, 600);
            final Stage stage = new Stage();
            stage.setTitle(Main.getMessageBundle().getString("project.title"));
            stage.setScene(scene);
            stage.setMaximized(true);
            _stage = stage;
            scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                final KeyCode code = event.getCode();
                view.handleKeyPressed(code);
            });
            view.addBuoys();
            return view;
        }
    }
}