package io.anime.otakubook.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import io.anime.otakubook.R;
import io.anime.otakubook.models.animes.anime.AnimeResponse;
import retrofit2.Call;
import retrofit2.Response;

public class AnimeRequestHandler extends AsyncTask<Call, Void, AnimeResponse> {

    private AsyncEventListener asyncEventListener;
    private Call<AnimeResponse> myCall;
    private Context myContext;

    public AnimeRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected AnimeResponse doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<AnimeResponse> response = myCall.execute();
            Log.d("TESZT", "Response: " + response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(AnimeResponse result) {
        if (result != null && !result.toString().contains("error:")) {
            asyncEventListener.onSuccessAnime(result);
        } else {
            Toast.makeText(myContext, myContext.getString(R.string.error_on_no_result_from_server2), Toast.LENGTH_LONG).show();
        }
    }
}
