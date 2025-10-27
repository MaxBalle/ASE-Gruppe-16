import java.util.ArrayList;
import java.util.List;

public class Workpackage {
    private String id;

    public int duration;

    public List<Workpackage> depedencies = new ArrayList<Workpackage>();
    private List<Workpackage> predecessors = new ArrayList<Workpackage>();
    private List<Workpackage> successors = new ArrayList<Workpackage>();

    public int earliestStart;

    public int latestStart;

    public int earliestFinish;

    public int latestFinish;

    public int slack;

    public boolean isStartNode;

    public boolean isEndNode;

    public Workpackage(String id, int duration, List<Workpackage> depedencies) {
        this.id = id;
        this.duration = duration;
        this.depedencies = depedencies;
        if (depedencies.size() == 0) {
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

    public List<Workpackage> getDepedencies() {
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
     * public void addDependency(String dependencyId) {
     * this.depedencies.add(dependencyId);
     * }
     */

    public void addDependencyList(List<Workpackage> dependencyIds) {
        this.depedencies.addAll(dependencyIds);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDepedencies(List<Workpackage> depedencies) {
        this.depedencies = depedencies;
        this.isStartNode = (depedencies == null || depedencies.isEmpty());
    }

    public List<Workpackage> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<Workpackage> predecessors) {
        this.predecessors = predecessors;
    }

    public void addPredecessor(Workpackage predecessor) {
        this.predecessors.add(predecessor);
    }

    public List<Workpackage> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<Workpackage> successors) {
        this.successors = successors;
    }

    public void addSuccessor(Workpackage successor) {
        this.successors.add(successor);
    }

    public void setStartNode(boolean isStartNode) {
        this.isStartNode = isStartNode;
    }
}
