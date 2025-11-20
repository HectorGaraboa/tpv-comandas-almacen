package com.example.comandero.offline;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ComandaRetryWorker extends Worker {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ComandaRetryWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<JSONObject> all = PendingComandaStore.loadAll(getApplicationContext());
        if (all.isEmpty()) return Result.success();
        List<JSONObject> remaining = new ArrayList<>();
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        for (JSONObject o : all) {
            try {
                if (!"POST_COMANDA".equals(o.getString("type"))) { remaining.add(o); continue; }
                String body = PendingComandaStore.decodeBody(o);
                RequestBody rb = RequestBody.create(JSON, body);
                Response<?> resp = api.rawPost(o.getString("endpoint"), rb).execute();
                if (resp.isSuccessful()) {
                    o.put("sentAt", System.currentTimeMillis());
                } else {
                    remaining.add(o);
                }
            } catch (Exception ex) {
                remaining.add(o);
            }
        }
        PendingComandaStore.replaceAll(getApplicationContext(), remaining);
        return Result.success();
    }

    public static Constraints netConnected() {
        return new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
    }
}
