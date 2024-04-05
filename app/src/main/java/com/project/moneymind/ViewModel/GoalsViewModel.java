package com.project.moneymind.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.project.moneymind.database.DBHelper;
import com.project.moneymind.models.Goals;
import com.project.moneymind.models.transaction;

import java.util.Date;

public class GoalsViewModel extends AndroidViewModel {
    private MutableLiveData<Goals> currentGoal = new MutableLiveData<>();
    private MutableLiveData<Integer> progress = new MutableLiveData<>();
    private DBHelper db;

    public GoalsViewModel(@NonNull Application application) {
        super(application);
        db = new DBHelper(this.getApplication());
    }

    public LiveData<Goals> getCurrentGoal() {
        return currentGoal;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }

    public void setGoal(Goals goal) {
        db.insertGoalData(goal, transaction.getAcid(), transaction.getUser_id());
        currentGoal.setValue(goal);
        progress.setValue(0);
    }

    public void addMoney(double amount) {
        Goals goal = currentGoal.getValue();
        if (goal != null) {
            double savedAmount = goal.getSavedAmount() + amount;
            goal.setSavedAmount(savedAmount);
            currentGoal.setValue(goal);
            updateProgress();

            // Update the saved amount in the database
            db.updateSavedAmount(goal.getId(), savedAmount);
        }
    }

    private void updateProgress() {
        Goals goal = currentGoal.getValue();
        if (goal != null && goal.getAmount() != 0) {
            double progressPercentage = (goal.getSavedAmount() / goal.getAmount()) * 100;
            progress.setValue((int) progressPercentage);
        }
    }
}
