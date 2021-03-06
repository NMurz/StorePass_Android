package com.password.kg.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.password.kg.model.User;
import com.password.kg.model.Categories;
import com.password.kg.model.Passwords;

import com.password.kg.model.UserDao;
import com.password.kg.model.CategoriesDao;
import com.password.kg.model.PasswordsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig categoriesDaoConfig;
    private final DaoConfig passwordsDaoConfig;

    private final UserDao userDao;
    private final CategoriesDao categoriesDao;
    private final PasswordsDao passwordsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        categoriesDaoConfig = daoConfigMap.get(CategoriesDao.class).clone();
        categoriesDaoConfig.initIdentityScope(type);

        passwordsDaoConfig = daoConfigMap.get(PasswordsDao.class).clone();
        passwordsDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        categoriesDao = new CategoriesDao(categoriesDaoConfig, this);
        passwordsDao = new PasswordsDao(passwordsDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Categories.class, categoriesDao);
        registerDao(Passwords.class, passwordsDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        categoriesDaoConfig.getIdentityScope().clear();
        passwordsDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CategoriesDao getCategoriesDao() {
        return categoriesDao;
    }

    public PasswordsDao getPasswordsDao() {
        return passwordsDao;
    }

}
