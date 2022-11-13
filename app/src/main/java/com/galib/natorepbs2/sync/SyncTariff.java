package com.galib.natorepbs2.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.galib.natorepbs2.Utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SyncTariff extends AsyncTask<Void, Void, Void> {
    String url = "http://pbs2.natore.gov.bd/en/site/page/%E0%A6%AC%E0%A6%BF%E0%A6%A6%E0%A7%8D%E0%A6%AF%E0%A7%81%E0%A6%A4%E0%A7%87%E0%A6%B0-%E0%A6%AE%E0%A7%82%E0%A6%B2%E0%A7%8D%E0%A6%AF%E0%A6%B9%E0%A6%BE%E0%A6%B0";
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
            Document document = Jsoup.connect(url).get();
            data = document.select("#left > div.page.details > div > div > div.html_text.body").html();
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
