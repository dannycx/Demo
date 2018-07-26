package com.danny.file.green.domain;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.danny.file.green.domain.Person;

import com.danny.file.green.domain.PersonDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig personDaoConfig;

    private final PersonDao personDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        personDaoConfig = daoConfigMap.get(PersonDao.class).clone();
        personDaoConfig.initIdentityScope(type);

        personDao = new PersonDao(personDaoConfig, this);

        registerDao(Person.class, personDao);
    }
    
    public void clear() {
        personDaoConfig.clearIdentityScope();
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

}
