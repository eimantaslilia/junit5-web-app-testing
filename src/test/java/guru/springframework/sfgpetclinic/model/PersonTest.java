package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
        //given
        Person person = new Person(1l, "Joe", "Buck");
        //then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName()),
                () -> assertEquals("Buck", person.getLastName()));
    }

    @Test
    void groupedAssertionMessage() {
        //given
        Person person = new Person(1l, "Joe", "Buck");
        //then
        assertAll("Test Props Set",
                () -> assertEquals(person.getFirstName(), "Joe", "First name failed"),
                () -> assertEquals(person.getLastName(), "Buck"));
    }
}