package com.galib.natorepbs2.sync;

import android.os.AsyncTask;

import com.galib.natorepbs2.viewmodel.InformationViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
public class SyncAtAGlance extends AsyncTask<Void, Void, Void> {
    String url = "http://pbs2.natore.gov.bd/bn/site/page/7XSs-%E0%A6%8F%E0%A6%95-%E0%A6%A8%E0%A6%9C%E0%A6%B0%E0%A7%87";
    InformationViewModel viewModel;
    String[][] trtd;
    public SyncAtAGlance(InformationViewModel informationViewModel){
        viewModel = informationViewModel;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(url).get();

            Elements tables = document.select("#left > div.page.details > div > div > div.html_text.body > div > table");

            for (Element table : tables) {
                Elements trs = table.select("tr");
                trtd = new String[trs.size()][];
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    trtd[i] = new String[tds.size()];
                    for (int j = 0; j < tds.size(); j++) {
                        trtd[i][j] = tds.get(j).text();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //viewModel.deleteAllByCategory(Category.atAGlance);
        viewModel.insertFromArray(trtd);
    }
}
