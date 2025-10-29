package ase.blatt01.aufgabe01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Calculates the critical path (ES/EF/LS/LF/Slack) for a precedence network.
 * Use {@link #analyze()} to compute results. The Workpackage instances are updated in-place.
 */
public final class CriticalPath {

    // Project structure
    private final List<Workpackage> workPackages = new ArrayList<>();
    private final List<Workpackage> startNodes  = new ArrayList<>();
    private final List<Workpackage> endNodes    = new ArrayList<>();

    /** Optional legacy constructor. Prefer passing a list via setWorkpackages(...). */
    public CriticalPath() { }

    /** Read-only view (useful for testing). */
    public List<Workpackage> getWorkpackages() {
        return Collections.unmodifiableList(workPackages);
    }

    /** Defensive copy with null guard. */
    public void setWorkpackages(final List<Workpackage> workpackages) {
        workPackages.clear();
        if (workpackages != null) {
            workPackages.addAll(workpackages);
        }
    }

    /** Read-only view (if external access is required). */
    public List<Workpackage> getStartNodes() {
        return Collections.unmodifiableList(startNodes);
    }

    /** 
     * Main API method — computes all scheduling parameters and returns a summary result.
     * This method runs: buildProject → calculateEarliestTimes → calculateLatestTimes → calculateSlack.
     */
    public Result analyze() {
        if (workPackages.isEmpty()) {
            return new Result(0, List.of());
        }

        buildProject();           // Build dependency graph and mark start/end nodes
        calculateEarliestTimes(); // Forward pass
        final int duration = calculateLatestTimes(); // Backward pass (also returns project duration)
        calculateSlack();         // Compute slack = LS - ES

        // All work packages with slack == 0 are considered critical
        final List<Workpackage> critical = workPackages.stream()
            .filter(w -> w.getSlack() == 0)
            .sorted((a, b) -> Integer.compare(a.getEarliestStart(), b.getEarliestStart()))
            .toList();

        return new Result(duration, critical);
    }

    // --------------------------- Project setup ---------------------------

    /** 
     * Builds predecessor/successor relationships based on dependencies 
     * and identifies start and end nodes. 
     */
    public void buildProject() {
        startNodes.clear();
        endNodes.clear();

        // Reset all predecessor/successor links
        for (final Workpackage wp : workPackages) {
            wp.setPredecessors(new ArrayList<>());
            wp.setSuccessors(new ArrayList<>());
        }

        // Link predecessors and successors
        for (final Workpackage wp : workPackages) {
            final List<Workpackage> deps = wp.getDependencies();
            if (deps == null) continue;
            for (final Workpackage dep : deps) {
                if (dep == null) continue;
                wp.addPredecessor(dep);
                dep.addSuccessor(wp);
            }
        }

        // Identify start and end nodes
        for (final Workpackage wp : workPackages) {
            final boolean isStart = wp.getPredecessors() == null || wp.getPredecessors().isEmpty();
            final boolean isEnd   = wp.getSuccessors()   == null || wp.getSuccessors().isEmpty();
            wp.setStartNode(isStart);
            wp.setEndNode(isEnd);
            if (isStart) startNodes.add(wp);
            if (isEnd)   endNodes.add(wp);
        }
    }

    // --------------------------- Forward / Backward / Slack ---------------------------

    /** 
     * Forward pass: computes earliest start (ES) and earliest finish (EF) times.
     * Start nodes have ES = 0 and EF = duration.
     */
    private void calculateEarliestTimes() {
        for (final Workpackage start : startNodes) {
            start.setEarliestStart(0);
            start.setEarliestFinish(start.getDuration());
        }

        // For other nodes, iterate until all earliest times stabilize
        boolean changed;
        do {
            changed = false;
            for (final Workpackage wp : workPackages) {
                if (wp.isStartNode()) continue;

                int maxPredFinish = 0;
                final List<Workpackage> preds = wp.getPredecessors();
                if (preds != null) {
                    for (final Workpackage pred : preds) {
                        maxPredFinish = Math.max(maxPredFinish, pred.getEarliestFinish());
                    }
                }

                if (wp.getEarliestStart() != maxPredFinish) {
                    wp.setEarliestStart(maxPredFinish);
                    wp.setEarliestFinish(maxPredFinish + wp.getDuration());
                    changed = true;
                }
            }
        } while (changed);
    }

    /** 
     * Backward pass: computes latest start (LS) and latest finish (LF) times. 
     * Also returns the total project duration (maximum EF among all end nodes).
     */
    private int calculateLatestTimes() {
        // Project duration = max(EF) among end nodes
        int projectDuration = 0;
        for (final Workpackage end : endNodes) {
            projectDuration = Math.max(projectDuration, end.getEarliestFinish());
        }

        // End nodes: LF = projectDuration, LS = LF - duration
        for (final Workpackage end : endNodes) {
            end.setLatestFinish(projectDuration);
            end.setLatestStart(projectDuration - end.getDuration());
        }

        // Other nodes: iterate backward until all latest times stabilize
        boolean changed;
        do {
            changed = false;
            for (final Workpackage wp : workPackages) {
                if (wp.isEndNode()) continue;

                int minSuccStart = Integer.MAX_VALUE;
                final List<Workpackage> succs = wp.getSuccessors();
                if (succs != null && !succs.isEmpty()) {
                    for (final Workpackage succ : succs) {
                        minSuccStart = Math.min(minSuccStart, succ.getLatestStart());
                    }
                } else {
                    // Fallback: if no successor exists (shouldn't happen), align with project duration
                    minSuccStart = projectDuration;
                }

                if (wp.getLatestFinish() != minSuccStart) {
                    wp.setLatestFinish(minSuccStart);
                    wp.setLatestStart(minSuccStart - wp.getDuration());
                    changed = true;
                }
            }
        } while (changed);

        return projectDuration;
    }

    /** 
     * Calculates slack for each work package.
     * Slack = LS - ES.
     */
    private void calculateSlack() {
        for (final Workpackage wp : workPackages) {
            wp.setSlack(wp.getLatestStart() - wp.getEarliestStart());
        }
    }

    // --------------------------- Compatibility API ---------------------------

    /**
     * Legacy method kept for backward compatibility.  
     * Use {@link #analyze()} instead — this method no longer prints anything.
     */
    public void calculateCriticalPath() {
        analyze(); // No console output — I/O should be handled by the caller
    }

    // --------------------------- Result type ---------------------------

    /** Immutable result structure containing project duration and critical path nodes. */
    public static final class Result {
        private final int projectDuration;
        private final List<Workpackage> criticalPath;

        public Result(final int projectDuration, final List<Workpackage> criticalPath) {
            this.projectDuration = projectDuration;
            this.criticalPath = List.copyOf(Objects.requireNonNull(criticalPath, "criticalPath"));
        }

        public int getProjectDuration() {
            return projectDuration;
        }

        public List<Workpackage> getCriticalPath() {
            return criticalPath;
        }
    }
}