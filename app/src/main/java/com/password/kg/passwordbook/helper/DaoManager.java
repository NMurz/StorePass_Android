package com.password.kg.passwordbook.helper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.password.kg.model.CategoriesDao;
import com.password.kg.model.DaoMaster;
import com.password.kg.model.DaoSession;
import com.password.kg.model.PasswordsDao;
import com.password.kg.model.UserDao;

/**
 * Created by Nurs on 11.01.2016.
 */
public class DaoManager extends Application {

    static PasswordsDao passwordsDao;
    static CategoriesDao categoriesDao;
    static UserDao userDao;
    private static int PasswordId;

    public static int getPasswordId() {
        return PasswordId;
    }

    public static void setPasswordId(int passwordId) {
        PasswordId = passwordId;
    }

    public static PasswordsDao getPasswordsDao(){return passwordsDao;}
    public static CategoriesDao getCategoriesDao(){return categoriesDao;}
    public static UserDao getUserDao(){return userDao;}
    SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "password-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        passwordsDao = daoSession.getPasswordsDao();
        categoriesDao = daoSession.getCategoriesDao();
        userDao = daoSession.getUserDao();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        db.close();

    }
}
