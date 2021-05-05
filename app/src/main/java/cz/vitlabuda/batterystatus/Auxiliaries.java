package cz.vitlabuda.batterystatus;

/*
SPDX-License-Identifier: BSD-3-Clause

Copyright (c) 2021 Vít Labuda. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
following conditions are met:
 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
    disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote
    products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



The app icon was created using the "battery charging" clipart shipped with Android Studio, licensed under the
Apache License Version 2.0.
*/

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public final class Auxiliaries {
    private static final String TAG = Auxiliaries.class.getName();
    private static final String RUN_ON_STARTUP_FLAG_FILENAME = "run_on_startup.flag";

    private final Context context;
    private final File runOnStartupFlagFile;

    public Auxiliaries(Context context) {
        this.context = context;
        this.runOnStartupFlagFile = new File(context.getFilesDir(), RUN_ON_STARTUP_FLAG_FILENAME);
    }

    public void startNotificationService() {
        if(isNotificationServiceRunning()) {
            Log.d(TAG, "[startNotificationService] Service already running, not starting it again.");
            return;
        }

        Intent intent = new Intent(context, NotificationService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intent);
        else
            context.startService(intent);

        Log.d(TAG, "[startNotificationService] Notification service started.");
    }

    public void stopNotificationService() {
        if(!isNotificationServiceRunning()) {
            Log.d(TAG, "[stopNotificationService] Service not running, not stopping it.");
            return;
        }

        Intent intent = new Intent(context, NotificationService.class);
        context.stopService(intent);

        Log.d(TAG, "[stopNotificationService] Notification service stopped.");
    }

    public boolean isNotificationServiceRunning() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(NotificationService.class.getName().equals(serviceInfo.service.getClassName()))
                return true;
        }

        return false;
    }

    public int getCurrentBatteryPercentage() {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

        int percentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return Math.min(100, Math.max(0, percentage));
    }

    public static String percentageIntToString(int percentage) {
        return (percentage + " %");
    }

    public boolean getRunOnStartup() {
        return runOnStartupFlagFile.exists();
    }

    public void setRunOnStartup(boolean runOnStartup) {
        try {
            if(runOnStartup)
                runOnStartupFlagFile.createNewFile();
            else
                runOnStartupFlagFile.delete();
        } catch (IOException e) {
            throw new AppRuntimeException("A file in the app's own files directory couldn't be created or deleted!");
        }

        Log.d(TAG, "[setRunOnStartup] Run on startup: " + runOnStartup);
    }
}
