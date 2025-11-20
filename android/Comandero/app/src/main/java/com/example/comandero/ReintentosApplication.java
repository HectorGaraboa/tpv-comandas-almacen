package com.example.comandero;

import android.app.Application;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.comandero.offline.ComandaRetryWorker;
import java.util.concurrent.TimeUnit;

public class ReintentosApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneTimeWorkRequest bootRetry = new OneTimeWorkRequest.Builder(ComandaRetryWorker.class)
                .setConstraints(ComandaRetryWorker.netConnected())
                .build();
        WorkManager.getInstance(this).enqueueUniqueWork("RetryPendientesBoot", ExistingWorkPolicy.REPLACE, bootRetry);

        PeriodicWorkRequest periodic = new PeriodicWorkRequest.Builder(ComandaRetryWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(ComandaRetryWorker.netConnected())
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("RetryPendientesPeriodic", ExistingPeriodicWorkPolicy.UPDATE, periodic);
    }
}
