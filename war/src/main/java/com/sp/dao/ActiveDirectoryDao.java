package com.sp.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sp.model.ActiveDirectoryDetails;

/**
 * ActiveDirectoryDao.
 */
@Repository
public class ActiveDirectoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * getSession.
     *
     * @return Session hibernate Session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * getAciveDirectoryDetails.
     *
     * @return ActiveDirectoryDetails
     * @param ldapId ldapId
     */
    @Transactional
    public ActiveDirectoryDetails getAciveDirectoryDetails(Long ldapId) {
        Query q = getSession().createQuery("from ActiveDirectoryDetails where ldap_id=:ldap_id");
        q.setParameter("ldap_id", ldapId);
        ActiveDirectoryDetails directoryDetails = (ActiveDirectoryDetails) q.uniqueResult();
        return directoryDetails;
    }
}
