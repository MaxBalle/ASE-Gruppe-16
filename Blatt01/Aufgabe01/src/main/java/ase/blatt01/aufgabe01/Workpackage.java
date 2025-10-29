package ase.blatt01.aufgabe01;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single work package (task) in a project network.
 * Each work package has timing information and relationships to others,
 * used for Critical Path Method (CPM) analysis.
 */
public class Workpackage {

    // --------------------------- Basic attributes ---------------------------

    private final String id;          // Unique identifier of the work package
    private final int duration;       // Duration (time units) required for completion

    private boolean isStartNode;      // True if the work package has no predecessors
    private boolean isEndNode;        // True if the work package has no successors

    // --------------------------- Network structure ---------------------------

    private List<Workpackage> dependencies; // Direct dependencies (input from outside)
    private List<Workpackage> predecessors; // Calculated during project setup
    private List<Workpackage> successors;   // Calculated during project setup

    // --------------------------- Scheduling attributes ---------------------------

    private int earliestStart;  // Earliest possible start time (ES)
    private int earliestFinish; // Earliest possible finish time (EF)
    private int latestStart;    // Latest allowed start time (LS)
    private int latestFinish;   // Latest allowed finish time (LF)
    private int slack;          // Slack time = LS - ES

    // --------------------------- Constructors ---------------------------

    /**
     * Creates a new work package with the specified properties.
     *
     * @param id           Unique identifier for this work package
     * @param duration     Duration (time units) required to complete the task
     * @param dependencies Other work packages that must finish before this one starts
     */
    public Workpackage(final String id, final int duration, final List<Workpackage> dependencies) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.duration = duration;
        this.dependencies = dependencies != null ? dependencies : new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.isStartNode = this.dependencies.isEmpty();
        this.isEndNode = false;
    }

    // --------------------------- Getters ---------------------------

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public List<Workpackage> getDependencies() {
        return dependencies;
    }

    public List<Workpackage> getPredecessors() {
        return predecessors;
    }

    public List<Workpackage> getSuccessors() {
        return successors;
    }

    public int getEarliestStart() {
        return earliestStart;
    }

    public int getEarliestFinish() {
        return earliestFinish;
    }

    public int getLatestStart() {
        return latestStart;
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

    // --------------------------- Setters ---------------------------

    public void setEarliestStart(final int earliestStart) {
        this.earliestStart = earliestStart;
    }

    public void setEarliestFinish(final int earliestFinish) {
        this.earliestFinish = earliestFinish;
    }

    public void setLatestStart(final int latestStart) {
        this.latestStart = latestStart;
    }

    public void setLatestFinish(final int latestFinish) {
        this.latestFinish = latestFinish;
    }

    public void setSlack(final int slack) {
        this.slack = slack;
    }

    public void setStartNode(final boolean isStartNode) {
        this.isStartNode = isStartNode;
    }

    public void setEndNode(final boolean isEndNode) {
        this.isEndNode = isEndNode;
    }

    public void setPredecessors(final List<Workpackage> predecessors) {
        this.predecessors = predecessors != null ? predecessors : new ArrayList<>();
    }

    public void setSuccessors(final List<Workpackage> successors) {
        this.successors = successors != null ? successors : new ArrayList<>();
    }

    // --------------------------- Relationship helpers ---------------------------

    /**
     * Adds a dependency (another work package that must finish first).
     * Automatically marks this node as non-start.
     */
    public void addDependency(final Workpackage dependency) {
        if (dependency != null && !dependencies.contains(dependency)) {
            dependencies.add(dependency);
            this.isStartNode = false;
        }
    }

    /** Adds a predecessor link (used during project setup). */
    public void addPredecessor(final Workpackage predecessor) {
        if (predecessor != null && !predecessors.contains(predecessor)) {
            predecessors.add(predecessor);
        }
    }

    /** Adds a successor link (used during project setup). */
    public void addSuccessor(final Workpackage successor) {
        if (successor != null && !successors.contains(successor)) {
            successors.add(successor);
        }
    }

    // --------------------------- Utility methods ---------------------------

    @Override
    public String toString() {
        return String.format(
            "Workpackage{id='%s', duration=%d, ES=%d, EF=%d, LS=%d, LF=%d, slack=%d}",
            id, duration, earliestStart, earliestFinish, latestStart, latestFinish, slack
        );
    }
}