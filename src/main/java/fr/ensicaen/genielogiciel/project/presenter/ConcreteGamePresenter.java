package fr.ensicaen.genielogiciel.project.presenter;

import java.io.IOException;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import fr.ensicaen.genielogiciel.project.model.IBuoy;
import fr.ensicaen.genielogiciel.project.model.ICoast;
import fr.ensicaen.genielogiciel.project.model.IMap;
import fr.ensicaen.genielogiciel.project.model.IWind;
import fr.ensicaen.genielogiciel.project.model.PlayerModel;
import fr.ensicaen.genielogiciel.project.model.TimeScore;
import fr.ensicaen.genielogiciel.project.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ConcreteGamePresenter implements IPresenter {

    private IMap _map;
    private IBoat _boat;
    private final PlayerModel _player;
    private IWind _wind;

    private BoatDirectionManager _boatDirectionManager;

    private GameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;
    private final TimeScore _timeScore = new TimeScore();
    private int _currentBuoyIndex = 0;

    public ConcreteGamePresenter( String nickName, String configurationFile ) throws IOException {
        _player = new PlayerModel();
        _player.setNickname(nickName);
        initGame(configurationFile);
        int _rotationAngle = 2;
        _boatDirectionManager = new BoatDirectionManager(_boat, _rotationAngle);
    }

    public void setBoat( IBoat boat ) {
        this._boat = boat;
        int _rotationAngle = 2;
        this._boatDirectionManager = new BoatDirectionManager(_boat, _rotationAngle);
        System.out.println("Presenter : Bateau mis à jour -> " + boat);
    }

    public void setGameView( GameView gameView ) {
        _gameView = gameView;

        if (_boat != null) {
            _gameView.addBoat(_boat.get_x(), _boat.get_y());
        }

        if (_map != null) {
            for (IBuoy buoy : _map.getBuoys()) {
                _gameView.addBuoys(buoy.get_x(), buoy.get_y());
            }

            if (!_map.getBuoys().isEmpty()) {
                IBuoy firstBuoy = _map.getBuoys().get(_currentBuoyIndex); // Utilise l'index 0
                _gameView.addBuoyLines(firstBuoy.getStartLineX(), firstBuoy.getStartLineY(), firstBuoy.getEndLineY(), firstBuoy.getEndLineX());
            }

            for (ICoast coast : _map.getCoasts()) {
                _gameView.addCoast(coast.startX(), coast.startY(), coast.endX(), coast.endY());
            }
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
        if (_boatDirectionManager != null) {
            if (action == UserAction.LEFT) {
                _boatDirectionManager.goLeft();
            } else if (action == UserAction.RIGHT) {
                _boatDirectionManager.goRight();
            }
        }
    }

    private void initGame( String configurationFile ) {
        _map = new GameLoader(new JsonParser()).loadGame(configurationFile);
        if (_map != null) {
            _wind = _map.getWaterPlan().getWind();
            _boat = _map.getBoat();
        }
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

    public void stopGameLoop() {
        if (_timeline != null) {
            _timeline.stop();
        }
        System.out.println("FIN DE PARTIE ! Temps : " + _timeScore.getFormattedCurrentTime());
    }

    private void update() {
        if (_boat == null || _wind == null) return;

        _boat.set_windSpeed(_wind.getWindSpeed());
        _boat.set_windAngle(_wind.getWindDirection());
        _boat.accelerate();
        _boat.applyWind();

        if (_map != null && _currentBuoyIndex < _map.getBuoys().size()) {
            IBuoy currentBuoy = _map.getBuoys().get(_currentBuoyIndex);
            if (currentBuoy.checkPassage(_boat)) {
                System.out.println("PASSAGE VALIDÉ POUR BOUEE " + _currentBuoyIndex);
                _currentBuoyIndex++;

                if (_currentBuoyIndex >= _map.getBuoys().size()) {
                    System.out.println("PARCOURS TERMINÉ !");
                    stopGameLoop();
                    _gameView.removeCurrentBuoyLine();
                } else {
                    IBuoy nextBuoy = _map.getBuoys().get(_currentBuoyIndex);
                    _gameView.addBuoyLines(nextBuoy.getStartLineX(), nextBuoy.getStartLineY(), nextBuoy.getEndLineY(), nextBuoy.getEndLineX());
                }
            }
        }

        if (_map != null) {
            for (ICoast coast : _map.getCoasts()) {
                if (coast.isCollision(_boat)) {
                    _boat.set_speed(0);
                    break;
                }
            }
            for (IBuoy buoy : _map.getBuoys()) {
                if (buoy.checkCollision(_boat)) {
                    _boat.set_speed(0);
                    break;
                }
            }
        }
    }

    private void render() {
        if (_gameView != null && _boat != null) {
            _gameView.updateBoat(_boat.get_dx(), _boat.get_dy(), _boat.get_angle());
            _gameView.updateDashboard(_wind.getWindDirection(), _boat.get_angle(), _boat.get_speed(), _timeScore.getFormattedCurrentTime());
        }
    }
}
