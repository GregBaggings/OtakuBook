package io.anime.otakubook.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import io.anime.otakubook.R;
import io.anime.otakubook.models.animes.search.SearchResponse;
import retrofit2.Call;
import retrofit2.Response;

public class SearchRequestHandler extends AsyncTask<Call, Void, SearchResponse> {

    private AsyncEventListener asyncEventListener;
    private Call<SearchResponse> myCall;
    private Context myContext;

    public SearchRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SearchResponse doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<SearchResponse> response = myCall.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchResponse result) {
        if (result != null) {
            asyncEventListener.onSuccessSearch(result);
        } else {
            Toast.makeText(myContext, myContext.getString(R.string.error_on_no_result_from_server), Toast.LENGTH_SHORT).show();
        }
    }

}
