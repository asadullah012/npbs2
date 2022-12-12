package com.galib.natorepbs2.sync;

import android.os.AsyncTask;

import com.galib.natorepbs2.constants.Selectors;
import com.galib.natorepbs2.constants.URLs;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SyncOfficerList extends AsyncTask<Void, Void, Void> {
    List<List<String>> tableData = null;
    EmployeeViewModel employeeViewModel;
    public SyncOfficerList(EmployeeViewModel viewModel){
        employeeViewModel = viewModel;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(URLs.BASE + URLs.OFFICER_LIST).get();

            Elements tables = document.select(Selectors.OFFICERS_LIST);
            for (Element table : tables) {
                Elements trs = table.select("tr");
                tableData = new ArrayList<>();
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    List<String> tdList = new ArrayList<>();
                    //Log.d("SyncOfficerList", "th: " + tds.select("th").html());

                    for (int j = 0; j < tds.size(); j++) {
                        if(j == 0)
                            tdList.add(tds.get(j).select("img").first().absUrl("src"));
                        else
                            tdList.add(tds.get(j).text());
                        //Log.d("SyncOfficerList", "doInBackground: " + tableData[i][j] );
                    }
                    if(tdList.size() > 0)
                        tableData.add(tdList);
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
        if(tableData == null) return;
        employeeViewModel.insertOfficersFromTable(tableData);
    }
}
