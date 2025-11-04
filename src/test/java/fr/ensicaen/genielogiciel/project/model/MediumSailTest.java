package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class MediumSailTest {

        @Test
        public void testAdjustSpeedCloseHauled() {
            IDecoratorSail sail = new MediumSail();
            assertEquals(11.0, sail.adjustSpeed(10.0, 45), 0.01);
        }

        @Test
        public void testAdjustSpeedDownwind() {
            IDecoratorSail sail = new MediumSail();
            assertEquals(9.0, sail.adjustSpeed(10.0, 150), 0.01);
        }
    }
