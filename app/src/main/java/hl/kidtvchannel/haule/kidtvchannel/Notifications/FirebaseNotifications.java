package hl.kidtvchannel.haule.kidtvchannel.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import hl.kidtvchannel.haule.kidtvchannel.Activity.MainActivity;
import hl.kidtvchannel.haule.kidtvchannel.R;

/**
 * Created by Hai Son on 2/14/2018.
 */

public class FirebaseNotifications {
    private Context context;
    private static FirebaseNotifications mInstance;

    private FirebaseNotifications(Context context){
        this.context = context;
    }
    public static synchronized FirebaseNotifications getInstance(Context context){
        if(mInstance == null){
            mInstance = new FirebaseNotifications(context);
        }
        return mInstance;
    }

    public void displayNotifications(String title, String body){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null){
            notificationManager.notify(1, builder.build());
        }
    }
}
