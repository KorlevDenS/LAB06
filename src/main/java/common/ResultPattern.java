package common;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultPattern implements Serializable {

    private ArrayList<String> reports = new ArrayList<>();
    private String instructionTitle;
    private boolean timeToExit;

    public String getInstructionTitle() {
        return instructionTitle;
    }

    public void setInstructionTitle(String instructionTitle) {
        this.instructionTitle = instructionTitle;
    }

    public ArrayList<String> getReports() {
        return reports;
    }

    public void setReports(ArrayList<String> reports) {
        this.reports = reports;
    }

    public void setTimeToExit(boolean timeToExit) {
        this.timeToExit = timeToExit;
    }

    public boolean isTimeToExit() {
        return timeToExit;
    }
}
