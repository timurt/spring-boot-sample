package com.backend.tasks.repository;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@code User} class.
 *
 * @author Timur Tibeyev.
 */
public class UserTest {
    @Test
    public void testHashCodeOne() {
        User user1 = new User();
        User user2 = new User();
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testHashCodeTwo() {
        User user1 = new User();
        user1.setUsername("Bruce Wayne");
        User user2 = new User();
        user2.setUsername("Peter Parker");
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testHashCodeThree() {
        User user1 = new User();
        user1.setUsername("flash");
        User user2 = new User();
        user2.setUsername("flash");
        User user3 = new User();
        user3.setUsername(new String("flash"));
        assertEquals(user1.hashCode(), user2.hashCode());
        assertEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    public void testEqualsOne() {
        User user = new User();
        assertTrue(user.equals(user));
        assertFalse(user.equals(null));
        assertFalse(user.equals(this));
    }

    @Test
    public void testEqualsTwo() {
        User user1 = new User();
        user1.setUsername("boy");
        User user2 = new User();
        user2.setUsername("boy1");
        assertFalse(user1.equals(user2));
        assertFalse(user2.equals(user1));
    }

    @Test
    public void testEqualsThree() {
        User user1 = new User();
        user1.setUsername("superman");
        User user2 = new User();
        user2.setUsername("superman");
        User user3 = new User();
        user3.setUsername("supergirl");

        assertTrue(user1.equals(user2));
        assertTrue(user2.equals(user1));

        assertFalse(user1.equals(user3));
        assertFalse(user3.equals(user1));

        assertFalse(user2.equals(user3));
        assertFalse(user3.equals(user2));
    }
}
