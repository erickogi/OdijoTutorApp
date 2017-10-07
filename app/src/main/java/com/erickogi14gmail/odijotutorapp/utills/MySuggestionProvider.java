package com.erickogi14gmail.odijotutorapp.utills;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Eric on 9/26/2017.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY="com.erickogi14gmail.odijos1";
    public final static int MODE=DATABASE_MODE_QUERIES;

    public  MySuggestionProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
