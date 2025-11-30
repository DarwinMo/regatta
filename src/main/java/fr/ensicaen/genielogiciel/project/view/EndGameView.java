package fr.ensicaen.genielogiciel.project.view;

import fr.ensicaen.genielogiciel.project.Main;
import fr.ensicaen.genielogiciel.project.presenter.ConcreteReplayPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameView {

    private Stage _stage;
    private ConcreteReplayPresenter _replayPresenter;
    @FXML
    private AnchorPane _replayView;


    public void setReplayPresenter( ConcreteReplayPresenter replayPresenter ) {
        _replayPresenter = replayPresenter;
    }

    public void show() {
        _stage.show();
    }

    public void close() {
        _stage.close();
    }

    @FXML
    private void onClickStartReplay() {
        _replayPresenter.lunchReplay();
    }

    @FXML
    private void onClickStartNewGame() {
        _replayPresenter.lunchNewGame();
    }

    @FXML
    private void onClickQuit() {
        _stage.close();
    }

    @FXML
    public void initializeReplayPanel( GameView gameView ) {
        Parent gameViewRoot = gameView.getRoot();
        _replayView.getChildren().setAll(gameViewRoot);
    }

    public static class EndGameViewFactory {
        private EndGameViewFactory() {
        }

        public static EndGameView createEndGameView( GameView gameView ) throws IOException {
            final FXMLLoader loader = new FXMLLoader(EndGameView.class.getResource("ReplayView.fxml"), Main.getMessageBundle());
            final Parent root = loader.load();
            final EndGameView view = loader.getController();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root));
            view._stage = stage;
            stage.setWidth(gameView.getStage().getWidth());
            stage.setHeight(gameView.getStage().getHeight());
            stage.setResizable(false);
            view.initializeReplayPanel(gameView);
            return view;
        }

    }

}
