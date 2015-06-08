package practice.dimak.inet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends Activity implements View.OnClickListener {

    private static String appId = "4950003";

    private static String authUrl = "https://oauth.vk.com/authorize?";
    private static String redirectUrl = "https://oauth.vk.com/blank.html";

    private static String scope = "";


    private static String log = "";
    private static String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void addToLog(String log) {
        MainActivity.log = log + "\n\n" + MainActivity.log;
    }

    public void fakeMenuButtonOnClick(View v) {

        LinearLayout layout = (LinearLayout) findViewById(R.id.main_l);

        layout.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);

        switch (v.getId()) {
            case R.id.loginMenuButton:
                layout.addView(inflater.inflate(R.layout.web_layout, null, false));
                break;
            case R.id.actionButton:
                layout.addView(inflater.inflate(R.layout.action_layout, null, false));
                break;
            case R.id.logMenuButton:
                layout.addView(inflater.inflate(R.layout.log_layout, null, false));
                ((TextView) findViewById(R.id.logView)).setText(MainActivity.log);
                break;
        }

    }

    public void actionMenuOnClick(View v) {

        if (null == accessToken) {
            Toast.makeText(this, "Please authorize before any actions.", Toast.LENGTH_LONG).show();
            return;
        }

        Uri.Builder builder = new Uri.Builder();
        builder
                .scheme("https")
                .authority("api.vk.com")
                .appendPath("method");

        switch (v.getId()) {
            case R.id.getNameByIdButton:
                builder.appendQueryParameter("user_ids", ((TextView)findViewById(R.id.idText)).getText().toString());
            case R.id.getMyNameButton:
                builder.appendPath("users.get");
                break;
            default:
                Toast.makeText(this, "Method not implemented", Toast.LENGTH_SHORT).show();
                return;
        }

        builder.appendQueryParameter("access_token", accessToken);

        MainActivity.addToLog("Make request: " + builder.build().toString());


        try {
            new JSONAsyncTask(this, v.getId()).execute(builder.build().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Cannot process your request\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void proceedAction(int pressedButtonId, JSONObject response, Context context) {
        try {

            MainActivity.addToLog(response.toString());
            JSONObject userInfo = (JSONObject) response.getJSONArray("response").get(0);

            switch (pressedButtonId) {
                case R.id.getMyNameButton:

                    MainActivity.addToLog("Loaded user: " + userInfo.getString("first_name") + " " + userInfo.getString("last_name"));
                    Toast.makeText(context, "Hi, my dear " + userInfo.getString("first_name") + " " + userInfo.getString("last_name"), Toast.LENGTH_LONG).show();
                    break;
                case R.id.getNameByIdButton:
                    MainActivity.addToLog("Loaded user: " + userInfo.getString("first_name") + " " + userInfo.getString("last_name"));
                    Toast.makeText(context, "User with requested id: " + userInfo.getString("first_name") + " " + userInfo.getString("last_name"), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Cannot process your request\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void checkAuth(String url) {
        // Костыли и велосипеды. Программируем как умеем.
        Uri uri = Uri.parse(url.replace("#", "?"));

        accessToken = uri.getQueryParameter("access_token");

        if (null != accessToken) {
            MainActivity.addToLog("Access token: " + accessToken);
            Toast.makeText(this, "Successfully authorized", Toast.LENGTH_SHORT).show();
        }

    }

    public void openOAuthRequest(View v) {
        WebView web = (WebView) findViewById(R.id.webView);

        web.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                MainActivity.addToLog("Browser open: " + url);
                checkAuth(url);
            }
        });

        web.loadUrl(authUrl + "client_id=" + appId + "&scope=" + scope + "&redirect_uri=" + redirectUrl + "&display=" + "mobile" + "&response_type=" + "token");
    }

    @Override
    public void onClick(View v) {

    }
}
