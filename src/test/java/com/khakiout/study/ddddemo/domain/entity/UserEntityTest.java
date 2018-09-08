package com.khakiout.study.ddddemo.domain.entity;

import static junit.framework.TestCase.assertNull;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationErrorItem;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationReport;
import java.util.List;
import org.junit.Test;

public class UserEntityTest {

    @Test
    public void testValidEntityMustNotError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "lobat@gmail.com");
            user.validate();
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
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().getDetails().size());
    }

    @Test
    public void testInvalidLastNameOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", null, "lobat@gmail.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().getDetails().size());
    }

    @Test
    public void testInvalidEmailOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(1L, "Lola", "Batista", "l.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        assertEquals(1, eve.getErrorMessages().getDetails().size());
    }

    @Test
    public void testMultipleInvalidFieldsOnEntityMustError() {
        EntityValidationException eve = null;
        try {
            UserEntity user = new UserEntity(null, null, null, "l.com");
            user.validate();
        } catch (EntityValidationException exception) {
            eve = exception;
        }

        assertNotNull(eve);
        assertEquals("Entity has validation errors.", eve.getMessage());
        ValidationReport validationReport = eve.getErrorMessages();
        List<ValidationErrorItem> errors = validationReport.getDetails();
        assertEquals(3, errors.size());
        System.out.println(eve.getErrorMessages());
        assertEquals("Name must have a value.", errors.get(0).getMessage());
        assertEquals("Name must have a value.", errors.get(1).getMessage());
        assertEquals("Email must be a valid email address.", errors.get(2).getMessage());
    }
}
