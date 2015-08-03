package org.openmrs.module.content.repository;

import org.openmrs.module.content.model.Content;

/**
 * Created by romanmudryi on 29.07.15.
 */

public interface ContentRepository {

    Content find(Long id);

    Content findByUUID(String uuid);

    Long save(Content content);

    void remove(Content content);

}
