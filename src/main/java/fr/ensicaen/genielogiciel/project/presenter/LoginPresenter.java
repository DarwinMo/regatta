package fr.ensicaen.genielogiciel.project.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.view.GameView;
import fr.ensicaen.genielogiciel.project.view.LoginView;
import fr.ensicaen.genielogiciel.project.view.BoatSelectionController;

import java.io.IOException;

public final class LoginPresenter {
    private ILoginView _loginView;

    public void setLoginView( LoginView loginView ) {
        _loginView = loginView;
    }

    public void launchGame( String nickName, String configurationFile ) {
        if (nickName.isEmpty()) {
            _loginView.displayError(Main.getMessageBundle().getString("error.nickname"));
        } else {
            try {
                // FXMLLoader loader = new FXMLLoader(getClass().getResource(
                //         "/fr/ensicaen/genielogiciel/project/view/BoatSelection.fxml"));
                // Parent root = loader.load();
                // BoatSelectionController controller = loader.getController();
                // LoginView view = (LoginView) _loginView;
                // controller.setStage(view.getStage());
                // Stage stage = view.getStage();
                // stage.setScene(new Scene(root));
                // stage.show();
                
                final GameView view = GameView.GameViewFactory.createView();
                final ConcreteGamePresenter gamePresenter = new ConcreteGamePresenter(nickName, configurationFile);
                view.setGamePresenter(gamePresenter);
                gamePresenter.setGameView(view);
                view.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
