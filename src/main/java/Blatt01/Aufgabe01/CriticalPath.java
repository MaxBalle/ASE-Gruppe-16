package Blatt01.Aufgabe01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Calculates the critical path of a project network based on work packages
 * and their dependency relations.
 */
public final class CriticalPath {

    private final List<Workpackage> workPackages = new ArrayList<>();
    private final List<Workpackage> startNodes  = new ArrayList<>();
    private final List<Workpackage> endNodes    = new ArrayList<>();

    /** Optional legacy constructor. Prefer passing a list via setWorkpackages(...). */
    public CriticalPath() { }

    /**
     * Returns all work packages that are part of this project/network.
     *
     * @return list of work packages
     */
    public List<Workpackage> getWorkpackages() {
        return Collections.unmodifiableList(workPackages);
    }

    /**
     * Sets the list of work packages to be analyzed.
     *
     * @param workpackages list of work packages (must not be {@code null})
     */
    public void setWorkpackages(final List<Workpackage> workpackages) {
        workPackages.clear();
        if (workpackages != null) {
            workPackages.addAll(workpackages);
        }
    }

    /**
     * Returns all start nodes (work packages without predecessors).
     *
     * @return list of start nodes
     */
    public List<Workpackage> getStartNodes() {
        return Collections.unmodifiableList(startNodes);
    }

    /**
     * Executes the full analysis and returns the project duration together with the
     * list of work packages that form the critical path.
     *
     * @return result object containing duration and critical path
     */
    public Result analyze() {
        if (workPackages.isEmpty()) {
            return new Result(0, List.of());
        }

        buildProject();           
        calculateEarliestTimes(); 
        final int duration = calculateLatestTimes(); 
        calculateSlack();         

        
        final List<Workpackage> critical = workPackages.stream()
            .filter(w -> w.getSlack() == 0)
            .sorted((a, b) -> Integer.compare(a.getEarliestStart(), b.getEarliestStart()))
            .toList();

        return new Result(duration, critical);
    }

    
    
    /**
     * Initializes the project graph from the dependency information of each work package.
     * Links predecessors/successors and identifies start and end nodes.
     */
    public void buildProject() {
        startNodes.clear();
        endNodes.clear();

  
        for (final Workpackage wp : workPackages) {
            wp.setPredecessors(new ArrayList<>());
            wp.setSuccessors(new ArrayList<>());
        }


        for (final Workpackage wp : workPackages) {
            final List<Workpackage> deps = wp.getDependencies();
            if (deps == null) continue;
            for (final Workpackage dep : deps) {
                if (dep == null) continue;
                wp.addPredecessor(dep);
                dep.addSuccessor(wp);
            }
        }


        for (final Workpackage wp : workPackages) {
            final boolean isStart = wp.getPredecessors() == null || wp.getPredecessors().isEmpty();
            final boolean isEnd   = wp.getSuccessors()   == null || wp.getSuccessors().isEmpty();
            wp.setStartNode(isStart);
            wp.setEndNode(isEnd);
            if (isStart) startNodes.add(wp);
            if (isEnd)   endNodes.add(wp);
        }
    }



    /**
     * Performs the forward pass and calculates earliest start (ES) and earliest finish (EF)
     * for all work packages.
     */
    private void calculateEarliestTimes() {
        for (final Workpackage start : startNodes) {
            start.setEarliestStart(0);
            start.setEarliestFinish(start.getDuration());
        }


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
     * Performs the backward pass and calculates latest start (LS) and latest finish (LF)
     * for all work packages.
     * The method also determines the total project duration.
     *
     * @return project duration (in time units)
     */
    private int calculateLatestTimes() {
        
        int projectDuration = 0;
        for (final Workpackage end : endNodes) {
            projectDuration = Math.max(projectDuration, end.getEarliestFinish());
        }

        
        for (final Workpackage end : endNodes) {
            end.setLatestFinish(projectDuration);
            end.setLatestStart(projectDuration - end.getDuration());
        }


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
     * Calculates the total slack for every work package.
     * Slack is defined as (latest start - earliest start).
     */
    private void calculateSlack() {
        for (final Workpackage wp : workPackages) {
            wp.setSlack(wp.getLatestStart() - wp.getEarliestStart());
        }
    }

    /**
     * Runs the complete calculation and prints the critical path to the console.
     * Intended for manual/console-based use.
     */
    public void calculateCriticalPath() {
        analyze(); 
    }

    /**
     * Immutable result of a critical path analysis.
     */
    public static final class Result {
        private final int projectDuration;
        private final List<Workpackage> criticalPath;

        /**
         * Creates a result with project duration and critical path list.
         *
         * @param projectDuration total project duration
         * @param criticalPath list of work packages on the critical path
         */
        public Result(final int projectDuration, final List<Workpackage> criticalPath) {
            this.projectDuration = projectDuration;
            this.criticalPath = List.copyOf(Objects.requireNonNull(criticalPath, "criticalPath"));
        }

        /**
         * Returns the total project duration.
         *
         * @return project duration
         */
        public int getProjectDuration() {
            return projectDuration;
        }

        /**
         * Returns the list of work packages on the critical path.
         *
         * @return list of critical work packages
         */
        public List<Workpackage> getCriticalPath() {
            return criticalPath;
        }
    }
}