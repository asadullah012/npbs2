package com.galib.natorepbs2.sync;

import android.os.AsyncTask;

import com.galib.natorepbs2.viewmodel.AchievementViewModel;
import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SyncAchievement extends AsyncTask<Void, Void, Void> {

    AchievementViewModel viewModel;
    String[][] trtd;
    public SyncAchievement(AchievementViewModel achievementViewModel){
        viewModel = achievementViewModel;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(URLs.BASE + URLs.ACHIEVEMENTS).get();

            Elements tables = document.select(Selectors.ACHIEVEMENTS);

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
        if(trtd == null) return;
        //viewModel.insertFromArray(trtd);
    }
}
