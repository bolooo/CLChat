package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateUserDataService extends Service {
    public UpdateUserDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
