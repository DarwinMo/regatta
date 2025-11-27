package fr.ensicaen.genielogiciel.project.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MainController {

    @FXML
    private Canvas gameCanvas;


    @FXML
    public void initialize() {
        if (gameCanvas != null) {
            GraphicsContext g = gameCanvas.getGraphicsContext2D();
            g.fillText("Plan d’eau (placeholder)", 20, 20);
        }
    }

    @FXML
    private void onQuit() {
        Platform.exit();
    }
}
