package fr.ensicaen.genielogiciel.project;

import fr.ensicaen.genielogiciel.project.presenter.LoginPresenter;
import fr.ensicaen.genielogiciel.project.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public final class Main extends Application {

    public static void main( String[] args ) {
        launch(args);
    }

    public static ResourceBundle getMessageBundle() {
        return ResourceBundle.getBundle("fr.ensicaen.genielogiciel.project.MessageBundle");
    }

    @Override
    public void start( final Stage primaryStage ) throws Exception {
        primaryStage.setTitle(getMessageBundle().getString("project.title"));
        final LoginView view = LoginView.LoginViewFactory.createView(primaryStage);
        final LoginPresenter presenter = new LoginPresenter();
        presenter.setLoginView(view);
        view.setLoginPresenter(presenter);
        view.show();
    }

    @Override
    public void stop() {
        System.out.println(getMessageBundle().getString("project.bye"));
    }
}