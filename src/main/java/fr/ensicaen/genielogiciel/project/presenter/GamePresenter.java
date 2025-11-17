package fr.ensicaen.genielogiciel.project.presenter;


import fr.ensicaen.genielogiciel.project.model.FigaroFactory;
import fr.ensicaen.genielogiciel.project.model.IBoat;
import fr.ensicaen.genielogiciel.project.model.MediumSail;
import fr.ensicaen.genielogiciel.project.model.PlayerModel;
import fr.ensicaen.genielogiciel.project.model.SingleCrew;
import fr.ensicaen.genielogiciel.project.model.WeatherAPI;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

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

        try {
            initGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void initGame() throws IOException {
        _boatModel = new FigaroFactory(new MediumSail(), new SingleCrew()).createFigaro();
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
        System.out.println("\nWind speed=" + _windSpeed + " | wind direction=" + _windDirection);
        _boatModel.accelerate();
        _boatModel.applyWind();
    }

    private void render() {
        _gameView.update(_boatModel.get_dx(), _boatModel.get_dy(), _boatModel.get_angle());
    }
}
