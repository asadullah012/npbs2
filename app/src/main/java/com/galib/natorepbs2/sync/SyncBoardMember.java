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

public class SyncBoardMember extends AsyncTask<Void, Void, Void> {
    private final EmployeeViewModel employeeViewModel;
    List<List<String>> tableData = null;

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
                tableData = new ArrayList<>();
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    List<String> tdList = new ArrayList<>();
                    for (int j = 0; j < tds.size(); j++) {
                        tdList.add(tds.get(j).text());
                    }
                    if(tdList.size() > 0)
                        tableData.add(tdList);
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
