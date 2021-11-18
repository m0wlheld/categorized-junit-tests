package org.dahlen;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    @Category(IntegrationTest.class)
    public void shouldAnswerWithFalse() {
        assertTrue(false);
    }
}
