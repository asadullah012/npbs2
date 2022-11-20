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

public class SyncJuniorOfficers extends AsyncTask<Void, Void, Void> {
    String tableData[][] = null;
    EmployeeViewModel employeeViewModel;

    public SyncJuniorOfficers(EmployeeViewModel viewModel){
        employeeViewModel = viewModel;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Connect to the website
            Document document = Jsoup.connect(URLs.BASE + URLs.JUNIOR_OFFICER_LIST).get();

            Elements tables = document.select(Selectors.JUNIOR_OFFICER_LIST);

            for (Element table : tables) {
                Elements trs = table.select("tr");
                tableData = new String[trs.size()][];
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    tableData[i] = new String[tds.size()];
                    for (int j = 0; j < tds.size(); j++) {
                        if(j == 1)
                            if(tds.get(j).select("img").first() != null)
                                tableData[i][j] = tds.get(j).select("img").first().absUrl("src");
                            else
                                tableData[i][j] = tds.get(j).text();
                        else
                            tableData[i][j] = tds.get(j).text();
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
        if(tableData == null) return;
        employeeViewModel.insertJuniorOfficerFromTable(tableData);
    }
}
