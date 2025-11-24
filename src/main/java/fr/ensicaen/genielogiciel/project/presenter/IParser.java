package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IMap;

public interface IParser {
    IMap loadGame( String filePath );
}
