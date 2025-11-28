package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.view.GameConfigurationView;
import fr.ensicaen.genielogiciel.project.view.GameView;

import java.io.IOException;

public class ConfigurationPresenter {
    private GameConfigurationView _configurationView;

    public void setConfigurationView( GameConfigurationView configurationView ) {
        _configurationView = configurationView;
    }

    public void launchGameFromFile( String fileName ) {
        if (fileName == null) {
            _configurationView.displayError("error");
        } else {
            try {
                final GameView view = GameView.GameViewFactory.createView();
                final ConcreteGamePresenter gamePresenter = new ConcreteGamePresenter(fileName);
                view.setGamePresenter(gamePresenter);
                gamePresenter.setGameView(view);
                view.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void launchGame( SailType sailType, CrewType crewType, BoatType boatType ) {

        if (sailType == null || crewType == null || boatType == null) {
            //_loginView.displayError(Main.getMessageBundle().getString("error.nickname"));
            _configurationView.displayError("error");
        } else {
            try {
                final GameView view = GameView.GameViewFactory.createView();
                final ConcreteGamePresenter gamePresenter = new ConcreteGamePresenter(sailType, crewType, boatType);
                view.setGamePresenter(gamePresenter);
                gamePresenter.setGameView(view);
                view.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _configurationView.close();
    }
}
