package com.example.aksha.gjusteve.database;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.example.aksha.gjusteve.BuildConfig;
import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.adapter.AnnouncementAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import static android.R.attr.visibility;
import static com.example.aksha.gjusteve.App.baseUrl;
import static com.example.aksha.gjusteve.App.getContext;

public class DownloadService extends IntentService {

    private String url;
    private String file_name;
    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    @Override
    protected void onHandleIntent(Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        url = Objects.requireNonNull(intent.getExtras()).getString("url");
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        initDownload();

    }

    private void initDownload(){
        APIInterface apiInterface = APIClient.getClient(baseUrl).create(APIInterface.class);

        Call<ResponseBody> request = apiInterface.downloadFile(url);
        try {

            downloadFile(Objects.requireNonNull(request.execute().body()));

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        file_name = url.substring(url.lastIndexOf('/') + 1);
        File docsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Announcements");
        boolean isPresent = true;
        if (!docsFolder.exists()) {
            isPresent = docsFolder.mkdir();
        }
        if (isPresent) {File outputFile = new File(docsFolder,file_name);
            OutputStream output = new FileOutputStream(outputFile);
            long total = 0;
            long startTime = System.currentTimeMillis();
            int timeCount = 1;
            while ((count = bis.read(data)) != -1) {

                total += count;
                totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                double current = Math.round(total / (Math.pow(1024, 2)));

                int progress = (int) ((total * 100) / fileSize);

                long currentTime = System.currentTimeMillis() - startTime;

                Downloader download = new Downloader();
                download.setTotalFileSize(totalFileSize);

                if (currentTime > 1000 * timeCount) {

                    download.setCurrentFileSize((int) current);
                    download.setProgress(progress);
                    sendNotification(download);
                    timeCount++;
                }

                output.write(data, 0, count);
            }
            onDownloadComplete();
            output.flush();
            output.close();
            bis.close();

        }
    }

    private void sendNotification(Downloader download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());

    }

    private void sendIntent(Downloader download){

        Intent intent = new Intent(AnnouncementAdapter.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(){

        Downloader download = new Downloader();
        download.setProgress(100);
        sendIntent(download);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/Announcements",file_name);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext=file.getName().substring(file.getName().indexOf(".")+1);
        String type = mime.getMimeTypeFromExtension(ext);
        Uri fileUri =FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID +".provider",file);
        Intent openFile = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID +".provider",file));
        openFile.setDataAndType(fileUri, type);
        openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> resolvedIntentActivities = getContext().getPackageManager().queryIntentActivities(openFile, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;

            getContext().grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, openFile, 0);
       // getContext().revokeUriPermissionfileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationBuilder.setCategory(Notification.CATEGORY_PROMO);
        notificationBuilder.setContentTitle("Downloaded File");
        notificationBuilder.setContentText(file_name);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setVisibility(visibility);
        notificationBuilder.addAction(android.R.drawable.ic_menu_view, "View details", contentIntent);
        notificationBuilder.setContentIntent(contentIntent);
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}