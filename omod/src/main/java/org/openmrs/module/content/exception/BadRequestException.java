package org.openmrs.module.content.exception;

/**
 * Created by romanmudryi on 03.08.15.
 */
public class BadRequestException extends RuntimeException {

    private String value;

    public BadRequestException(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}