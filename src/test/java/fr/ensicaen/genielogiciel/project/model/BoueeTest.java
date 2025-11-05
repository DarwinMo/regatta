package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoueeTest {
    private Bouee boueeMarque;
    private Bouee boueeDepart;
    private Bouee boueeArrivee;

    @BeforeEach
    void setUp() {
        Bouee.viderParcours();
        boueeMarque = new Bouee("test1", 100.0, 100.0, Bouee.TypeBouee.MARQUE, 1, 10.0);
        boueeDepart = new Bouee("depart", 0.0, 0.0, Bouee.TypeBouee.DEPART, 0);
        boueeArrivee = new Bouee("arrivee", 200.0, 200.0, Bouee.TypeBouee.ARRIVEE, 3);
    }

    @Test
    void testCreationBoueeAvecRayonDefaut() {
        Bouee boueeDefaut = new Bouee("defaut", 50.0, 50.0, Bouee.TypeBouee.MARQUE, 2);
        assertEquals(10.0, boueeDefaut.getRayon());
    }

    @Test
    void testCreationBoueeAvecRayonPersonnalise() {
        assertEquals(10.0, boueeMarque.getRayon());
        assertEquals(100.0, boueeMarque.getX());
        assertEquals(100.0, boueeMarque.getY());
        assertEquals(Bouee.TypeBouee.MARQUE, boueeMarque.getType());
        assertEquals(1, boueeMarque.getOrdre());
        assertFalse(boueeMarque.estPassee());
    }

    @Test
    void testTypesBouee() {
        assertEquals("Départ", boueeDepart.getType().getNomAffichage());
        assertEquals("Arrivée", boueeArrivee.getType().getNomAffichage());
        assertEquals("start", boueeDepart.getType().getClasseStyle());
        assertEquals("finish", boueeArrivee.getType().getClasseStyle());
    }

    @Test
    void testDetectionCollision() {
        assertTrue(boueeMarque.estEnCollision(105.0, 105.0, 5.0));
        assertFalse(boueeMarque.estEnCollision(200.0, 200.0, 5.0));
    }

    @Test
    void testBoueePassee() {
        assertTrue(boueeMarque.estPassee(105.0, 105.0));
        assertFalse(boueeMarque.estPassee(150.0, 150.0));
        boueeMarque.setPassee(true);
        assertTrue(boueeMarque.estPassee(150.0, 150.0));
    }

    @Test
    void testSetPassee() {
        boueeMarque.setPassee(true);
        assertTrue(boueeMarque.estPassee());
        boueeMarque.setPassee(false);
        assertFalse(boueeMarque.estPassee());
    }

    @Test
    void testGestionParcoursVide() {
        assertNull(Bouee.getBoueeCourante());
        assertTrue(Bouee.isCourseTerminee());
        assertEquals(0, Bouee.getNombreTotalBouees());
        assertEquals(0, Bouee.getNombreBoueesPassees());
        assertNull(Bouee.verifierCollisions(0.0, 0.0, 5.0));
        assertFalse(Bouee.verifierBoueePassee(0.0, 0.0));
    }

    @Test
    void testAjouterBoueeAuParcours() {
        Bouee.ajouterBouee(boueeDepart);
        Bouee.ajouterBouee(boueeMarque);
        Bouee.ajouterBouee(boueeArrivee);
        assertEquals(3, Bouee.getNombreTotalBouees());
        assertEquals(boueeDepart, Bouee.getBoueeCourante());
        assertFalse(Bouee.isCourseTerminee());
    }

    @Test
    void testVerifierBoueePasseeAvecParcours() {
        Bouee.ajouterBouee(boueeDepart);
        Bouee.ajouterBouee(boueeMarque);
        boolean passee = Bouee.verifierBoueePassee(5.0, 5.0);
        assertTrue(passee);
        assertTrue(boueeDepart.estPassee());
        assertEquals(boueeMarque, Bouee.getBoueeCourante());
        assertEquals(1, Bouee.getNombreBoueesPassees());
    }

    @Test
    void testVerifierCollisionsAvecParcours() {
        Bouee.ajouterBouee(boueeDepart);
        Bouee.ajouterBouee(boueeMarque);
        Bouee collision = Bouee.verifierCollisions(5.0, 5.0, 5.0);
        assertEquals(boueeDepart, collision);
        collision = Bouee.verifierCollisions(200.0, 200.0, 5.0);
        assertNull(collision);
    }

    @Test
    void testConfigurationTriangulaire() {
        Bouee.chargerConfigurationTriangulaire();
        assertEquals("Triangle Olympique", Bouee.getNomConfiguration());
        assertEquals(4, Bouee.getNombreTotalBouees());
        assertTrue(Bouee.estParcoursValide());
    }

    @Test
    void testConfigurationLigne() {
        Bouee.chargerConfigurationLigne();
        assertEquals("Course en Ligne", Bouee.getNomConfiguration());
        assertEquals(3, Bouee.getNombreTotalBouees());
        assertTrue(Bouee.estParcoursValide());
    }

    @Test
    void testCourseComplete() {
        Bouee.chargerConfigurationLigne();
        Bouee.verifierBoueePassee(105.0, 305.0);
        Bouee.verifierBoueePassee(405.0, 305.0);
        Bouee.verifierBoueePassee(705.0, 305.0);
        assertTrue(Bouee.isCourseTerminee());
        assertEquals(3, Bouee.getNombreBoueesPassees());
        assertNull(Bouee.getBoueeCourante());
    }

    @Test
    void testReinitialiserParcours() {
        Bouee.chargerConfigurationLigne();
        Bouee.verifierBoueePassee(105.0, 305.0);
        Bouee.verifierBoueePassee(405.0, 305.0);
        Bouee.reinitialiserParcours();
        assertEquals(0, Bouee.getNombreBoueesPassees());
        assertNotNull(Bouee.getBoueeCourante());
        assertEquals(Bouee.TypeBouee.DEPART, Bouee.getBoueeCourante().getType());
        assertFalse(Bouee.isCourseTerminee());
    }
}
