package de.jensklingenberg.sheasy.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import de.jensklingenberg.sheasy.App;
import de.jensklingenberg.sheasy.R;
import de.jensklingenberg.sheasy.ui.MainActivity;

/**
 * Created by multidots on 6/23/2016.
 */
public class BundledNotificationHelper {
    public static final String NOTIFICATION_GROUP_KEY = "group_key";
    private static NotificationManager notificationManager;
    private static int BIG_TEXT_NOTIFICATION_KEY = 0;

    public BundledNotificationHelper(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void generateBundle(Context context) {

        generateSingleNotification(context);


        //create summary notification
        setSummaryNotification(context);
    }

    private void setSummaryNotification(Context context) {
        NotificationCompat.Builder summaryNotification = new NotificationCompat.Builder(context)
                .setContentText("sheasy Server running")
                .setAutoCancel(false)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.InboxStyle()
                        .setBigContentTitle("Bundled notification tips")
                        .addLine("Tip 1")
                        .addLine("Tip 2")
                        .addLine("Tip 3")
                        .addLine("Tip 4")
                        .setSummaryText("Total 4 tips"))
                .setGroup(NOTIFICATION_GROUP_KEY)
                .setGroupSummary(true);

        notificationManager.notify(100, summaryNotification.build());
    }

    private void generateSingleNotification(Context context) {
        BIG_TEXT_NOTIFICATION_KEY++;

        PendingIntent pIntent = PendingIntent.getActivity(context,
                (int) System.currentTimeMillis(),
                new Intent(context, MainActivity.class), 0);

        // Add to your action, enabling Direct Reply for it
        NotificationCompat.Action replayAction =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher_background, "Replay", pIntent)
                        .build();

        NotificationCompat.Builder noti1 = new NotificationCompat.Builder(context)
                .setContentText("sheasy Server running")
                .setAutoCancel(false)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("sheasy Server running at " + NetworkUtils.Companion.getIP(App.instance))
                        .setBigContentTitle("How to create bundle notification? Tip: " + BIG_TEXT_NOTIFICATION_KEY)
                        .setSummaryText("Tip to build notification"))
                .setContentIntent(pIntent)
                //.addAction(replayAction)
                .setGroup(NOTIFICATION_GROUP_KEY);

        notificationManager.notify(BIG_TEXT_NOTIFICATION_KEY, noti1.build());
    }
}
