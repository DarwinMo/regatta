package fr.ensicaen.genielogiciel.project.model;

public interface IWaterPlan {
    boolean isInside(double x, double y);
    Bounds getBounds();
    IWind getWind();
}
