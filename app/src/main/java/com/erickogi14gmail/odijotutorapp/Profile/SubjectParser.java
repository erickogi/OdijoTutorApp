package com.erickogi14gmail.odijotutorapp.Profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Eric on 10/18/2017.
 */

public class SubjectParser {
    private static ArrayList<Subjects> subjectss;

    public static ArrayList<Subjects> parseData(String response) {
        subjectss = new ArrayList<>();
        JSONArray subjects = null;
        Subjects model = null;

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(response);

            subjects = jObj.getJSONArray("subjects");

            for (int i = 0; i < subjects.length(); i++) {

                JSONObject obj = subjects.getJSONObject(i);
                model = new Subjects();
                model.setSubject_id(obj.getInt("subject_id"));
                model.setSubject_name(obj.getString("subject_name"));


                ArrayList<Levels> levels = new ArrayList<>();
                String gsong = obj.getString("levels");


                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Levels>>() {

                }.getType();
                levels = gson.fromJson(gsong, collectionType);

                model.setLevelses(levels);
                subjectss.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return subjectss;

    }

}
