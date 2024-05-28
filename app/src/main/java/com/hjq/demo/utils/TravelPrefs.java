package com.hjq.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.api.sharedpreferences.BooleanPrefEditorField;
import org.androidannotations.api.sharedpreferences.BooleanPrefField;
import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.IntPrefEditorField;
import org.androidannotations.api.sharedpreferences.IntPrefField;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;
import org.androidannotations.api.sharedpreferences.StringPrefEditorField;
import org.androidannotations.api.sharedpreferences.StringPrefField;

/**
 * @author flight
 * @date 2024/5/26
 */
public class TravelPrefs extends SharedPreferencesHelper {
    private static final String SP_NAME = "travel.SJ_Prefs";

    private static TravelPrefs instance;

    private TravelPrefs(Context context) {
        super(context.getSharedPreferences(SP_NAME, 0));
    }

    public static TravelPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new TravelPrefs(context);
        }
        return instance;
    }

    public TravelPrefs.TravelPrefsEditor edit() {
        return new TravelPrefs.TravelPrefsEditor(getSharedPreferences());
    }

    public StringPrefField token() {
        // 访问令牌
        return stringField("token", "");
    }

    public final static class TravelPrefsEditor extends EditorHelper<TravelPrefsEditor> {
        TravelPrefsEditor(SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        public StringPrefEditorField<TravelPrefs.TravelPrefsEditor> token() {
            return stringField("token");
        }
    }
}
