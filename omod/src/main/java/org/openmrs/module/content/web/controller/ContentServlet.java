package org.openmrs.module.content.web.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.content.exception.BadRequestException;
import org.openmrs.module.content.exception.ContentNotFoundException;
import org.openmrs.module.content.model.FileContent;
import org.openmrs.module.content.service.content.ContentService;
import org.openmrs.module.content.service.filecontent.FileContentService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by romanmudryi on 29.07.15.
 */
public class ContentServlet extends HttpServlet {
    private Log LOGGER = LogFactory.getLog(ContentServlet.class);

    private static final String SERVLET_PATH = "/openmrs-content-omod/contents/";

    private ContentService contentService;

    private FileContentService fileContentService;

    @Override
    public void init() throws ServletException {
        LOGGER.info("Initialization of ContentServlet");

        ApplicationContext ac = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        this.contentService = ac.getBean("contentService", ContentService.class);
        this.fileContentService = ac.getBean("fileContentService", FileContentService.class);
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("GET request to ContentServlet");

        String uuid = getUUIDFromRequest(request.getPathInfo());
        File file = contentService.findContentByUUID(uuid);
        try(InputStream input = new FileInputStream(file);
            OutputStream output = response.getOutputStream()){
            IOUtils.copy(input, output);
        }
        response.setContentLength((int) file.length());
        response.setContentType(new MimetypesFileTypeMap().getContentType(file));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("POST request to ContentServlet");

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            throw new BadRequestException("Bad data");
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> fields = upload.parseRequest(request);
            FileContent fileContent = fileContentService.parseToFileContent(fields.iterator());
            String uuid = contentService.saveContent(fileContent);
            response.setStatus(200);
            response.getWriter().write(uuid);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("DELETE request to ContentServlet");

        String uuid = getUUIDFromRequest(request.getPathInfo());
        contentService.removeContent(uuid);
        response.setStatus(200);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (BadRequestException e) {
            resp.setStatus(400);
            resp.getWriter().write(e.getValue());
        } catch (ContentNotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(e.getValue());
        }
    }

    private String getUUIDFromRequest(String path) {
        if (SERVLET_PATH.length() >= path.length()) {
            throw new BadRequestException("No uuid in url");
        }
        return path.replace(SERVLET_PATH, "");
    }

}