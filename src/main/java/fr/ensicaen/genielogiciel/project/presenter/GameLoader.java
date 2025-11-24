package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;

import java.io.InputStream;

public class GameLoader {

    private final IParser _parser;

    public GameLoader( IParser parser ) {
        _parser = parser;
    }

    public IMap loadGame( String filePath ) {
        if (filePath == null) {
            filePath = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/default_configuration.json";
        }
        return _parser.loadGame(filePath);
    }


}
