package fr.ensicaen.genielogiciel.project.model;

import javafx.geometry.Point2D;
import java.util.*;

public class Bouee {
    private static final double RAYON_BOUEE_DEFAUT = 10.0;
    private static final double FACTEUR_TOLERANCE_PASSAGE = 2.0;
    private static final double FACTEUR_TOLERANCE_COLLISION = 1.0;
    
    private static final double POSITION_DEPART_X = 100.0;
    private static final double POSITION_DEPART_Y = 100.0;
    private static final double POSITION_MARQUE_1_X = 300.0;
    private static final double POSITION_MARQUE_1_Y = 400.0;
    private static final double POSITION_MARQUE_2_X = 500.0;
    private static final double POSITION_MARQUE_2_Y = 200.0;
    private static final double POSITION_ARRIVEE_X = 100.0;
    private static final double POSITION_ARRIVEE_Y = 100.0;
    
    private static final double POSITION_LIGNE_DEPART_X = 100.0;
    private static final double POSITION_LIGNE_DEPART_Y = 300.0;
    private static final double POSITION_LIGNE_MARQUE_X = 400.0;
    private static final double POSITION_LIGNE_MARQUE_Y = 300.0;
    private static final double POSITION_LIGNE_ARRIVEE_X = 700.0;
    private static final double POSITION_LIGNE_ARRIVEE_Y = 300.0;
    
    private static final String NOM_CONFIGURATION_TRIANGLE = "Triangle Olympique";
    private static final String NOM_CONFIGURATION_LIGNE = "Course en Ligne";
    private static final String NOM_CONFIGURATION_DEFAUT = "Parcours par défaut";

    public enum TypeBouee {
        DEPART("Départ", "start"),
        MARQUE("Marque", "mark"),
        ARRIVEE("Arrivée", "finish");

        private final String nomAffichage;
        private final String classeStyle;

        TypeBouee(String nomAffichage, String classeStyle) {
            this.nomAffichage = nomAffichage;
            this.classeStyle = classeStyle;
        }

        public String getNomAffichage() { return nomAffichage; }
        public String getClasseStyle() { return classeStyle; }
    }

    private final String id;
    private final Point2D position;
    private final double rayon;
    private boolean passee;
    private final TypeBouee type;
    private final int ordre;

    private static final List<Bouee> boueesParcours = new ArrayList<>();
    private static final Map<String, Bouee> boueesParId = new HashMap<>();
    private static int indexBoueeCourante = 0;
    private static String nomConfiguration = NOM_CONFIGURATION_DEFAUT;

    public Bouee(String id, double x, double y, TypeBouee type, int ordre) {
        this(id, x, y, type, ordre, RAYON_BOUEE_DEFAUT);
    }

    public Bouee(String id, double x, double y, TypeBouee type, int ordre, double rayon) {
        this.id = id;
        this.position = new Point2D(x, y);
        this.type = type;
        this.ordre = ordre;
        this.rayon = rayon;
        this.passee = false;
    }

    public String getId() { return id; }
    public Point2D getPosition() { return position; }
    public double getX() { return position.getX(); }
    public double getY() { return position.getY(); }
    public double getRayon() { return rayon; }
    public TypeBouee getType() { return type; }
    public int getOrdre() { return ordre; }
    public boolean estPassee() { return passee; }

    public void setPassee(boolean passee) { this.passee = passee; }

    public boolean estEnCollision(double xBateau, double yBateau, double rayonBateau) {
        double distance = position.distance(xBateau, yBateau);
        double distanceCollision = (rayon + rayonBateau) * FACTEUR_TOLERANCE_COLLISION;
        return distance <= distanceCollision;
    }

    public boolean estPassee(double xBateau, double yBateau) {
        if (passee) return true;
        double tolerancePassage = rayon * FACTEUR_TOLERANCE_PASSAGE;
        double distance = position.distance(xBateau, yBateau);
        return distance <= tolerancePassage;
    }

    @Override
    public String toString() {
        return String.format("Bouee{id='%s', position=(%.2f, %.2f), type=%s, ordre=%d, passee=%s}",
                id, position.getX(), position.getY(), type, ordre, passee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bouee bouee = (Bouee) obj;
        return id.equals(bouee.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static void ajouterBouee(Bouee bouee) {
        boueesParcours.add(bouee);
        boueesParId.put(bouee.getId(), bouee);
        boueesParcours.sort(Comparator.comparingInt(Bouee::getOrdre));
    }

    public static Bouee getBoueeCourante() {
        if (boueesParcours.isEmpty() || indexBoueeCourante >= boueesParcours.size()) {
            return null;
        }
        return boueesParcours.get(indexBoueeCourante);
    }

    public static boolean verifierBoueePassee(double xBateau, double yBateau) {
        Bouee boueeCourante = getBoueeCourante();
        if (boueeCourante == null) return false;
        if (boueeCourante.estPassee(xBateau, yBateau)) {
            boueeCourante.setPassee(true);
            indexBoueeCourante++;
            return true;
        }
        return false;
    }

    public static Bouee verifierCollisions(double xBateau, double yBateau, double rayonBateau) {
        for (Bouee bouee : boueesParcours) {
            if (bouee.estEnCollision(xBateau, yBateau, rayonBateau)) {
                return bouee;
            }
        }
        return null;
    }

    public static void reinitialiserParcours() {
        for (Bouee bouee : boueesParcours) {
            bouee.setPassee(false);
        }
        indexBoueeCourante = 0;
    }

    public static void chargerConfigurationTriangulaire() {
        boueesParcours.clear();
        boueesParId.clear();
        indexBoueeCourante = 0;
        nomConfiguration = NOM_CONFIGURATION_TRIANGLE;
        ajouterBouee(new Bouee("depart", POSITION_DEPART_X, POSITION_DEPART_Y, TypeBouee.DEPART, 0));
        ajouterBouee(new Bouee("marque1", POSITION_MARQUE_1_X, POSITION_MARQUE_1_Y, TypeBouee.MARQUE, 1));
        ajouterBouee(new Bouee("marque2", POSITION_MARQUE_2_X, POSITION_MARQUE_2_Y, TypeBouee.MARQUE, 2));
        ajouterBouee(new Bouee("arrivee", POSITION_ARRIVEE_X, POSITION_ARRIVEE_Y, TypeBouee.ARRIVEE, 3));
    }

    public static void chargerConfigurationLigne() {
        boueesParcours.clear();
        boueesParId.clear();
        indexBoueeCourante = 0;
        nomConfiguration = NOM_CONFIGURATION_LIGNE;
        ajouterBouee(new Bouee("depart", POSITION_LIGNE_DEPART_X, POSITION_LIGNE_DEPART_Y, TypeBouee.DEPART, 0));
        ajouterBouee(new Bouee("marque1", POSITION_LIGNE_MARQUE_X, POSITION_LIGNE_MARQUE_Y, TypeBouee.MARQUE, 1));
        ajouterBouee(new Bouee("arrivee", POSITION_LIGNE_ARRIVEE_X, POSITION_LIGNE_ARRIVEE_Y, TypeBouee.ARRIVEE, 2));
    }

    public static List<Bouee> getToutesBouees() { 
        return Collections.unmodifiableList(boueesParcours); 
    }
    
    public static Bouee getBoueeParId(String id) { 
        return boueesParId.get(id); 
    }
    
    public static int getIndexBoueeCourante() { 
        return indexBoueeCourante; 
    }
    
    public static boolean isCourseTerminee() { 
        return indexBoueeCourante >= boueesParcours.size(); 
    }
    
    public static int getNombreTotalBouees() { 
        return boueesParcours.size(); 
    }
    
    public static int getNombreBoueesPassees() { 
        return indexBoueeCourante; 
    }
    
    public static String getNomConfiguration() { 
        return nomConfiguration; 
    }
    
    public static void setNomConfiguration(String nom) { 
        nomConfiguration = nom; 
    }

    public static boolean estParcoursValide() {
        boolean hasStart = boueesParcours.stream().anyMatch(b -> b.type == TypeBouee.DEPART);
        boolean hasFinish = boueesParcours.stream().anyMatch(b -> b.type == TypeBouee.ARRIVEE);
        return hasStart && hasFinish && !boueesParcours.isEmpty();
    }

    public static List<Bouee> getBoueesAVenir() {
        if (indexBoueeCourante >= boueesParcours.size()) {
            return Collections.emptyList();
        }
        return boueesParcours.subList(indexBoueeCourante, boueesParcours.size());
    }

    public static List<Bouee> getBoueesPassees() {
        if (indexBoueeCourante == 0) {
            return Collections.emptyList();
        }
        return boueesParcours.subList(0, indexBoueeCourante);
    }
    
    public static void chargerConfigurationPersonnalisee(String nom, List<Bouee> bouees) {
        boueesParcours.clear();
        boueesParId.clear();
        indexBoueeCourante = 0;
        nomConfiguration = nom;
        for (Bouee bouee : bouees) {
            ajouterBouee(bouee);
        }
    }
    
    public static void viderParcours() {
        boueesParcours.clear();
        boueesParId.clear();
        indexBoueeCourante = 0;
        nomConfiguration = NOM_CONFIGURATION_DEFAUT;
    }
}