package org.openmrs.module.content.service.content;

import org.openmrs.module.content.model.FileContent;

import java.io.File;
import java.io.IOException;

/**
 * Created by romanmudryi on 30.07.15.
 */
public interface ContentService {

    String saveContent(FileContent fileContent) throws IOException;

    File findContentByUUID(String uuid) throws IOException;

    void removeContent(String uuid) throws IOException;
}
