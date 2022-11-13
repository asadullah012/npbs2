package com.galib.natorepbs2.sync;

import android.os.AsyncTask;
import com.galib.natorepbs2.InformationViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

// Currently not in use
public class SyncVisionMission extends AsyncTask<Void, Void, Void> {
    String url = "http://pbs2.natore.gov.bd/bn/site/page/ZLmE-%E0%A6%AE%E0%A6%BF%E0%A6%B6%E0%A6%A8-%E0%A6%93-%E0%A6%AD%E0%A6%BF%E0%A6%B6%E0%A6%A8";
    InformationViewModel viewModel;
    String vision, mission;
    public SyncVisionMission(InformationViewModel informationViewModel) {
        viewModel = informationViewModel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(url).get();

            vision = document.selectFirst("#left > div.page.details > div > div > div.html_text.body > div > p:nth-child(1) > span").text();
            mission = document.selectFirst("#left > div.page.details > div > div > div.html_text.body > div > p:nth-child(3) > span").text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //viewModel.deleteAllByCategory(Category.atAGlance);
        viewModel.insertVisionMission(vision, mission);
    }
}
