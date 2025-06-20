package sk.leziemevpezinku.spring.model;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class AppParamTest {

    @Test
    public void testToString() {
        var appParam = AppParam.builder()
                .name("TEST")
                .value("TEST")
                .build();

        assertEquals("AppParam(name=TEST, value=TEST)", appParam.toString());
    }
}
