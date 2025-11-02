package Blatt01.Aufgabe01;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single workpackage.
 * Each work package contains timing information and dependency relations
 * used to calculate the critical path.
 */
public class Workpackage {

    private String id;
    private int duration;
    private boolean isStartNode;
    private boolean isEndNode;
    private List<Workpackage> dependencies;
    private List<Workpackage> predecessors;
    private List<Workpackage> successors;
    private int earliestStart;
    private int earliestFinish;
    private int latestStart;
    private int latestFinish;
    private int slack;

    /**
     * Creates a new work package.
     *
     * @param id           the unique identifier of the work package
     * @param duration     the duration of this work package
     * @param dependencies list of work packages that must be completed before this one
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

    /**
     * Returns the ID of this work package.
     *
     * @return unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the duration of this work package.
     *
     * @return duration value
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the dependencies of this work package.
     *
     * @return list of dependent work packages
     */
    public List<Workpackage> getDependencies() {
        return dependencies;
    }

    /**
     * Returns the earliest start time (ES).
     *
     * @return earliest start time
     */
    public int getEarliestStart() {
        return earliestStart;
    }

    /**
     * Returns the latest start time (LS).
     *
     * @return latest start time
     */
    public int getLatestStart() {
        return latestStart;
    }

    /**
     * Returns the earliest finish time (EF).
     *
     * @return earliest finish time
     */
    public int getEarliestFinish() {
        return earliestFinish;
    }

    /**
     * Returns the latest finish time (LF).
     *
     * @return latest finish time
     */
    public int getLatestFinish() {
        return latestFinish;
    }

    /**
     * Returns the calculated slack (float) time.
     *
     * @return slack value
     */
    public int getSlack() {
        return slack;
    }

    /**
     * Returns whether this work package is a start node.
     *
     * @return true if no predecessors exist
     */
    public boolean isStartNode() {
        return isStartNode;
    }

    /**
     * Returns whether this work package is an end node.
     *
     * @return true if no successors exist
     */
    public boolean isEndNode() {
        return isEndNode;
    }

    /**
     * Returns the list of predecessor work packages.
     *
     * @return list of predecessors
     */
    public List<Workpackage> getPredecessors() {
        return predecessors;
    }

    /**
     * Returns the list of successor work packages.
     *
     * @return list of successors
     */
    public List<Workpackage> getSuccessors() {
        return successors;
    }

    /**
     * Sets the earliest start time.
     *
     * @param earliestStart earliest start value
     */
    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    /**
     * Sets the latest start time.
     *
     * @param latestStart latest start value
     */
    public void setLatestStart(int latestStart) {
        this.latestStart = latestStart;
    }

    /**
     * Sets the earliest finish time.
     *
     * @param earliestFinish earliest finish value
     */
    public void setEarliestFinish(int earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    /**
     * Sets the latest finish time.
     *
     * @param latestFinish latest finish value
     */
    public void setLatestFinish(int latestFinish) {
        this.latestFinish = latestFinish;
    }

    /**
     * Sets the slack (float) time.
     *
     * @param slack slack value
     */
    public void setSlack(int slack) {
        this.slack = slack;
    }

    /**
     * Defines whether this package is a start node.
     *
     * @param isStartNode true if this is a start node
     */
    public void setStartNode(boolean isStartNode) {
        this.isStartNode = isStartNode;
    }

    /**
     * Defines whether this package is an end node.
     *
     * @param isEndNode true if this is an end node
     */
    public void setEndNode(boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    /**
     * Sets the list of predecessor nodes.
     *
     * @param predecessors list of predecessors
     */
    public void setPredecessors(List<Workpackage> predecessors) {
        this.predecessors = predecessors != null ? predecessors : new ArrayList<>();
    }

    /**
     * Sets the list of successor nodes.
     *
     * @param successors list of successors
     */
    public void setSuccessors(List<Workpackage> successors) {
        this.successors = successors != null ? successors : new ArrayList<>();
    }

    /**
     * Adds a dependency to this work package.
     *
     * @param dependency the dependency to add
     */
    public void addDependency(Workpackage dependency) {
        if (dependency != null && !dependencies.contains(dependency)) {
            dependencies.add(dependency);
            this.isStartNode = false;
        }
    }

    /**
     * Adds a predecessor node.
     *
     * @param predecessor the predecessor to add
     */
    public void addPredecessor(Workpackage predecessor) {
        if (predecessor != null && !predecessors.contains(predecessor)) {
            predecessors.add(predecessor);
        }
    }

    /**
     * Adds a successor node.
     *
     * @param successor the successor to add
     */
    public void addSuccessor(Workpackage successor) {
        if (successor != null && !successors.contains(successor)) {
            successors.add(successor);
        }
    }
}