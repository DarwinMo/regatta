package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IMap;

public class GameLoader {

    private IParser _parser;

    public GameLoader( IParser parser ) {
        _parser = parser;
    }

    public IMap loadGame( String filePath ) {
        return _parser.loadGame(filePath);
    }


}
