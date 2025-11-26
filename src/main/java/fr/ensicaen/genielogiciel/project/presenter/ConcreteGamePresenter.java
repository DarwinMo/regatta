package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;
import fr.ensicaen.genielogiciel.project.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class ConcreteGamePresenter implements IPresenter {

    private IMap _map;
    private IBoat _boat;
    private final PlayerModel _player;
    private IWind _wind;

    private final BoatDirectionManager _boatDirectionManager;

    private GameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;
    private final TimeScore _timeScore = new TimeScore();

    public ConcreteGamePresenter( String nickName, String configurationFile ) throws IOException {
        _player = new PlayerModel();
        _player.setNickname(nickName);
        initGame(configurationFile);
        int _rotationAngle = 2;
        _boatDirectionManager = new BoatDirectionManager(_boat, _rotationAngle);
    }

    public void setGameView( GameView gameView ) {
        _gameView = gameView;
        _gameView.addBoat(_boat.get_x(), _boat.get_y());
        for (IBuoy buoy : _map.getBuoys()) {
            _gameView.addBuoys(buoy.get_x(), buoy.get_y());
            if (buoy instanceof ConcreteBuoy) {
                _gameView.addBuoyLines(((ConcreteBuoy)buoy).getLine());
            }
        }

        for (ICoast coast : _map.getCoasts()) {
            _gameView.addCoast(coast.getStartX(), coast.getStartY(), coast.getEndX(), coast.getEndY());
        }
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
            _timeScore.start();
            runGameLoop();
        }
    }

    private void changeDirection( UserAction action ) {
        if (action == UserAction.LEFT) {
            _boatDirectionManager.goLeft();
        } else if (action == UserAction.RIGHT) {
            _boatDirectionManager.goRight();
        }
    }

    private void initGame( String configurationFile ) {
        _map = new GameLoader(new JsonParser()).loadGame(configurationFile);
        _wind = _map.getWaterPlan().getWind();
        _boat = _map.getBoat();
    }

    private void runGameLoop() {
        _timeline = new Timeline(new KeyFrame(
                Duration.millis(50), onFinished -> {
            update();
            render();
        }
        ));
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
    }

    private void stopGameLoop() {
        if (_timeline != null) {
            _timeline.stop();
        }
        System.out.println("FIN DE PARTIE ! Temps : " + _timeScore.getFormattedCurrentTime());
    }

    private boolean loopCompleted() {
        for (IBuoy buoy : _map.getBuoys()) {
            if (!buoy.isMarked()) {
                return false;
            }
        }
        return true;
    }

    private void update() {
        _boat.set_windSpeed(_wind.getWindSpeed());
        _boat.set_windAngle(_wind.getWindDirection());
        _boat.accelerate();
        _boat.applyWind();

        for (IBuoy buoy : _map.getBuoys()) {
            if (buoy.checkPassage(_boat)) {
                System.out.println("PASSAGE VALIDÉ");
            }
            if (buoy.checkCollision(_boat)) {
                System.out.println("Collision avec la bouée !");
                _boat.set_speed(0);
            }
        }
        if (loopCompleted()) {
            stopGameLoop();
        }
    }
    private void render() {
        _gameView.updateBoat(_boat.get_dx(), _boat.get_dy(), _boat.get_angle());
        _gameView.updateDashboard(_wind.getWindDirection(), _boat.get_angle(), _boat.get_speed(), _timeScore.getFormattedCurrentTime());
    }
}