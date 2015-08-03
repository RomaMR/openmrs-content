package org.openmrs.module.content.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by romanmudryi on 29.07.15.
 */
@Entity
@Table(name = "content")
public class Content implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "content_id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "encounter_id", nullable = false)
    private String encounterId;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "path")
    private String path;

    public Content() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
