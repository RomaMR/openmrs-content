package org.openmrs.module.content.service.filecontent;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.content.exception.BadRequestException;
import org.openmrs.module.content.model.FileContent;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * Created by romanmudryi on 03.08.15.
 */
@Component("fileContentService")
public class DefaultFileContentSevice implements FileContentService {
    private Log LOGGER = LogFactory.getLog(DefaultFileContentSevice.class);

    private static final String PATIENT_ID = "patientId";

    private static final String ENCOUNTER_ID = "encounterId";

    @Override
    public FileContent parseToFileContent(Iterator<FileItem> it) {
        LOGGER.info("Parsing file content");

        if (!it.hasNext()) {
            throw new BadRequestException("No fields found");
        }

        FileContent fileContent = new FileContent();

        while (it.hasNext()) {
            FileItem fileItem = it.next();
            boolean isFormField = fileItem.isFormField();
            if (isFormField) {
                if (PATIENT_ID.equals(fileItem.getFieldName())) {
                    fileContent.setPatientId(fileItem.getString());
                } else if (ENCOUNTER_ID.equals(fileItem.getFieldName())) {
                    fileContent.setEncounterId(fileItem.getString());
                }
            } else {
                fileContent.setFile(fileItem);
            }
        }
        return fileContent;
    }

}
