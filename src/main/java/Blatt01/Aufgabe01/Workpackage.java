package Blatt01.Aufgabe01;

import java.util.ArrayList;
import java.util.List;

public class Workpackage {
    public String id;

    public int duration;

    public List<String> depedencies = new ArrayList<>();

    public int earliestStart;

    public int latestStart;

    public int earliestFinish;

    public int latestFinish;

    public int slack;

    public boolean isStartNode;

    public boolean isEndNode;

    public Workpackage(String id, int duration, List<String> depedencies) {
        this.id = id;
        this.duration = duration;
        this.depedencies = depedencies;
        if (depedencies.size()== 0) {
            this.isStartNode = true;
        } else {
            this.isStartNode = false;
            
        }
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public List<String> getDepedencies() {
        return depedencies;
    }

    public int getEarliestStart() {
        return earliestStart;
    }

    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public void setLatestStart(int latestStart) {
        this.latestStart = latestStart;
    }

    public int getEarliestFinish() {
        return earliestFinish;
    }

    public void setEarliestFinish(int earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    public void setLatestFinish(int latestFinish) {
        this.latestFinish = latestFinish;
    }

    public int getSlack() {
        return slack;
    }

    public void setSlack(int slack) {
        this.slack = slack;
    }

    public boolean isStartNode() {
        return isStartNode;
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public void setEndNode(boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    /* 
    public void addDependency(String dependencyId) {
        this.depedencies.add(dependencyId);
    }*/

    public void addDependencyList(List<String> dependencyIds) {
        this.depedencies.addAll(dependencyIds);
    }

    
    

    
}
