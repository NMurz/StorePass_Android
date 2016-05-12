package com.password.kg.passwordbook.helper;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Nurs on 07.02.2016.
 */
public class LanguageHelper {
    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());


        switch (locale) {
            case "ru":
                config.locale = new Locale("ru");
                break;
            case "ky":
                config.locale = new Locale("ky");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        // reload files from assets directory



    }
}
