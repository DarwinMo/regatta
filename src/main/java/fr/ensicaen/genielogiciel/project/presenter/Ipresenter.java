package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.view.IGameView;

public interface Ipresenter {
    public void handleUserAction( UserAction code );
}
