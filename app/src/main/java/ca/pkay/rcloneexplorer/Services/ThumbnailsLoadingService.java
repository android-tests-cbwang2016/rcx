package ca.pkay.rcloneexplorer.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import ca.pkay.rcloneexplorer.Rclone;

public class ThumbnailsLoadingService extends IntentService {

    public static final String REMOTE_ARG = "ca.pkay.rcexplorer.ThumbnailsLoadingService.REMOTE_ARG";
    private Rclone rclone;
    private Process process;

    public ThumbnailsLoadingService() {
        super("ca.pkay.rcexplorer.ThumbnailLoadingService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rclone = new Rclone(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        String remote = intent.getStringExtra(REMOTE_ARG);
        process = rclone.serveHttp(remote, "", 29170);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        process.destroy();
    }
}
