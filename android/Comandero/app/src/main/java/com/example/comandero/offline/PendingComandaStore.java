package com.example.comandero.offline;

import android.content.Context;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PendingComandaStore {

    private static final String FILE_NAME = "pendientes_comandas.jsonl";

    public static void enqueue(Context ctx, long mesaId, String bodyJson, long createdAtEpochMs) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("type", "POST_COMANDA");
            obj.put("endpoint", "/api/comandas");
            obj.put("mesaId", mesaId);
            obj.put("createdAt", createdAtEpochMs);
            obj.put("sentAt", JSONObject.NULL);
            String encoded = Base64.encodeToString(bodyJson.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            obj.put("bodyB64", encoded);
            File f = new File(ctx.getFilesDir(), FILE_NAME);
            BufferedWriter w = new BufferedWriter(new FileWriter(f, true));
            w.write(obj.toString());
            w.write("\n");
            w.flush();
            w.close();
        } catch (Exception ignored) { }
    }

    public static List<JSONObject> loadAll(Context ctx) {
        List<JSONObject> out = new ArrayList<>();
        try {
            File f = new File(ctx.getFilesDir(), FILE_NAME);
            if (!f.exists()) return out;
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line;
            while ((line = r.readLine()) != null) {
                try {
                    JSONObject o = new JSONObject(line);
                    out.add(o);
                } catch (JSONException ignored) { }
            }
            r.close();
        } catch (Exception ignored) { }
        return out;
    }

    public static void replaceAll(Context ctx, List<JSONObject> remaining) {
        try {
            File f = new File(ctx.getFilesDir(), FILE_NAME);
            if (remaining == null || remaining.isEmpty()) {
                if (f.exists()) f.delete();
                return;
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(f, false));
            for (JSONObject o : remaining) {
                w.write(o.toString());
                w.write("\n");
            }
            w.flush();
            w.close();
        } catch (Exception ignored) { }
    }

    public static String decodeBody(JSONObject obj) {
        try {
            String b64 = obj.getString("bodyB64");
            byte[] bytes = Base64.decode(b64, Base64.NO_WRAP);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "{}";
        }
    }

    public static JSONArray toJSONArray(List<JSONObject> list) {
        JSONArray a = new JSONArray();
        if (list == null) return a;
        for (JSONObject o : list) a.put(o);
        return a;
    }
}
