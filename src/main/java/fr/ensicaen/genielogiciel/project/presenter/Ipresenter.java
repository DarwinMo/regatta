package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.view.IGameView;

public interface Ipresenter {
    public void attach( IGameView gameView );

    public void handleUserEvent( UserAction action );
}
