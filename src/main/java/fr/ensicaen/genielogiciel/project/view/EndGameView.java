package fr.ensicaen.genielogiciel.project.view;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteReplayPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {

    private Stage _stage;
    private ConcreteReplayPresenter _replayPresenter;

    @FXML
    private Button _newGameButton;
    @FXML
    private Button _pauseButton;
    @FXML
    private Button _quitButton;
// TODO: UNDER CONSTRUCTION

//    public void setReplayPresenter( ConcreteReplayPresenter replayPresenter ) {
//        _replayPresenter = replayPresenter;
//    }
//
//    public void setGamePresenter(  loginPresenter ) {
//    }
//
//    public void show() {
//        _stage.show();
//    }
//
//    public void close() {
//        _stage.close();
//    }
//
//    public void displayError( String message ) {
//    }
//
//
//    @FXML
//    private void onClickStartNewGame() {
//        _loginPresenter.launchGame("test", null);
//    }
//
//    @FXML
//    private void onClickPause() {
//        // to implement
//    }
//
//    @FXML
//    private void onClickQuit() {
//        _stage.close();
//    }
//
//    public static class LoginViewFactory {
//        private LoginViewFactory() {
//        }
//
//        public static EndGameView createEndGameView( Stage primaryStage ) throws IOException {
//            final FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("ReplayView.fxml"), Main.getMessageBundle());
//            final Parent root = loader.load();
//            final EndGameView view = loader.getController();
//            final Scene scene = new Scene(root);
//            view._stage = primaryStage;
//            primaryStage.setScene(scene);
//            return view;
//        }
//
//    }
}
