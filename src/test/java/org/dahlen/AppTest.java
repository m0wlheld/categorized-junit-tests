package org.dahlen;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Tests for {@link org.dahlen.App}. 
 * 
 * @author Christoph Dahlen
 */
public class AppTest {

    /**
     * A sample test that should never fail.
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * A sample test that will always fail. It's
     * marked as 'integration test', it will not be
     * run without activating the appropriate Maven
     * profile.
     */
    @Test
    @Category(IntegrationTest.class)
    public void shouldAnswerWithFalse() {
        assertTrue(false);
    }
}
