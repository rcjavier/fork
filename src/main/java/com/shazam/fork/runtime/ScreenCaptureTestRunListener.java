/*
 * Copyright 2015 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.shazam.fork.runtime;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;
import com.shazam.fork.io.FileManager;
import com.shazam.fork.model.Device;

import java.util.Map;

public class ScreenCaptureTestRunListener implements ITestRunListener {
    private final FileManager fileManager;
    private final IDevice deviceInterface;
    private final String pool;
    private final String serial;

    private ScreenCapturer screenCapturer;
    private boolean hasFailed;

    public ScreenCaptureTestRunListener(FileManager fileManager, String pool, Device device) {
        this.fileManager = fileManager;
        this.deviceInterface = device.getDeviceInterface();
        this.pool = pool;
        serial = device.getSerial();
    }

    @Override
    public void testRunStarted(String runName, int testCount) {
    }

    @Override
    public void testStarted(TestIdentifier test) {
        hasFailed = false;
        screenCapturer = new ScreenCapturer(deviceInterface, fileManager, pool, serial, test);
        new Thread(screenCapturer).start();
    }

    @Override
    public void testFailed(TestIdentifier test, String trace) {
        hasFailed = true;
    }

    @Override
    public void testAssumptionFailure(TestIdentifier test, String trace) {
        screenCapturer.stopCapturing(hasFailed);
    }

    @Override
    public void testIgnored(TestIdentifier test) {
        screenCapturer.stopCapturing(hasFailed);
    }

    @Override
    public void testEnded(TestIdentifier test, Map<String, String> testMetrics) {
        screenCapturer.stopCapturing(hasFailed);
    }

    @Override
    public void testRunFailed(String errorMessage) {
    }

    @Override
    public void testRunStopped(long elapsedTime) {
    }

    @Override
    public void testRunEnded(long elapsedTime, Map<String, String> runMetrics) {
    }
}
