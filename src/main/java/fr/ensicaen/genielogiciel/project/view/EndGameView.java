package fr.ensicaen.genielogiciel.project.view;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteReplayPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {

    private Stage _stage;
    private ConcreteReplayPresenter _replayPresenter;


    public void setReplayPresenter( ConcreteReplayPresenter replayPresenter ) {
        _replayPresenter = replayPresenter;
    }

    public void show() {
        _stage.show();
    }

    public void close() {
        _stage.close();
    }

    public void displayError( String message ) {
    }


    @FXML
    private void onClickStartNewGame() throws IOException {
        _replayPresenter.lunchNewGame();
    }

    @FXML
    private void onClickPause() {
        // to implement
    }

    @FXML
    private void onClickQuit() {
        _stage.close();
    }

    public static class EndGameViewFactory {
        private EndGameViewFactory() {
        }

        public static EndGameView createEndGameView() throws IOException {
            final FXMLLoader loader = new FXMLLoader(EndGameView.class.getResource("ReplayView.fxml"), Main.getMessageBundle());
            final Parent root = loader.load();
            final EndGameView view = loader.getController();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root));
            view._stage = stage;
            return view;
        }

    }
}
