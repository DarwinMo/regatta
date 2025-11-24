package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.*;

public record GameLoader(IParser _parser) {

    public IMap loadGame( String filePath ) {
        if (filePath == null) {
            filePath = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/default_configuration.json";
        }
        return _parser.loadGame(filePath);
    }


}
