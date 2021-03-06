package com.udacity.gradle.builditbigger.paid;

/**
 * Created by Danielyan on 1/30/17.
 * The Async Task Class For RESTfull Api
 */

import android.os.AsyncTask;

import com.example.danelyan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.MainActivityFragment;

import java.io.IOException;

class EndpointAsyncTask extends AsyncTask<MainActivityFragment, Void, String> {
    private static MyApi myApiService = null;
    private MainActivityFragment mainActivityFragment;

    @Override
    protected String doInBackground(MainActivityFragment... params) {

        mainActivityFragment = params[0];


        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://joketellingapp-1257.appspot.com/_ah/api/");
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            // end options for devappserver

            myApiService = builder.build();
        }




        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        /*// Create Intent to launch JokeFactory Activity
        Intent intent = new Intent(context, DisplayJokeActivity.class);
        // Put the string in the envelope
        intent.putExtra(DisplayJokeActivity.JOKE_KEY,result);
        context.startActivity(intent);
        */

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        mainActivityFragment.loadedJoke = result;
        mainActivityFragment.launchDisplayJokeActivity();

    }
}