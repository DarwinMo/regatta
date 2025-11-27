package fr.ensicaen.genielogiciel.project.presenter;

import java.io.IOException;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.view.BoatSelectionController;
import fr.ensicaen.genielogiciel.project.view.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fr/ensicaen/genielogiciel/project/view/BoatSelection.fxml"));
                Parent root = loader.load();
                BoatSelectionController controller = loader.getController();
                LoginView view = (LoginView) _loginView;
                controller.setStage(view.getStage());
                Stage stage = view.getStage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //_loginView.close();
        }
    }

}
