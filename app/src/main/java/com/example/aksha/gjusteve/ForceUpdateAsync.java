package com.example.aksha.gjusteve;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import com.example.aksha.gjusteve.login.SplashScreen;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//class ForceUpdateAsync extends AsyncTask<String, String, JSONObject> {
//
//    private String latestVersion;
//    final private String currentVersion;
//    private final Context context;
//
//// --Commented out by Inspection START (8/2/2018 7:23 PM):
////    ForceUpdateAsync(String currentVersion, Context context){
////        this.currentVersion = currentVersion;
////        this.context = context;
////    }
//// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
//
//    @Override
//    protected JSONObject doInBackground(String... params) {
//
//        try {
//            latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+context.getPackageName()+"&hl=en")
//                    .timeout(30000)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get()
//                    .select("div[itemprop=softwareVersion]")
//                    .first()
//                    .ownText();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new JSONObject();
//    }
//
//    @Override
//    protected void onPostExecute(JSONObject jsonObject) {
//        if(latestVersion!=null){
//            if(!currentVersion.equalsIgnoreCase(latestVersion)){
//                // Toast.makeText(context,"update is available.",Toast.LENGTH_LONG).show();
//                if(!(context instanceof SplashScreen)) {
//                    if(!((Activity)context).isFinishing()){
//                        showForceUpdateDialog();
//                    }
//                }
//            }
//        }
//        super.onPostExecute(jsonObject);
//    }
//
//    private void showForceUpdateDialog(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context,
//                R.style.DialogDark));
//
//        alertDialogBuilder.setTitle(context.getString(R.string.youAreNotUpdatedTitle));
//        alertDialogBuilder.setMessage(context.getString(R.string.youAreNotUpdatedMessage) + " " + latestVersion + context.getString(R.string.youAreNotUpdatedMessage1));
//        alertDialogBuilder.setCancelable(true);
//        alertDialogBuilder.setPositiveButton(R.string.update, (dialog, id) -> {
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
//            dialog.cancel();
//        });
//        alertDialogBuilder.show();
//    }
//}
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)
