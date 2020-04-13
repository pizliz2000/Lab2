package android.bignerdranch.com.androidlab21;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ItemFetcher {
    private static final String TAG = "ItemFetcher";

        //Довольно универсальный метод. Получает HTML-разметку сайта в виде массива байтов.
    public byte[] getUrlBytes(String urlSpec) throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }
        finally
        {
            connection.disconnect();
        }
    }

    //Преобразует результат из getUrlBytes в String
    public String getUrlString(String urlSpec) throws IOException
    {
        return new String(getUrlBytes(urlSpec));
    }

    public List<JsonItem> fetchItems() {

        List<JsonItem> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json").toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            //JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray jsonBody = new JSONArray(jsonString);
            parseItems(items, jsonBody);
        }catch(JSONException je)
        {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return items;
    }

    private void parseItems(List<JsonItem> items, JSONArray jsonBody) throws IOException, JSONException
    {
        for(int i = 1; i < jsonBody.length(); i++)
        {
            JSONObject itemJsonObject = jsonBody.getJSONObject(i);
            JsonItem item = new JsonItem();

            item.setGraphics(itemJsonObject.getString("graphic"));
            item.setName(itemJsonObject.getString("name"));
            if(itemJsonObject.has("helptext")) {
                item.setHelpText(itemJsonObject.getString("helptext"));
            }
            else{
                item.setHelpText("No description");
            }
            items.add(item);

        }
    }
}
