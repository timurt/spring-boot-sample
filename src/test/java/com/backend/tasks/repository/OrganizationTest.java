package com.backend.tasks.repository;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@code Organization} class.
 *
 * @author Timur Tibeyev.
 */
public class OrganizationTest {
    @Test
    public void testHashCodeOne() {
        Organization org1 = new Organization();
        Organization org2 = new Organization();
        assertEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    public void testHashCodeTwo() {
        Organization org1 = new Organization();
        org1.setName("Microsoft");
        Organization org2 = new Organization();
        org2.setName("Apple");
        assertNotEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    public void testHashCodeThree() {
        Organization org1 = new Organization();
        org1.setName("Tesla");
        Organization org2 = new Organization();
        org2.setName("Tesla");
        Organization org3 = new Organization();
        org3.setName(new String("Tesla"));
        assertEquals(org1.hashCode(), org2.hashCode());
        assertEquals(org1.hashCode(), org3.hashCode());
    }

    @Test
    public void testEqualsOne() {
        Organization org = new Organization();
        assertTrue(org.equals(org));
        assertFalse(org.equals(null));
        assertFalse(org.equals(this));
    }

    @Test
    public void testEqualsTwo() {
        Organization org1 = new Organization();
        org1.setName("Microsoft");
        Organization org2 = new Organization();
        org2.setName("Samsung");
        assertFalse(org1.equals(org2));
        assertFalse(org2.equals(org1));
    }

    @Test
    public void testEqualsThree() {
        Organization org1 = new Organization();
        org1.setName("Ford");
        Organization org2 = new Organization();
        org2.setName("Ford");
        Organization org3 = new Organization();
        org3.setName("Apple");

        assertTrue(org1.equals(org2));
        assertTrue(org2.equals(org1));

        assertFalse(org1.equals(org3));
        assertFalse(org3.equals(org1));

        assertFalse(org2.equals(org3));
        assertFalse(org3.equals(org2));
    }
}
