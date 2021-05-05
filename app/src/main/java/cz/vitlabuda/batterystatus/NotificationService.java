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

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends Service {

    private final BatteryChangedReceiver batteryChangedReceiver = new BatteryChangedReceiver(percentage -> {
        Notification notification = buildNotification(percentage);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationService.this);
        notificationManager.notify(App.BATTERY_NOTIFICATION_ID, notification);
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Auxiliaries auxiliaries = new Auxiliaries(this);
        Notification notification = buildNotification(auxiliaries.getCurrentBatteryPercentage());

        startForeground(App.BATTERY_NOTIFICATION_ID, notification);
        registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(batteryChangedReceiver);
    }

    private Notification buildNotification(int batteryPercentage) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String percentageString = Auxiliaries.percentageIntToString(batteryPercentage);
        String text = String.format(getResources().getString(R.string.current_battery_level), percentageString);

        return new NotificationCompat.Builder(this, App.BATTERY_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(percentageString)
                .setContentText(text)
                .setSmallIcon(getBatteryPercentageIcon(batteryPercentage))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setSound(null)
                .setOngoing(true)
                .build();
    }

    private int getBatteryPercentageIcon(int batteryPercentage) {
        switch(batteryPercentage) {
            case 0: return R.drawable.ic_number_0;
            case 1: return R.drawable.ic_number_1;
            case 2: return R.drawable.ic_number_2;
            case 3: return R.drawable.ic_number_3;
            case 4: return R.drawable.ic_number_4;
            case 5: return R.drawable.ic_number_5;
            case 6: return R.drawable.ic_number_6;
            case 7: return R.drawable.ic_number_7;
            case 8: return R.drawable.ic_number_8;
            case 9: return R.drawable.ic_number_9;
            case 10: return R.drawable.ic_number_10;
            case 11: return R.drawable.ic_number_11;
            case 12: return R.drawable.ic_number_12;
            case 13: return R.drawable.ic_number_13;
            case 14: return R.drawable.ic_number_14;
            case 15: return R.drawable.ic_number_15;
            case 16: return R.drawable.ic_number_16;
            case 17: return R.drawable.ic_number_17;
            case 18: return R.drawable.ic_number_18;
            case 19: return R.drawable.ic_number_19;
            case 20: return R.drawable.ic_number_20;
            case 21: return R.drawable.ic_number_21;
            case 22: return R.drawable.ic_number_22;
            case 23: return R.drawable.ic_number_23;
            case 24: return R.drawable.ic_number_24;
            case 25: return R.drawable.ic_number_25;
            case 26: return R.drawable.ic_number_26;
            case 27: return R.drawable.ic_number_27;
            case 28: return R.drawable.ic_number_28;
            case 29: return R.drawable.ic_number_29;
            case 30: return R.drawable.ic_number_30;
            case 31: return R.drawable.ic_number_31;
            case 32: return R.drawable.ic_number_32;
            case 33: return R.drawable.ic_number_33;
            case 34: return R.drawable.ic_number_34;
            case 35: return R.drawable.ic_number_35;
            case 36: return R.drawable.ic_number_36;
            case 37: return R.drawable.ic_number_37;
            case 38: return R.drawable.ic_number_38;
            case 39: return R.drawable.ic_number_39;
            case 40: return R.drawable.ic_number_40;
            case 41: return R.drawable.ic_number_41;
            case 42: return R.drawable.ic_number_42;
            case 43: return R.drawable.ic_number_43;
            case 44: return R.drawable.ic_number_44;
            case 45: return R.drawable.ic_number_45;
            case 46: return R.drawable.ic_number_46;
            case 47: return R.drawable.ic_number_47;
            case 48: return R.drawable.ic_number_48;
            case 49: return R.drawable.ic_number_49;
            case 50: return R.drawable.ic_number_50;
            case 51: return R.drawable.ic_number_51;
            case 52: return R.drawable.ic_number_52;
            case 53: return R.drawable.ic_number_53;
            case 54: return R.drawable.ic_number_54;
            case 55: return R.drawable.ic_number_55;
            case 56: return R.drawable.ic_number_56;
            case 57: return R.drawable.ic_number_57;
            case 58: return R.drawable.ic_number_58;
            case 59: return R.drawable.ic_number_59;
            case 60: return R.drawable.ic_number_60;
            case 61: return R.drawable.ic_number_61;
            case 62: return R.drawable.ic_number_62;
            case 63: return R.drawable.ic_number_63;
            case 64: return R.drawable.ic_number_64;
            case 65: return R.drawable.ic_number_65;
            case 66: return R.drawable.ic_number_66;
            case 67: return R.drawable.ic_number_67;
            case 68: return R.drawable.ic_number_68;
            case 69: return R.drawable.ic_number_69;
            case 70: return R.drawable.ic_number_70;
            case 71: return R.drawable.ic_number_71;
            case 72: return R.drawable.ic_number_72;
            case 73: return R.drawable.ic_number_73;
            case 74: return R.drawable.ic_number_74;
            case 75: return R.drawable.ic_number_75;
            case 76: return R.drawable.ic_number_76;
            case 77: return R.drawable.ic_number_77;
            case 78: return R.drawable.ic_number_78;
            case 79: return R.drawable.ic_number_79;
            case 80: return R.drawable.ic_number_80;
            case 81: return R.drawable.ic_number_81;
            case 82: return R.drawable.ic_number_82;
            case 83: return R.drawable.ic_number_83;
            case 84: return R.drawable.ic_number_84;
            case 85: return R.drawable.ic_number_85;
            case 86: return R.drawable.ic_number_86;
            case 87: return R.drawable.ic_number_87;
            case 88: return R.drawable.ic_number_88;
            case 89: return R.drawable.ic_number_89;
            case 90: return R.drawable.ic_number_90;
            case 91: return R.drawable.ic_number_91;
            case 92: return R.drawable.ic_number_92;
            case 93: return R.drawable.ic_number_93;
            case 94: return R.drawable.ic_number_94;
            case 95: return R.drawable.ic_number_95;
            case 96: return R.drawable.ic_number_96;
            case 97: return R.drawable.ic_number_97;
            case 98: return R.drawable.ic_number_98;
            case 99: return R.drawable.ic_number_99;
            case 100: return R.drawable.ic_number_100;
        }

        return R.drawable.ic_question;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
