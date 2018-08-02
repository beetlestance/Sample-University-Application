package com.example.aksha.gjusteve.adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.aksha.gjusteve.POJO.AnnouncementObject;
import com.example.aksha.gjusteve.R;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementObjectHolder> {
    private String pdfUrl;
    final private List<AnnouncementObject> mData;
    public static final String MESSAGE_PROGRESS = "message_progress";
    final private DownloadFile downloadFile;

    class AnnouncementObjectHolder extends RecyclerView.ViewHolder {
        final TextView mAnnouncement;
        final ImageView mDownload;
        AnnouncementObjectHolder(View itemView) {
            super(itemView);
            mAnnouncement = itemView.findViewById(R.id.text_announcement);
            mDownload=itemView.findViewById(R.id.announcement_download);
        }
    }
    public AnnouncementAdapter(DownloadFile mDownloadFile, List<AnnouncementObject> data) {
        downloadFile = mDownloadFile;
        mData = data;
    }

    @NonNull
    @Override
    public AnnouncementObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAnnouncement = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_announcement, parent, false);
        return new AnnouncementObjectHolder(viewAnnouncement);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementObjectHolder holder, int position) {

        final AnnouncementObject announcementObject = mData.get(position);
        holder.mAnnouncement.setText(announcementObject.getmString());
        holder.mDownload.setOnClickListener(view -> {
            pdfUrl =announcementObject.getmPdfUrl();
            downloadFile.downloadFile(pdfUrl);
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface DownloadFile{
        void downloadFile(String uid);
    }
}
