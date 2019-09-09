package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test Proper view name is returned for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), "Wrong View Returned");
        assertEquals("index", controller.index(), () -> "Another Expensive Message");
        assertThat(controller.index()).isEqualTo("index");
    }

    @Test
    void oopsHandler() {
//        assertTrue("asdf".equals(controller.oopsHandler()),() -> "This is some expensive" + "Message to build" + "for my test");
    assertThrows(ValueNotFoundException.class, () -> {
        controller.oopsHandler();
    });
    }

    @Disabled
    @Test
    void testTimeOut(){
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(5000);
            System.out.println("I got here");
        });
    }

    @Disabled
    @Test
    void testTimeOutPreemptively(){
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(5000);
            System.out.println("I got here preemptively");
        });
    }

    @Test
    void testAssumptionTrue(){
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU_RUNTIME")));
    }

    @Test
    void testAssumptionTrueisTrue(){
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU")));
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testOnMacOs(){

    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testOnWindows(){

    }
    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testOnJre11(){

    }
    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void TestOnJre8(){

    }

}