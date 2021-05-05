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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final BatteryChangedReceiver batteryChangedReceiver = new BatteryChangedReceiver(new BatteryChangedReceiver.BatteryChangedListener() {
        @Override
        public void onBatteryPercentageChange(int percentage) {
            if(batteryPercentageTextView == null)
                return;

            batteryPercentageTextView.setText(Auxiliaries.percentageIntToString(percentage));
        }
    });

    private TextView batteryPercentageTextView = null;
    private CheckBox showNotificationCheckbox = null;
    private CheckBox runOnStartupCheckbox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryPercentageTextView = findViewById(R.id.battery_percentage_textview);
        showNotificationCheckbox = findViewById(R.id.show_notitfication_checkbox);
        runOnStartupCheckbox = findViewById(R.id.run_on_startup_checkbox);

        initializeWidgets();
    }

    private void initializeWidgets() {
        Auxiliaries auxiliaries = new Auxiliaries(this);

        batteryPercentageTextView.setText(Auxiliaries.percentageIntToString(auxiliaries.getCurrentBatteryPercentage()));

        showNotificationCheckbox.setChecked(auxiliaries.isNotificationServiceRunning());
        showNotificationCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                auxiliaries.startNotificationService();
            else
                auxiliaries.stopNotificationService();
        });

        runOnStartupCheckbox.setChecked(auxiliaries.getRunOnStartup());
        runOnStartupCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> auxiliaries.setRunOnStartup(isChecked));
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(batteryChangedReceiver);
    }
}
