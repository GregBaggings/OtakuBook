package io.anime.otakubook.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import io.anime.otakubook.R;
import io.anime.otakubook.models.animes.trending.TrendingResponse;
import retrofit2.Call;
import retrofit2.Response;

public class TrendingAnimesRequestHandler extends AsyncTask<Call, Void, TrendingResponse> {

    private AsyncEventListener asyncEventListener;
    private Call<TrendingResponse> myCall;
    private Context myContext;

    public TrendingAnimesRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected TrendingResponse doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<TrendingResponse> response = myCall.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(TrendingResponse result) {
        if (result != null) {
            asyncEventListener.onSuccessTrendingAnimes(result);
        } else {
            Toast.makeText(myContext, myContext.getString(R.string.error_on_no_result_from_server), Toast.LENGTH_SHORT).show();
        }
    }

}
