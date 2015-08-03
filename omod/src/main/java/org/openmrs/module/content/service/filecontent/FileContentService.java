package org.openmrs.module.content.service.filecontent;

import org.apache.commons.fileupload.FileItem;
import org.openmrs.module.content.model.FileContent;

import java.util.Iterator;

/**
 * Created by romanmudryi on 03.08.15.
 */
public interface FileContentService {

    FileContent parseToFileContent(Iterator<FileItem> it);

}
