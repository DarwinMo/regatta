package fr.ensicaen.genielogiciel.project.presenter;

import java.io.IOException;

import fr.ensicaen.genielogiciel.project.model.*;
import fr.ensicaen.genielogiciel.project.view.EndGameView;
import fr.ensicaen.genielogiciel.project.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ConcreteGamePresenter implements IPresenter {

    private IMap _map;
    private IBoat _boat;
    private IWind _wind;

    private BoatDirectionManager _boatDirectionManager;

    private GameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;
    private final TimeScore _timeScore = new TimeScore();
    private int _currentBuoyIndex = 0;
    private final double _rotationAngle = 5.0;


    public ConcreteGamePresenter( String configurationFile ) throws IOException {
        initGame(configurationFile);
        routineGameInit();
    }

    public ConcreteGamePresenter( SailType sailType, CrewType crewType, BoatType boatType ) throws IOException {
        initGame(sailType, crewType, boatType);
        routineGameInit();
    }

    private void routineGameInit() {
        _boatDirectionManager = new BoatDirectionManager(_rotationAngle);
        _boatDirectionManager.setBoat(_boat);
    }

    public void launchEndGame() {
        try {
            final EndGameView view = EndGameView.EndGameViewFactory.createEndGameView(_gameView);
            final ConcreteReplayPresenter replayPresenter = new ConcreteReplayPresenter(_rotationAngle, _map, _gameView);
            view.setReplayPresenter(replayPresenter);
            replayPresenter.setView(view);
            view.show();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        _gameView.close();
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
                IBuoy firstBuoy = _map.getBuoys().get(_currentBuoyIndex);
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

    public void startGame() {
        if (!_started) {
            _started = true;
            _timeScore.start();
            runGameLoop();
        }
    }

    private void changeDirection( UserAction action ) {
        if (_boatDirectionManager != null) {
            long currentTime = _timeScore.getCurrentTime();

            if (action == UserAction.LEFT) {
                _boatDirectionManager.goLeft(currentTime);
            } else if (action == UserAction.RIGHT) {
                _boatDirectionManager.goRight(currentTime);
            }
        }
    }

    private void initGame( String configurationFile ) {
        _map = new GameLoader(new JsonParser()).loadGame(configurationFile);
        if (_map != null) {
            _wind = _map.getWind();
            _boat = _map.getBoat();
        }
    }

    private void initGame( SailType sailType, CrewType crewType, BoatType boatType ) throws IOException {
        _map = new GameLoader(new JsonParser()).loadGame(null);
        _wind = _map.getWind();
        _boat = BoatType.getBoatFactory(boatType, sailType, crewType).createBoat(600, 600, _wind.getWindSpeed(), _wind.getWindDirection());
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
        _timeScore.finish();
        _boatDirectionManager.saveGame();
    }

    private void boatHasCollided() {
        for (ICoast coast : _map.getCoasts()) {
            if (coast.isCollision(_boat)) {
                _boat.setInCollision(true);
                return;
            }
        }
        for (IBuoy buoy : _map.getBuoys()) {
            if (buoy.checkCollision(_boat)) {
                _boat.setInCollision(true);
                return;
            }
        }
    }

    private boolean gameHasEnded() {
        for (IBuoy buoy : _map.getBuoys()) {
            if (buoy.isMarked()) continue;
            buoy.checkPassage(_boat);
            if (!buoy.isMarked()) {
                return false;
            }
            _gameView.removeCurrentBuoyLine();
            _currentBuoyIndex++;
            if (_currentBuoyIndex >= _map.getBuoys().size()) break;
            IBuoy nextBuoy = _map.getBuoys().get(_currentBuoyIndex);
            _gameView.addBuoyLines(nextBuoy.getStartLineX(), nextBuoy.getStartLineY(), nextBuoy.getEndLineY(), nextBuoy.getEndLineX());
        }
        return true;
    }


    private void update() {
        if (_boat == null || _wind == null) return;
        boatHasCollided();
        _boatDirectionManager.goStraight(_timeScore.getCurrentTime());

        if (gameHasEnded()) {
            _started = false;
            stopGameLoop();
            launchEndGame();
        }
    }

    private void render() {
        if (_gameView != null && _boat != null) {
            _gameView.updateBoat(_boat.get_dx(), _boat.get_dy(), _boat.get_angle());
            _gameView.updateDashboard(_wind.getWindDirection(), _boat.get_angle(), _boat.get_speed(), _timeScore.getFormattedCurrentTime());
        }
    }
}
