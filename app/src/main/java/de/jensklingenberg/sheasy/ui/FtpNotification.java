package de.jensklingenberg.sheasy.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import de.jensklingenberg.sheasy.R;

/**
 * Created by yashwanthreddyg on 19-06-2016.
 */
public class FtpNotification {



    static public final String ACTION_STOP_FTPSERVER = "com.amaze.filemanager.services.ftpservice.FTPReceiver.ACTION_STOP_FTPSERVER";

    static public final String TAG_STARTED_BY_TILE = "started_by_tile";  // attribute of action_started, used by notification
    public static final int FTP_ID = 5;
    public static final String CHANNEL_FTP_ID = "ftpChannel";


    public void createNotification(Context context, boolean noStopButton) {

        String notificationService = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(notificationService);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // InetAddress address = FtpService.getLocalInetAddress(context);

        String iptext = "IP ADRESS";

        // int icon = R.drawable.ic_ftp_light;
        long when = System.currentTimeMillis();


        CharSequence contentTitle = "TITLTLE";
        CharSequence contentText = "CONTENXTTEXT";

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_FTP_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setTicker("Ticker")
                .setWhen(when)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        // NotificationConstants.setMetadata(context, notificationBuilder, NotificationConstants.TYPE_FTP);

        Notification notification;
        if (!noStopButton && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int stopIcon = android.R.drawable.ic_menu_close_clear_cancel;
            CharSequence stopText = "STOP";
            Intent stopIntent = new Intent(ACTION_STOP_FTPSERVER).setPackage(context.getPackageName());
            PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0,
                    stopIntent, PendingIntent.FLAG_ONE_SHOT);

            notificationBuilder.addAction(stopIcon, stopText, stopPendingIntent);
            notificationBuilder.setShowWhen(false);
            notification = notificationBuilder.build();
        } else {
            notification = notificationBuilder.getNotification();
        }

        // Pass Notification to NotificationManager
        notificationManager.notify(FTP_ID, notification);
    }

    private void removeNotification(Context context) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) context.getSystemService(ns);
        nm.cancelAll();
    }
}
