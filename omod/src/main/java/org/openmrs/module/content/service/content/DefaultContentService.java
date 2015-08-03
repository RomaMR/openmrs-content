package org.openmrs.module.content.service.content;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.content.exception.ContentNotFoundException;
import org.openmrs.module.content.model.Content;
import org.openmrs.module.content.model.FileContent;
import org.openmrs.module.content.repository.ContentRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by romanmudryi on 30.07.15.
 */
@Component("contentService")
public class DefaultContentService implements ContentService {
    private Log LOGGER = LogFactory.getLog(DefaultContentService.class);

    private static final String ROOT_DIRECTORY = "content/";

    @Resource(name = "contentRepository")
    private ContentRepository contentRepository;

    @Override
    public String saveContent(FileContent fileContent) throws IOException {
        LOGGER.info("Saving Content");

        Content content = new Content();
        content.setDateCreated(new Date());
        content.setPatientId(fileContent.getPatientId());
        content.setEncounterId(fileContent.getEncounterId());
        content.setFileName(fileContent.getFile().getName());
        content.setMimeType(fileContent.getFile().getContentType());

        String uuid = String.format("%s_%s_%s_%d.%s", content.getPatientId(), content.getEncounterId(), content.getFileName(), content.getDateCreated().getTime(), content.getMimeType());
        content.setUuid(uuid);

        String path = content.getPatientId() + "/" + UUID.randomUUID().toString();
        File contentFolder = new File(getDirectory(), path);
        if (!contentFolder.exists()) {
            contentFolder.mkdirs();
        }
        content.setPath(path + "/" + content.getFileName());

        try (InputStream is = fileContent.getFile().getInputStream();
             FileOutputStream os = new FileOutputStream(new File(contentFolder, content.getFileName()))) {
            IOUtils.copy(is, os);
        }
        Long id = contentRepository.save(content);
        return contentRepository.find(id).getUuid();
    }

    @Override
    public File findContentByUUID(String uuid) throws IOException {
        LOGGER.info("Finding Content");

        Content content = contentRepository.findByUUID(uuid);

        if (content == null) {
            throw new ContentNotFoundException("Content not found");
        }

        File file = new File(getDirectory(), content.getPath());

        if (!file.exists()) {
            throw new ContentNotFoundException("File not exists");
        }

        return file;
    }

    @Override
    public void removeContent(String uuid) throws IOException {
        LOGGER.info("Removing Content");

        Content content = contentRepository.findByUUID(uuid);

        if (content == null) {
            throw new ContentNotFoundException("Content not found");
        }

        File file = new File(getDirectory(), content.getPath());

        if (!file.exists()) {
            throw new ContentNotFoundException("File not exists");
        }

        File folder = file.getParentFile();

        if (!folder.exists()) {
            throw new ContentNotFoundException("File not exists");
        }

        file.setWritable(true);
        file.delete();
        folder.setWritable(true);
        folder.delete();

        contentRepository.remove(content);
    }

    private File getDirectory() {
        String root = System.getProperty("user.home");
        File contentFolder = new File(root, ROOT_DIRECTORY);
        if (!contentFolder.exists()) {
            contentFolder.mkdirs();
        }
        return contentFolder;
    }
}
