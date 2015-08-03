package org.openmrs.module.content.model;

import org.apache.commons.fileupload.FileItem;

/**
 * Created by romanmudryi on 03.08.15.
 */
public class FileContent {

    private String patientId;

    private String encounterId;

    private FileItem file;

    public FileContent() {
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterId) {
        this.encounterId = encounterId;
    }

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }
}
