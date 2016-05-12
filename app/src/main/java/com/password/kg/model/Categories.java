package com.password.kg.model;

import java.util.List;
import com.password.kg.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CATEGORIES".
 */
public class Categories {

    private Long id;
    /** Not-null value. */
    private String Name;
    private int Color;
    private Long UserId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CategoriesDao myDao;

    private User user;
    private Long user__resolvedKey;

    private List<Passwords> Passwords;

    public Categories() {
    }

    public Categories(Long id) {
        this.id = id;
    }

    public Categories(Long id, String Name, int Color, Long UserId) {
        this.id = id;
        this.Name = Name;
        this.Color = Color;
        this.UserId = UserId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoriesDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String Name) {
        this.Name = Name;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int Color) {
        this.Color = Color;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long UserId) {
        this.UserId = UserId;
    }

    /** To-one relationship, resolved on first access. */
    public User getUser() {
        Long __key = this.UserId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
            	user__resolvedKey = __key;
            }
        }
        return user;
    }

    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            UserId = user == null ? null : user.getId();
            user__resolvedKey = UserId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Passwords> getPasswords() {
        if (Passwords == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PasswordsDao targetDao = daoSession.getPasswordsDao();
            List<Passwords> PasswordsNew = targetDao._queryCategories_Passwords(id);
            synchronized (this) {
                if(Passwords == null) {
                    Passwords = PasswordsNew;
                }
            }
        }
        return Passwords;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetPasswords() {
        Passwords = null;
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
