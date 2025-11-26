package fr.ensicaen.genielogiciel.project.presenter;

import javafx.stage.Stage;

public interface ILoginView {
    void displayError( String string );

    void close();

    Stage getStage();
}
