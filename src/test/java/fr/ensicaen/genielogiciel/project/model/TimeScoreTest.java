package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TimeScoreTest {

    @Test
    void should_start_timer_when_call_start() {
        TimeScore timer = new TimeScore();

        timer.start();

        assertTrue(timer.isRunning());
        assertEquals(0, timer.getTotalTime());
    }

    @Test
    void should_throw_exception_when_start_already_running_timer() {
        TimeScore timer = new TimeScore();
        timer.start();

        assertThrows(IllegalStateException.class, timer::start);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void should_measure_correct_time_when_start_and_finish() throws InterruptedException {
        TimeScore timer = new TimeScore();
        long expectedTime = 50;

        timer.start();
        Thread.sleep(expectedTime);
        timer.finish();

        assertFalse(timer.isRunning());
        assertTrue(timer.getTotalTime() >= expectedTime);
        assertTrue(timer.getTotalTime() < 100);
    }

    @Test
    void should_throw_exception_when_finish_not_running_timer() {
        TimeScore timer = new TimeScore();

        assertThrows(IllegalStateException.class, timer::finish);
    }

    @ParameterizedTest
    @MethodSource("timeValues")
    void should_return_correct_formatted_time_when_get_formatted_time( long totalTime, String expectedFormat ) {
        TimeScore timer = new TimeScore();

        try {
            Field field = TimeScore.class.getDeclaredField("totalTime");
            field.setAccessible(true);
            field.set(timer, totalTime);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        String formattedTime = timer.getFormattedTime();

        assertEquals(expectedFormat, formattedTime);
    }

    public static Stream<Arguments> timeValues() {
        return Stream.of(
                Arguments.of(0L, "00:00:000"), Arguments.of(500L, "00:00:500"), Arguments.of(1000L, "00:01:000"), Arguments.of(1500L, "00:01:500"), Arguments.of(60000L, "01:00:000"), Arguments.of(61500L, "01:01:500"), Arguments.of(125000L, "02:05:000")
        );
    }

    @Test
    void should_reset_timer_when_call_reset() {
        TimeScore timer = new TimeScore();
        timer.start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        timer.finish();

        long timeBeforeReset = timer.getTotalTime();

        timer.reset();

        assertEquals(0, timer.getTotalTime());
        assertFalse(timer.isRunning());
        assertTrue(timeBeforeReset > 0);
    }

    @Test
    void should_allow_multiple_sessions_when_start_after_finish() {
        TimeScore timer = new TimeScore();

        timer.start();
        assertTrue(timer.isRunning());
        timer.finish();
        long firstTime = timer.getTotalTime();
        assertFalse(timer.isRunning());

        timer.start();
        assertTrue(timer.isRunning());

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        timer.finish();
        long secondTime = timer.getTotalTime();

        assertFalse(timer.isRunning());
        assertTrue(secondTime > firstTime);
    }

    @Test
    void should_have_zero_time_when_new_instance() {
        TimeScore timer = new TimeScore();

        assertEquals(0, timer.getTotalTime());
        assertFalse(timer.isRunning());
    }
}
