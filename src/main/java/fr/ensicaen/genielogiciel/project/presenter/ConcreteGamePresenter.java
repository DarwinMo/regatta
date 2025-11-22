package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;
import fr.ensicaen.genielogiciel.project.view.GameView;
import fr.ensicaen.genielogiciel.project.view.IGameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class ConcreteGamePresenter implements Ipresenter {

    private IMap _map;
    private IMapBuilder _mapBuilder;
    private IBoat _boat;
    private final PlayerModel _player;
    private IWind _wind;


    private final BoatDirectionManager _boatDirectionManager;

    private IGameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;
    private final TimeScore _timeScore = new TimeScore();

    public ConcreteGamePresenter( String nickName, CrewType crewType, SailType sailType ) throws IOException {
        _player = new PlayerModel();
        _player.setNickname(nickName);
        try {
            initGame(crewType, sailType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int _rotationAngle = 2;
        _boatDirectionManager = new BoatDirectionManager(_boat, _rotationAngle);
    }

    public void setGameView( GameView gameView ) {
        _gameView = gameView;
        _gameView.addBoat(_boat.get_x(), _boat.get_y());
        for (IBuoy buoy : _map.getBuoys()) {
            _gameView.addBuoys(buoy.getX(), buoy.getY());
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

    private void initGame( CrewType crewType, SailType sailType ) throws IOException {
        _boat = new FigaroFactory(_getDecoratorSail(sailType), _getDecoratorEquipment(crewType)).createBoat();
        // temporary this needs to be replaced by a file configuration and default map with rand
        _wind = new ConcreteWind(new WeatherAPI());
        ConcreteWaterPlan plan = new ConcreteWaterPlan(1000, 800, _wind);
        ConcreteCoast coast = new ConcreteCoast(1, 2, 3, 4);
        ConcreteBuoy buoy1 = new ConcreteBuoy(30, 30);
        ConcreteBuoy buoy2 = new ConcreteBuoy(300, 120);
        ConcreteBuoy buoy3 = new ConcreteBuoy(600, 400);
        _map = new ClassicMapBuilder().addWaterPlan(plan).addCoast(coast).addBuoy(buoy1).addBuoy(buoy2).addBuoy(buoy3).build();
    }

    private IDecoratorSail _getDecoratorSail( SailType sailType ) {
        if (sailType == SailType.MEDIUM) {
            return new MediumSail();
        }
        return new LargeSail();
    }

    private IDecoratorEquipment _getDecoratorEquipment( CrewType crewType ) {
        if (crewType == CrewType.SINGLE) {
            return new SingleCrew();
        }
        return new DoubleCrew();
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
    }

    private boolean loopCompleted() {
        for (IBuoy buoy : _map.getBuoys()) {
            if (!buoy.isMarked()) {
                return false;
            }
        }
        return _boat.hasCrossedFinishLine();
    }

    private void update() {
        _boat.set_windSpeed(_wind.getWindSpeed());
        _boat.set_windAngle(_wind.getWindDirection());
        _boat.accelerate();
        _boat.applyWind();
        if (loopCompleted()) {
            stopGameLoop();
        }
    }

    private void render() {
        _gameView.update(_boat.get_dx(), _boat.get_dy(), _boat.get_angle());
        String liveTime = _timeScore.getFormattedCurrentTime();
        _gameView.updateDashboard(_wind.getWindDirection(), _boat.get_angle(), _boat.get_speed(), liveTime);
    }

}