package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the critical path in a project network diagram.
 * The critical path is the sequence of work packages that determines the project duration.
 */
public class CriticalPath {
    // Project structure
    private List<Workpackage> workpackages = new ArrayList<>();
    private List<Workpackage> startNodes = new ArrayList<>();
    private List<Workpackage> endNodes = new ArrayList<>();

    // Public methods - Getters and Setters
    public List<Workpackage> getWorkpackages() {
        return workpackages;
    }

    public void setWorkpackages(List<Workpackage> workpackages) {
        this.workpackages = workpackages;
    }

    public List<Workpackage> getStartNodes() {
        return startNodes;
    }

    // Project initialization
    public void buildProject() {
        // Vorherige Verknüpfungen löschen
        startNodes.clear();
        endNodes.clear();

        // Sicherstellen, dass predecessors/successors leer sind
        for (Workpackage wp : workpackages) {
            wp.setPredecessors(new ArrayList<Workpackage>());
            wp.setSuccessors(new ArrayList<Workpackage>());
        }

        // Für jedes Workpackage: alle in depedencies gelisteten Elemente sind dessen Vorgänger
        for (Workpackage wp : workpackages) {
            List<Workpackage> deps = wp.getDependencies();
            if (deps == null) continue;
            for (Workpackage dep : deps) {
                if (dep == null) continue;
                wp.addPredecessor(dep);
                dep.addSuccessor(wp);
            }
        }

        // Start- und Endknoten ermitteln und Flags setzen
        for (Workpackage wp : workpackages) {
            boolean isStart = (wp.getDependencies() == null || wp.getDependencies().isEmpty());
            wp.setStartNode(isStart);
            if (isStart) startNodes.add(wp);

            boolean isEnd = (wp.getSuccessors() == null || wp.getSuccessors().isEmpty());
            wp.setEndNode(isEnd);
            if (isEnd) endNodes.add(wp);
        }
    }
 
    private void calculateEarliestTimes() {
        // Für alle Startknoten: Frühester Start ist 0
        for (Workpackage start : startNodes) {
            start.setEarliestStart(0);
            start.setEarliestFinish(start.getDuration());
        }

        // Für alle anderen Knoten in topologischer Reihenfolge
        boolean changed;
        do {
            changed = false;
            for (Workpackage wp : workpackages) {
                if (wp.isStartNode()) continue;

                // Frühester Start ist das Maximum der frühesten Endzeiten aller Vorgänger
                int maxPredFinish = 0;
                for (Workpackage pred : wp.getPredecessors()) {
                    maxPredFinish = Math.max(maxPredFinish, pred.getEarliestFinish());
                }

                // Wenn sich der früheste Start ändert, aktualisieren
                if (wp.getEarliestStart() != maxPredFinish) {
                    wp.setEarliestStart(maxPredFinish);
                    wp.setEarliestFinish(maxPredFinish + wp.getDuration());
                    changed = true;
                }
            }
        } while (changed);
    }

    private int calculateLatestTimes() {
        // Projektdauer ist das Maximum der frühesten Endzeiten aller Endknoten
        int projectDuration = 0;
        for (Workpackage end : endNodes) {
            projectDuration = Math.max(projectDuration, end.getEarliestFinish());
        }

        // Für alle Endknoten: Spätester End = Projektdauer
        for (Workpackage end : endNodes) {
            end.setLatestFinish(projectDuration);
            end.setLatestStart(projectDuration - end.getDuration());
        }

        // Für alle anderen Knoten rückwärts
        boolean changed;
        do {
            changed = false;
            for (Workpackage wp : workpackages) {
                if (wp.isEndNode()) continue;

                // Spätester End ist das Minimum der spätesten Starts aller Nachfolger
                int minSuccStart = Integer.MAX_VALUE;
                for (Workpackage succ : wp.getSuccessors()) {
                    minSuccStart = Math.min(minSuccStart, succ.getLatestStart());
                }

                // Wenn sich der späteste End ändert, aktualisieren
                if (wp.getLatestFinish() != minSuccStart) {
                    wp.setLatestFinish(minSuccStart);
                    wp.setLatestStart(minSuccStart - wp.getDuration());
                    changed = true;
                }
            }
        } while (changed);

        return projectDuration;
    }

    private void calculateSlack() {
        // Für jedes Workpackage den Puffer berechnen
        for (Workpackage wp : workpackages) {
            wp.setSlack(wp.getLatestStart() - wp.getEarliestStart());
        }
    }

    public void calculateCriticalPath() {
        // Vorwärtspass: Früheste Zeiten
        calculateEarliestTimes();

        // Rückwärtspass: Späteste Zeiten
        int projectDuration = calculateLatestTimes();

        // Puffer berechnen
        calculateSlack();

        // Workpackages auf dem kritischen Pfad haben Puffer 0
        System.out.println("Critical Path (Project Duration: " + projectDuration + "):");
        for (Workpackage wp : workpackages) {
            if (wp.getSlack() == 0) {
                System.out.println("Task " + wp.getId() + 
                    " (Duration: " + wp.getDuration() + 
                    ", ES: " + wp.getEarliestStart() + 
                    ", EF: " + wp.getEarliestFinish() + 
                    ", LS: " + wp.getLatestStart() + 
                    ", LF: " + wp.getLatestFinish() + ")");
            }
        }
    }
}
