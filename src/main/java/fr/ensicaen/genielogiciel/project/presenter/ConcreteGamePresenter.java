package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.ConcreteGame;
import fr.ensicaen.genielogiciel.project.model.IGame;

public class ConcreteGamePresenter implements Ipresenter {
    private IGame _game;
    private boolean running = false;
    private IGameView _gameView;

    public ConcreteGamePresenter() {

    }

    public void initGame() {
        IGame _game = new ConcreteGame();
    }

    public void startGame(){
        running = true;
    }

    public void attach(IGameView gameView) {
        _gameView = gameView;
    }

    public void handleUserEvent(UserAction action){
        if (action == UserAction.LEFT) {
            _game.getBoat().rotate(-2);
        } else if (action == UserAction.RIGHT) {
            _game.getBoat().rotate(+2);
        }
    }

}
