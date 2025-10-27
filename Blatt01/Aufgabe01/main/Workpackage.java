package main;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a work package in a project network diagram.
 * Contains timing information and dependencies for critical path calculation.
 */
public class Workpackage {
    // Fields for identification and basic properties
    private String id;
    private int duration;
    private boolean isStartNode;
    private boolean isEndNode;

    // Network relationships
    private List<Workpackage> dependencies;
    private List<Workpackage> predecessors;
    private List<Workpackage> successors;

    // Timing calculations
    private int earliestStart;
    private int earliestFinish;
    private int latestStart;
    private int latestFinish;
    private int slack;

    /**
     * Constructs a new work package with the specified parameters.
     * 
     * @param id           The unique identifier for the work package
     * @param duration     The time required to complete the work package
     * @param dependencies List of work packages that must be completed before this
     *                     one
     */
    public Workpackage(String id, int duration, List<Workpackage> dependencies) {
        this.id = id;
        this.duration = duration;
        this.dependencies = dependencies != null ? dependencies : new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.isStartNode = dependencies.isEmpty();
        this.isEndNode = false;
    }

    // Public methods - Getters and Setters
    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public List<Workpackage> getDependencies() {
        return dependencies;
    }

    public int getEarliestStart() {
        return earliestStart;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public int getEarliestFinish() {
        return earliestFinish;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    public int getSlack() {
        return slack;
    }

    public boolean isStartNode() {
        return isStartNode;
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public List<Workpackage> getPredecessors() {
        return predecessors;
    }

    public List<Workpackage> getSuccessors() {
        return successors;
    }

    // Public methods - Setters
    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    public void setLatestStart(int latestStart) {
        this.latestStart = latestStart;
    }

    public void setEarliestFinish(int earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    public void setLatestFinish(int latestFinish) {
        this.latestFinish = latestFinish;
    }

    public void setSlack(int slack) {
        this.slack = slack;
    }

    public void setStartNode(boolean isStartNode) {
        this.isStartNode = isStartNode;
    }

    public void setEndNode(boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    public void setPredecessors(List<Workpackage> predecessors) {
        this.predecessors = predecessors != null ? predecessors : new ArrayList<>();
    }

    public void setSuccessors(List<Workpackage> successors) {
        this.successors = successors != null ? successors : new ArrayList<>();
    }

    public void addDependency(Workpackage dependency) {
        if (dependency != null && !dependencies.contains(dependency)) {
            dependencies.add(dependency);
            this.isStartNode = false;
        }
    }

    public void addPredecessor(Workpackage predecessor) {
        if (predecessor != null && !predecessors.contains(predecessor)) {
            predecessors.add(predecessor);
        }
    }

    public void addSuccessor(Workpackage successor) {
        if (successor != null && !successors.contains(successor)) {
            successors.add(successor);
        }
    }
}
