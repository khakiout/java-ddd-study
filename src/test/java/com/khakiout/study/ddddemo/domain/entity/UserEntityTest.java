package com.khakiout.study.ddddemo.domain.entity;

import static junit.framework.TestCase.assertNull;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import org.junit.Test;

public class UserEntityTest {

    @Test
    public void testValidEntityMustNotError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "lobat@gmail.com");
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNull(eve);
    }

    @Test
    public void testInvalidFirstNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "", "Batista", "lobat@gmail.com");
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().size());
    }

    @Test
    public void testInvalidLastNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", null, "lobat@gmail.com");
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().size());
    }

    @Test
    public void testInvalidEmailOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "l.com");
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().size());
    }

    @Test
    public void testMultipleInvalidFieldsOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(null, null, null, "l.com");
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(3, eve.getErrorMessages().size());
        System.out.println(eve.getErrorMessages());
        assertEquals("Name must have a value.", eve.getErrorMessages().get(0).getMessage());
        assertEquals("Name must have a value.", eve.getErrorMessages().get(1).getMessage());
        assertEquals("Email must be a valid email address.", eve.getErrorMessages().get(2).getMessage());
    }
}
