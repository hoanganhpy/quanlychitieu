package vn.edu.hutech.quanlychitieu.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPrefsUtil {

    public static final String LOGGED_IN = "LOGGED_IN";

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getPreferencesEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static String getPreferenceValue(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void setPreferenceValue(Context context, String key, String prefsValue) {
        getPreferencesEditor(context).putString(key, prefsValue).apply();
    }

    public static boolean containsPreferenceKey(Context context, String key) {
        return getPreferences(context).contains(key);
    }

    public static void removePreferenceValue(Context context, String key) {
        getPreferencesEditor(context).remove(key).apply();
    }
}