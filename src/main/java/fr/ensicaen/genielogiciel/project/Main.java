package fr.ensicaen.genielogiciel.project;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import fr.ensicaen.genielogiciel.project.presenter.LoginPresenter;
import fr.ensicaen.genielogiciel.project.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Main extends Application {

    public static void main( String[] args ) {
        launch(args);
    }

    private static volatile ResourceBundle BUNDLE;

    public static ResourceBundle getMessageBundle() {
        ResourceBundle local = BUNDLE;
        if (local == null) {
            synchronized (Main.class) {
                local = BUNDLE;
                if (local == null) {
                    try {
                        BUNDLE = local = ResourceBundle.getBundle(
                                "fr.ensicaen.genielogiciel.project.MessageBundle", Locale.getDefault()
                        );
                    } catch (MissingResourceException e) {
                        // Fallback si la locale système ne correspond pas à un bundle présent
                        BUNDLE = local = ResourceBundle.getBundle(
                                "fr.ensicaen.genielogiciel.project.MessageBundle", Locale.FRANCE
                        );
                    }
                }
            }
        }
        return local;
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