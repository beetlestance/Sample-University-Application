package com.example.aksha.gjusteve.helper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class CustomTaskLoader extends AsyncTaskLoader {

    final private String fragmentName;
    final private DataParser dataParser;
    public CustomTaskLoader(DataParser dataParserContext,Context context,String fragment) {
        super(context);
        fragmentName=fragment;
        dataParser=dataParserContext;
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        switch (fragmentName){
            case "Announcement":
                dataParser.prepareData();
                break;
            case "PastEvent":
                dataParser.prepareData();
                break;
            case "Events":
                dataParser.prepareData();
                break;
            case "SuggestionAndFeedback":
                dataParser.prepareData();
                break;
            case "FacultyMembers":
                dataParser.prepareData();
                break;
        }
        return null;
    }

}