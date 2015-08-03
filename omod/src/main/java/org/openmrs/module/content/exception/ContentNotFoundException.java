package org.openmrs.module.content.exception;

/**
 * Created by romanmudryi on 03.08.15.
 */
public class ContentNotFoundException extends RuntimeException {

    private String value;

    public ContentNotFoundException(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
