package com.password.kg.model;

import com.password.kg.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PASSWORDS".
 */
public class Passwords {

    private Long id;
    /** Not-null value. */
    private String Title;
    private String Website;
    /** Not-null value. */
    private String Account;
    /** Not-null value. */
    private String Password;
    private String Description;
    private Long CategoryId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PasswordsDao myDao;

    private Categories categories;
    private Long categories__resolvedKey;


    public Passwords() {
    }

    public Passwords(Long id) {
        this.id = id;
    }

    public Passwords(Long id, String Title, String Website, String Account, String Password, String Description, Long CategoryId) {
        this.id = id;
        this.Title = Title;
        this.Website = Website;
        this.Account = Account;
        this.Password = Password;
        this.Description = Description;
        this.CategoryId = CategoryId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPasswordsDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return Title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }

    /** Not-null value. */
    public String getAccount() {
        return Account;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAccount(String Account) {
        this.Account = Account;
    }

    /** Not-null value. */
    public String getPassword() {
        return Password;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long CategoryId) {
        this.CategoryId = CategoryId;
    }

    /** To-one relationship, resolved on first access. */
    public Categories getCategories() {
        Long __key = this.CategoryId;
        if (categories__resolvedKey == null || !categories__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoriesDao targetDao = daoSession.getCategoriesDao();
            Categories categoriesNew = targetDao.load(__key);
            synchronized (this) {
                categories = categoriesNew;
            	categories__resolvedKey = __key;
            }
        }
        return categories;
    }

    public void setCategories(Categories categories) {
        synchronized (this) {
            this.categories = categories;
            CategoryId = categories == null ? null : categories.getId();
            categories__resolvedKey = CategoryId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
