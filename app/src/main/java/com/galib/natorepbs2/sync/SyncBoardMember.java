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

public class SyncBoardMember extends AsyncTask<Void, Void, Void> {
    private final EmployeeViewModel employeeViewModel;
    String tableData[][] = null;

    public SyncBoardMember(EmployeeViewModel employeeViewModel){
        this.employeeViewModel = employeeViewModel;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(URLs.BASE + URLs.BOARD_MEMBER).get();

            Elements tables = document.select(Selectors.BOARD_MEMBER);
            for (Element table : tables) {
                Elements trs = table.select("tr");
                tableData = new String[trs.size()][];
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    tableData[i] = new String[tds.size()];
                    for (int j = 0; j < tds.size(); j++) {
                        tableData[i][j] = tds.get(j).text();
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if(tableData == null) return;
        employeeViewModel.insertBoardMembersFromTable(tableData);
    }
}
