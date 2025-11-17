package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class ConcreteGamePresenter implements Ipresenter {

    IMap _map;
    IBoat _boat;
    PlayerModel _player;
    IWind _wind;
    private IGameView _gameView;
    private boolean _started = false;
    private Timeline _timeline;

    public ConcreteGamePresenter( String nickName ) {

        _player = new PlayerModel();
        _player.setNickname(nickName);
        _wind = new ConcreteWind(new WeatherAPI());

        try {
            initGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGameView( IGameView gameView ) {
        _gameView = gameView;
        _gameView.addBoat(_boat.get_x(), _boat.get_y());
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
            _boat.rotate(-2);
        } else if (action == UserAction.RIGHT) {
            _boat.rotate(+2);
        }
    }

    private void initGame(CrewType crewType,SailType sailType) throws IOException {
        _boat = new FigaroFactory(_getDecoratorSail(sailType), _getDecoratorEquipment(crewType)).createFigaro();
        
    }

    private IDecoratorSail _getDecoratorSail(SailType sailType) {
        if(sailType == SailType.MEDIUM){
            return new MediumSail();
        }
        return new LargeSail();
    }
    private IDecoratorEquipment _getDecoratorEquipment(CrewType crewType) {
        if(crewType == CrewType.SINGLE){
            return new SingleCrew();
        }
        return new DoubleCrew();
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
        _boat.set_windSpeed(_wind.getWindSpeed());
        _boat.set_windAngle(_wind.getWindDirection());
        _boat.accelerate();
        _boat.applyWind();
    }

    private void render() {
        _gameView.update(_boat.get_dx(), _boat.get_dy(), _boat.get_angle());
    }

    @Override
    public void attach( IGameView gameView ) {

    }

    @Override
    public void handleUserEvent( UserAction action ) {

    }
}