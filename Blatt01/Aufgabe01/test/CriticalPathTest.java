package test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;


import main.CriticalPath;
import main.Workpackage;


public class CriticalPathTest {
    private static Workpackage a01 = new Workpackage("A01", 3, new ArrayList<>());
	private static Workpackage a02 = new Workpackage("A02", 4, new ArrayList<>());
	private static Workpackage a03 = new Workpackage("A03", 5, new ArrayList<>());
	private static Workpackage a04 = new Workpackage("A04", 4, new ArrayList<>(List.of(a01)));
	private static Workpackage a05 = new Workpackage("A05", 9, new ArrayList<>(List.of(a01)));
	private static Workpackage a06 = new Workpackage("A06", 4, new ArrayList<>(List.of(a02)));
	private static Workpackage a07 = new Workpackage("A07", 2, new ArrayList<>(List.of(a03)));
	private static Workpackage a08 = new Workpackage("A08", 4, new ArrayList<>(List.of(a04)));
	private static Workpackage a09 = new Workpackage("A09", 2, new ArrayList<>(List.of(a06)));
	private static Workpackage a10 = new Workpackage("A10", 3, new ArrayList<>(List.of(a06)));
    private static Workpackage a11 = new Workpackage("A11", 3, new ArrayList<>(List.of(a07)));
	private static Workpackage a12 = new Workpackage("A12", 2, new ArrayList<>(List.of(a05, a08)));
	private static Workpackage a13 = new Workpackage("A13", 4, new ArrayList<>(List.of(a10, a11)));
	private static Workpackage a14 = new Workpackage("A14", 7, new ArrayList<>(List.of(a09, a12)));
	private static Workpackage a15 = new Workpackage("A15", 2, new ArrayList<>(List.of(a09, a12)));
	private static Workpackage a16 = new Workpackage("A16", 4, new ArrayList<>(List.of(a13, a15)));


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CriticalPath path = new Project();

		graph.getStartNodes().add(a);
		graph.getStartNodes().add(b);
		graph.getStartNodes().add(c);

		a.addSuccessor(f).addSuccessor(h);
		a.addSuccessor(g).addSuccessor(i);
		b.addSuccessor(d).addSuccessor(j);
		c.addSuccessor(e).addSuccessor(j);

		graph.computeCriticalPath();
	}
}
