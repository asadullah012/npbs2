package com.galib.natorepbs2.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SyncTariff extends AsyncTask<Void, Void, Void> {
    String data;
    Context context;
    public SyncTariff(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(URLs.BASE + URLs.TARIFF).get();
            data = document.select(Selectors.TARIFF).html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Utility.openWebActivity(context, "বিদ্যুতের মূল্যহার", null, data);
    }
}
