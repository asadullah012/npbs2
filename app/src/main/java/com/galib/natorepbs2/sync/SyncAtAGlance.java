package com.galib.natorepbs2.sync;

import android.os.AsyncTask;

import com.galib.natorepbs2.constants.Category;
import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.viewmodel.InformationViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SyncAtAGlance extends AsyncTask<Void, Void, Void> {
    InformationViewModel viewModel;
    List<List<String>> trtd;
    String month = null;
    public SyncAtAGlance(InformationViewModel informationViewModel){
        viewModel = informationViewModel;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(URLs.BASE + URLs.AT_A_GLANCE).get();
            month = document.select(Selectors.AT_A_GLANCE_MONTH).text();
            Elements tables = document.select(Selectors.AT_A_GLANCE);

            for (Element table : tables) {
                Elements trs = table.select("tr");
                trtd = new ArrayList<>();
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    List<String> tdList = new ArrayList<>();
                    for (int j = 0; j < tds.size(); j++) {
                        tdList.add(tds.get(j).text());
                    }
                    trtd.add(tdList);
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
        if(trtd == null) return;
        //viewModel.deleteAllByCategory(Category.atAGlance);
        viewModel.insertFromArray(trtd);
        viewModel.setMonth(month);
    }
}
