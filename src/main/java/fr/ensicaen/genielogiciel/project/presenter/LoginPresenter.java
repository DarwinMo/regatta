package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.model.DoubleCrew;
import fr.ensicaen.genielogiciel.project.model.LargeSail;
import fr.ensicaen.genielogiciel.project.view.GameView;
import fr.ensicaen.genielogiciel.project.view.LoginView;

import java.io.IOException;

public final class LoginPresenter {
    private ILoginView _loginView;

    public void setLoginView( LoginView loginView ) {
        _loginView = loginView;
    }

    public void launchGame( String nickName ) {
        if (nickName.isEmpty()) {
            _loginView.displayError(Main.getMessageBundle().getString("error.nickname"));
        } else {
            try {
                final GameView view = GameView.GameViewFactory.createView();
                final ConcreteGamePresenter gamePresenter = new ConcreteGamePresenter(nickName, CrewType.DOUBLE, SailType.LARGE);
                view.setGamePresenter(gamePresenter);
                gamePresenter.setGameView(view);
                view.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _loginView.close();
        }
    }
}
