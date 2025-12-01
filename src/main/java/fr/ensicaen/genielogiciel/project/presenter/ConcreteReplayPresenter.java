package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;
import fr.ensicaen.genielogiciel.project.view.EndGameView;
import fr.ensicaen.genielogiciel.project.view.GameConfigurationView;
import fr.ensicaen.genielogiciel.project.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class ConcreteReplayPresenter {

    private EndGameView _endGameView;
    private final GameView _gameView;
    private final IMap _map;
    private IBoat _boat;
    private IWind _wind;
    private final BoatDirectionManager _boatDirectionManager;
    private Timeline _timeline;
    private final TimeScore _timeScore;
    private int _currentMoveIdx;


    ConcreteReplayPresenter( Double rotationAngle, IMap map, GameView gameView ) {
        gameView.setGamePresenter(null);
        _map = map;
        _boatDirectionManager = new BoatDirectionManager(rotationAngle);
        initReplay();
        _gameView = gameView;
        _currentMoveIdx = 0;
        _timeScore = new TimeScore();
    }

    public void setView( final EndGameView view ) {
        _endGameView = view;
    }

    private void initReplay() {
        _boat = _map.getBoat();
        _boat.setX(_boat.getStartX());
        _boat.setY(_boat.getStartY());
        _wind = _map.getWind();
        for (IBuoy buoy : _map.getBuoys()) {
            buoy.setMarked(false);
        }
        _boatDirectionManager.loadGame();
    }

    private void update() {
        if (_currentMoveIdx >= _boatDirectionManager.getBoatPositionsHistory().size()) {
            _timeline.stop();
            _timeScore.finish();
            return;
        }
        double x = _boatDirectionManager.getBoatPositionsHistory().get(_currentMoveIdx).getX();
        double y = _boatDirectionManager.getBoatPositionsHistory().get(_currentMoveIdx).getY();
        double angle = _boatDirectionManager.getBoatPositionsHistory().get(_currentMoveIdx).getZ();
        _boat.setX(x);
        _boat.setY(y);
        _boat.set_angle((int) angle);
        _currentMoveIdx++;
    }

    private void render() {
        if (_gameView != null && _boat != null) {
            _gameView.updateBoatCenter(_boat.get_x(), _boat.get_y(), _boat.get_angle());
            _gameView.updateDashboard(_wind.getWindDirection(), _boat.get_angle(), _boat.get_speed(), _timeScore.getFormattedCurrentTime());
        }
    }

    public void lunchReplay() {
        _timeline = new Timeline();
        _timeline = new Timeline(new KeyFrame(
                Duration.millis(50), onFinished -> {
            update();
            render();
        }
        ));
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
        _timeScore.start();
    }


    public void lunchNewGame() {
        try {
            final GameConfigurationView view = GameConfigurationView.GameConfigurationViewFactory.createConfigurationView();
            final ConfigurationPresenter presenter = new ConfigurationPresenter();
            presenter.setConfigurationView(view);
            view.setConfigurationPresenter(presenter);


            view.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        _endGameView.close();
    }

}
