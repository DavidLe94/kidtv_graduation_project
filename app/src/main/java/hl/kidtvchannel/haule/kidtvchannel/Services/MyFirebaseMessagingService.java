package hl.kidtvchannel.haule.kidtvchannel.Services;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import hl.kidtvchannel.haule.kidtvchannel.Notifications.FirebaseNotifications;

/**
 * Created by Hau Le on 2/8/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        FirebaseNotifications.getInstance(getApplicationContext()).displayNotifications(title, body);
    }

}
