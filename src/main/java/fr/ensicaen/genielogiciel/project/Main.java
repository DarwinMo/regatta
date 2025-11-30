package fr.ensicaen.genielogiciel.project;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import fr.ensicaen.genielogiciel.project.presenter.ConfigurationPresenter;
import fr.ensicaen.genielogiciel.project.view.GameConfigurationView;
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
        final GameConfigurationView view = GameConfigurationView.GameConfigurationViewFactory.createConfigurationView();
        final ConfigurationPresenter presenter = new ConfigurationPresenter();
        presenter.setConfigurationView(view);
        view.setConfigurationPresenter(presenter);
        view.show();
    }

    @Override
    public void stop() {
        System.out.println(getMessageBundle().getString("project.bye"));
    }
}