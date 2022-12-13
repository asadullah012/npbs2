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

public class SyncPowerOutageContact extends AsyncTask<Void, Void, Void> {
    private EmployeeViewModel employeeViewModel;
    private List<List<String>> tableData = null;
    private String headerText = null;
    private String footerText = null;

    public SyncPowerOutageContact(EmployeeViewModel employeeViewModel){
        this.employeeViewModel = employeeViewModel;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(URLs.BASE + URLs.POWER_OUTAGE_CONTACT).get();
            headerText = document.select(Selectors.POWER_OUTAGE_CONTACT_HEADER).text();
            footerText = document.select(Selectors.POWER_OUTAGE_CONTACT_FOOTER).text();
            Elements tables = document.select(Selectors.POWER_OUTAGE_CONTACT);
            for (Element table : tables) {
                Elements trs = table.select("tr");
                tableData = new ArrayList<>();
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    List<String> tdList = new ArrayList<>();
                    for (int j = 0; j < tds.size(); j++) {
                        if(j == 1)
                            if(tds.get(j).select("img").first() != null)
                                tdList.add(tds.get(j).select("img").first().absUrl("src"));
                            else
                                tdList.add(tds.get(j).text());
                        else
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
        employeeViewModel.insertPowerOutageContactFromTable(tableData, headerText, footerText);
    }
}
