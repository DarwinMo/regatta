package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @ParameterizedTest
    @MethodSource("usernames")
    void should_get_nickname_when_set_nickname( String name ) {
        // TODO This test is of no interest.
        //       It only shows the use of a parameterized test
        PlayerModel loginModel = new PlayerModel();
        loginModel.setNickname(name);
        assertEquals(name, loginModel.getNickname());
    }

    public static Stream<Arguments> usernames() {
        return Stream.of(
                Arguments.of("Toto"),
                Arguments.of("Un nom avec espace")
        );
    }
}