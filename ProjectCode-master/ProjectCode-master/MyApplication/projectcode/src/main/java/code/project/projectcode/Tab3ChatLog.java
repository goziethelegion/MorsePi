package code.project.projectcode;
/*
Created By Radhika Hira
TEAM PANAD
PROJECT CODE
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Tab3ChatLog extends Fragment {
    ListView listView;
    ScrollView scroller;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> mess = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Handling View
        final View rootView = inflater.inflate(R.layout.tab3_chatlog, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_of_message);
        scroller = (ScrollView) rootView.findViewById(R.id.scroller);

        new RetrieveTasks(getActivity()).execute();

        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mess);
        listView.setAdapter(itemsAdapter);

        return rootView;
    }

    class RetrieveTasks extends AsyncTask<String, Void, String> {
        String json_url;
        String JSON_STRING;
        Context ctx;
        int count;
        //used to access an activity
        AlertDialog.Builder builder;
        private Activity activity;
        private AlertDialog loginDialog;

        public RetrieveTasks(Context ctx) {
            this.ctx = ctx;
            activity = (Activity) ctx;
        }

        //This is first executed because it assigns the database url before connection is established
        @Override
        protected void onPreExecute() {
            builder = new AlertDialog.Builder(ctx);
            View dialogView = LayoutInflater.from(this.ctx).inflate(R.layout.processdialog, null);
            ((TextView) dialogView.findViewById(R.id.tprogressdialog)).setText(getString(R.string.wait));
            loginDialog = builder.setView(dialogView).setCancelable(false).show();
            json_url = getString(R.string.website1);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();

                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //could be used to monitor progress while doInBackground method is still running
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //This is implemented after execution
        @Override
        protected void onPostExecute(String result) {
            loginDialog.dismiss();
            //Checks if there is an internet connection
            if (TextUtils.isEmpty(result)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage(getString(R.string.errconnection));
                builder.setCancelable(false);
                builder.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new Tab3ChatLog.RetrieveTasks(ctx).execute();
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            //if there is a connection, then do the following
            else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    JSONObject JO;
                    count = 0;
                    String c_id = "", user_one = "", user_two = "", message = "", morse;

                    while (count < jsonArray.length())
                    {
                        JO = jsonArray.getJSONObject(count);
                        c_id = JO.getString("c_id");
                        user_one = JO.getString("user_one");
                        message = JO.getString("message");
                        user_two = JO.getString("user_two");

                        if (user_one.equals("1")) {
                            // Here is where we need the adapater to append "Gozie ->"
                            mess.add("Gozie ->");
                        }
                        else {
                            // Here is where we need the adapter to append "Andrew ->"
                            mess.add("Andrew ->");
                        }
                        if(user_two.equals("1"))
                        {
                            mess.add("Gozie\n");
                            //Here is where we need the adapter to append "Gozie \n"
                        }
                        else{
                            //Here is where we need the adapter to append "Andrew \n"
                            mess.add("Andrew\n");
                        }
                        mess.add(message);
                        morse = TextToMorse.textToMorse(message);
                        // adapter must append morse and \n
                        mess.add(morse);
                        count++;
                        Log.d("message", String.valueOf(jsonArray.length()));
                        itemsAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle(getString(R.string.err));
                    builder.setMessage(getString(R.string.retry));
                    builder.setCancelable(true);
                    builder.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Tab3ChatLog.RetrieveTasks(ctx).execute();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }

    }
}
