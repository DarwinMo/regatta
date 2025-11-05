package fr.ensicaen.genielogiciel.project.presenter;

// Remarque : l'animation n'est pas considérée comme étant du graphisme à proprement parlé.
//            On peut la considérer comme une bibliothèque tiers de gestion de threading.
//            On peut donc l'utiliser dans le presenter.
import fr.ensicaen.genielogiciel.project.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public final class GamePresenter {
    private final PlayerModel _playerModel;
    private IBoat _boatModel;
    private IGameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;
    private double _windSpeed;
    private double _windDirection;

    public GamePresenter( String nickName ) {
        _playerModel = new PlayerModel();
        _playerModel.setNickname(nickName);
        WeatherAPI weatherAPI = new WeatherAPI();
        _windSpeed = weatherAPI.getWindSpeed();
        _windDirection = weatherAPI.getWindDirection();

        initGame();
    }

    public void setGameView( IGameView gameView ) {
        _gameView = gameView;
        _gameView.addBoat(_boatModel.get_x(), _boatModel.get_y());
    }

    public void handleUserAction( UserAction code ) {
        if (code == UserAction.START) {
            startGame();
        } else {
            changeDirection(code);
        }
    }

    private void startGame() {
        if (!_started) {
            _started = true;
            runGameLoop();
        }
    }

    private void changeDirection( UserAction action ) {
        if (action == UserAction.LEFT) {
            _boatModel.rotate(-2);
        } else if (action == UserAction.RIGHT) {
            _boatModel.rotate(+2);
        }
    }

    private void initGame() {
        _boatModel = new Figaro(new LargeSail(),new SingleCrew());
    }

    private void runGameLoop() {
        _timeline = new Timeline(new KeyFrame(Duration.millis(50), onFinished -> {
            update();
            render();
        }));
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
    }

    private void update() {
        _boatModel.set_windSpeed(_windSpeed);
        _boatModel.set_windAngle(_windDirection);
        _boatModel.accelerate();
        _boatModel.applyWind();
    }

    private void render() {
        _gameView.update(_boatModel.get_dx(), _boatModel.get_dy(), _boatModel.get_angle());
    }
}
