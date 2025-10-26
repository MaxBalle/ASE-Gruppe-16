package Blatt01.Aufgabe01;

import java.util.ArrayList;
import java.util.List;

public class CriticPath {
	private List<Workpackage> workpackages = new ArrayList<Workpackage>();
    private List<Workpackage> startNodes = new ArrayList<Workpackage>();
	private List<Workpackage> endNodes = new ArrayList<Workpackage>();

    public List<Workpackage> getStartNodes() {
		return startNodes;
	}

	public void setStartNodes(List<Workpackage> startNodes) {
		this.startNodes = startNodes;

	}
}
