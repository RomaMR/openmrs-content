package org.openmrs.module.content.repository;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.openmrs.module.content.model.Content;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by romanmudryi on 30.07.15.
 */
@Component("contentRepository")
public class DefaultContentRepository implements ContentRepository{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Content find(Long id) {
        return (Content) sessionFactory.getCurrentSession().get(Content.class, id);
    }

    @Override
    public Content findByUUID(String uuid) {
       return (Content) sessionFactory.getCurrentSession().createQuery("from Content c where c.uuid = :uuid").setParameter("uuid", uuid).uniqueResult();
    }

    @Override
    public Long save(Content content) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(content);
        session.flush();
        transaction.commit();
        return id;
    }

    @Override
    public void remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
    }
}
