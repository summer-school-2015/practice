package practice.dimak.inet;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

class JSONAsyncTask extends AsyncTask<String, Void, JSONObject> {


    Context context;
    int pressedButtonId;

    public JSONAsyncTask(Context context, int pressedButtonId) {
        this.context = context;
        this.pressedButtonId = pressedButtonId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        try {

            //------------------>>
            HttpGet httppost = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);


            JSONObject json = new JSONObject(data);

            return json;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject result) {
        ((MainActivity) context).proceedAction(pressedButtonId, result, context);
    }
}