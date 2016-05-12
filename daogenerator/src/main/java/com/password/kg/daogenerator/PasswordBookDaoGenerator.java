package com.password.kg.daogenerator;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class PasswordBookDaoGenerator {
    public static void main(String[] args) throws IOException {
        Schema schema = new Schema(1, "com.password.kg.model");

        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("Email").notNull();
        user.addStringProperty("FIO");
        user.addStringProperty("Token");
        user.addStringProperty("Avatar");
        user.addBooleanProperty("isAuthenticated");
        user.addStringProperty("PinCode");
        user.addIntProperty("LanguageId");

        Entity categories = schema.addEntity("Categories");
        categories.addIdProperty().primaryKey().autoincrement();
        categories.addStringProperty("Name").notNull();
        categories.addIntProperty("Color").notNull();

        Property userId = categories.addLongProperty("UserId").getProperty();
        categories.addToOne(user, userId);

        ToMany userToCategory = user.addToMany(categories, userId);
        userToCategory.setName("Categories");

        Entity passwords = schema.addEntity("Passwords");
        passwords.addIdProperty().primaryKey().autoincrement();
        passwords.addStringProperty("Title").notNull();
        passwords.addStringProperty("Website");
        passwords.addStringProperty("Account").notNull();
        passwords.addStringProperty("Password").notNull();
        passwords.addStringProperty("Description");

        Property categoryId = passwords.addLongProperty("CategoryId").getProperty();
        passwords.addToOne(categories, categoryId);

        ToMany categoryToPasswords = categories.addToMany(passwords, categoryId);
        categoryToPasswords.setName("Passwords");

        try {
            new DaoGenerator().generateAll(schema, "../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
