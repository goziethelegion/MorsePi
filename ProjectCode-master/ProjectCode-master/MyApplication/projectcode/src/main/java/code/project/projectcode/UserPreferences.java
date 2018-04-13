package code.project.projectcode;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */

public class UserPreferences {

    private boolean french;
    private boolean sound;
    private String color;


     public UserPreferences()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ProjectActivity.getAppContext());
        sound = prefs.getBoolean("Sound",true);
        color = prefs.getString("Color","white");
    }

    public void setColor(String newcolor)
    {
        SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(ProjectActivity.getAppContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("Color",newcolor);
        edit.apply();
        this.color = newcolor;
    }

    public void setSound(boolean vol)
    {
        SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(ProjectActivity.getAppContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("Sound",vol);
        edit.apply();
        this.sound = vol;
    }


    public boolean checkSound()
    {
        return sound;
    }
    public String checkColor(){return color;}


}
