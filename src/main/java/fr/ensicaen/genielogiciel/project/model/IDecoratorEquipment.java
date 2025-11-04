package fr.ensicaen.genielogiciel.project.model;

/**
 * Interface pour les modifications de performance dues à l'équipage.
 * Permet d’ajuster l’angle et la vitesse obtenus via la polaire.
 */
public interface IDecoratorEquipment {

    /**
     * Ajuste l'angle avant d'interroger la polaire.
     * @param angleDeg angle réel du bateau par rapport au vent (0–180°)
     * @return angle modifié selon les performances de l’équipage
     */
    double adjustAngle(double angleDeg);

    /**
     * Ajuste la vitesse renvoyée par la polaire selon l’allure.
     * @param speed vitesse issue de la polaire
     * @param angleDeg angle utilisé
     * @return vitesse ajustée
     */
    double adjustSpeed(double speed, double angleDeg);
}