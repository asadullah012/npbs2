package com.galib.natorepbs2.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.galib.natorepbs2.constants.Category;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NPBS2Repository {

    private InformationDao informationDao;
    private AchievementDao achievementDao;
    private ComplainCentreDao complainCentreDao;
    private EmployeeDao employeeDao;
    private static final String TAG = NPBS2Repository.class.getName();
    public NPBS2Repository(Application application) {
        NPBS2DB db = NPBS2DB.getDatabase(application);
        informationDao = db.informationDao();
        achievementDao = db.achievementDao();
        complainCentreDao = db.complainCentreDao();
        employeeDao = db.employeeDao();
    }

    public LiveData<List<Achievement>> getAllAchievement() {
        return achievementDao.getAll();
    }

    public void insertAchievement(Achievement achievement){
        achievementDao.insert(achievement);
    }

    public void insertAchievementAll(List<Achievement> achievements){
        NPBS2DB.databaseWriteExecutor.execute(() -> {
            achievementDao.insertAll(achievements);
        });
    }

    public LiveData<List<Information>> getInformationByCategory(String category) {
        return informationDao.getInformationsByCategory(category);
    }

    public void insertInformation(Information information) {
        NPBS2DB.databaseWriteExecutor.execute(() -> {
            Log.d(TAG, "insertInformation: " + information.getCategory() + " " + information.getDescription());
            informationDao.insert(information);
        });
    }

    public void setMonth(String month){
        insertInformation(new Information(0, month, "", Category.atAGlanceMonth));
    }

    public LiveData<Information> getMonth(){
        LiveData<Information> information = informationDao.getInformationByCategory(Category.atAGlanceMonth);
        return information;
    }

    public int insertInformations(List<Information> informationList) {
        Log.d(TAG, "insertInformations: " + informationList.size());
        NPBS2DB.databaseWriteExecutor.execute(() -> {
            informationDao.insertInfos(informationList);
        });
        return getInformationCount();
    }

    public int getInformationCount(){
        AtomicInteger count = new AtomicInteger();
        NPBS2DB.databaseWriteExecutor.execute(() -> count.set(informationDao.getCount()));
        Log.d(TAG, "getInformationCount: " + count);
        return count.intValue();
    }

    public void deleteAllByCategory(String category) {
        NPBS2DB.databaseWriteExecutor.execute(() -> informationDao.deleteAllByCategory(category));
    }

    public void insertComplainCentre(ComplainCentre complainCentre){
        NPBS2DB.databaseWriteExecutor.execute(()->complainCentreDao.insert(complainCentre));
    }

    public void insertAllComplainCentre(List<ComplainCentre> complainCentreList){
        NPBS2DB.databaseWriteExecutor.execute(()->complainCentreDao.insertAll(complainCentreList));
    }

    public LiveData<List<ComplainCentre>> getAllComplainCentre(){
        return complainCentreDao.getAll();
    }

    //Employee

    public void insertEmployeeList(List<Employee> employeeList){
        NPBS2DB.databaseWriteExecutor.execute(()->employeeDao.insertList(employeeList));
    }

    public LiveData<List<Employee>> getOfficerList(){
        return employeeDao.getAllByType(Category.OFFICERS);
    }
    public LiveData<List<Employee>> getJuniorOfficerList(){
        return employeeDao.getAllByType(Category.JUNIOR_OFFICER);
    }

    public LiveData<List<Employee>> getBoardMemberList() {
        return employeeDao.getAllByType(Category.BOARD_MEMBER);
    }

    public LiveData<List<Employee>> getPowerOutageContactList() {
        return employeeDao.getAllByType(Category.POWER_OUTAGE_CONTACT);
    }

    public LiveData<Information> getPowerOutageHeaderText() {
        return informationDao.getInformationByCategory(Category.powerOutageContactHeader);
    }

    public LiveData<Information> getPowerOutageFooterText() {
        return informationDao.getInformationByCategory(Category.powerOutageContactFooter);
    }

    public LiveData<Employee> getOfficeHead() {
        return employeeDao.getOfficeHead(Category.OFFICERS);
    }
}