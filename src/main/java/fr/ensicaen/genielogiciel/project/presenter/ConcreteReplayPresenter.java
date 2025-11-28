package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.view.EndGameView;
import fr.ensicaen.genielogiciel.project.view.GameConfigurationView;

import java.io.IOException;

public class ConcreteReplayPresenter {

    EndGameView _endGameView;

    public void setView( final EndGameView view ) {
        _endGameView = view;
    }

    public void lunchNewGame() {
        final GameConfigurationView view;
        try {
            view = GameConfigurationView.GameConfigurationViewFactory.createConfigurationView();
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
