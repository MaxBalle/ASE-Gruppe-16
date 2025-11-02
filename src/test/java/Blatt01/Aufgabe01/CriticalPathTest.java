package Blatt01.Aufgabe01; 

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for CriticalPath.
 * Verifies correct calculation of earliest/latest times, slack, and critical path.
 *
 * Group 16:
 * @author Max Balle (3592845) - st178636@stud.uni-stuttgart.de
 * @author Annalena Schmid (3530959) - st173100@stud.uni-stuttgart.de
 * @author Marie Kufner (3535116) - st173710@stud.uni-stuttgart.de
 * @author Moritz Neubert (3860551) - st199704@stud.uni-stuttgart.de
 * @author Anh Quan Le (3593420) - st177186@stud.uni-stuttgart.de
 * @author Elena Restivo (3594296) - st177463@stud.uni-stuttgart.de
 * @author Martin Huynh (3532274) - st173632@stud.uni-stuttgart.de
 * @author Luca Hirt (3591972) - st178216@stud.uni-stuttgart.de
 * @author Trang Hua (3259405) - st149076@stud.uni-stuttgart.de
 * @author Christoph Alber (3824940) - st196946@stud.uni-stuttgart.de
 */
public class CriticalPathTest {

    /**
     * Builds the exercise network as described in the project example.
     *
     * @return list of work packages forming the project network
     */
    private List<Workpackage> buildExerciseNetwork() {

        Workpackage a01 = new Workpackage("A01", 3, new ArrayList<>());
        Workpackage a02 = new Workpackage("A02", 4, new ArrayList<>());
        Workpackage a03 = new Workpackage("A03", 5, new ArrayList<>());
        Workpackage a04 = new Workpackage("A04", 4, new ArrayList<>(List.of(a01)));
        Workpackage a05 = new Workpackage("A05", 9, new ArrayList<>(List.of(a01)));
        Workpackage a06 = new Workpackage("A06", 4, new ArrayList<>(List.of(a02)));
        Workpackage a07 = new Workpackage("A07", 2, new ArrayList<>(List.of(a03)));
        Workpackage a08 = new Workpackage("A08", 4, new ArrayList<>(List.of(a04)));
        Workpackage a09 = new Workpackage("A09", 2, new ArrayList<>(List.of(a06)));
        Workpackage a10 = new Workpackage("A10", 3, new ArrayList<>(List.of(a06)));
        Workpackage a11 = new Workpackage("A11", 3, new ArrayList<>(List.of(a07)));
        Workpackage a12 = new Workpackage("A12", 2, new ArrayList<>(List.of(a05, a08)));
        Workpackage a13 = new Workpackage("A13", 4, new ArrayList<>(List.of(a10, a11)));
        Workpackage a14 = new Workpackage("A14", 7, new ArrayList<>(List.of(a09, a12)));
        Workpackage a15 = new Workpackage("A15", 2, new ArrayList<>(List.of(a09, a12)));
        Workpackage a16 = new Workpackage("A16", 4, new ArrayList<>(List.of(a13, a15)));

        return List.of(
                a01, a02, a03, a04, a05, a06, a07, a08, a09, a10, a11, a12, a13, a14, a15, a16
        );
    }

    /**
     * Returns the work package with the given ID.
     *
     * @param nodes list of work packages
     * @param id    ID of the desired work package
     * @return matching work package
     * @throws IllegalArgumentException if no work package with the given ID exists
     */
    private static Workpackage byId(List<Workpackage> nodes, String id) {
        return nodes.stream().filter(w -> w.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No work package with ID " + id));
    }

    /**
     * Tests whether earliest/latest times and slack are calculated correctly for each node.
     */
    @Test
    void calculates_expected_times_and_slack() {
        List<Workpackage> nodes = buildExerciseNetwork();
        CriticalPath cp = new CriticalPath();
        cp.setWorkpackages(nodes);

        cp.buildProject();
        cp.calculateCriticalPath();

        Workpackage a01 = byId(nodes, "A01");
        assertEquals(0, a01.getEarliestStart());
        assertEquals(3, a01.getEarliestFinish());
        assertEquals(0, a01.getLatestStart());
        assertEquals(3, a01.getLatestFinish());
        assertEquals(0, a01.getSlack());

        Workpackage a05 = byId(nodes, "A05");
        assertEquals(3, a05.getEarliestStart());
        assertEquals(12, a05.getEarliestFinish());
        assertEquals(3, a05.getLatestStart());
        assertEquals(12, a05.getLatestFinish());
        assertEquals(0, a05.getSlack());

        Workpackage a12 = byId(nodes, "A12");
        assertEquals(12, a12.getEarliestStart());
        assertEquals(14, a12.getEarliestFinish());
        assertEquals(12, a12.getLatestStart());
        assertEquals(14, a12.getLatestFinish());
        assertEquals(0, a12.getSlack());

        Workpackage a14 = byId(nodes, "A14");
        assertEquals(14, a14.getEarliestStart());
        assertEquals(21, a14.getEarliestFinish());
        assertEquals(14, a14.getLatestStart());
        assertEquals(21, a14.getLatestFinish());
        assertEquals(0, a14.getSlack());

        Workpackage a16 = byId(nodes, "A16");
        assertEquals(16, a16.getEarliestStart());
        assertEquals(20, a16.getEarliestFinish());
        assertEquals(17, a16.getLatestStart());
        assertEquals(21, a16.getLatestFinish());
        assertEquals(1, a16.getSlack());
    }

    /**
     * Tests whether the {@code analyze()} method returns the expected project duration and critical path.
     */
    @Test
    @DisplayName("analyze(): Project duration = 21 and critical path A01→A05→A12→A14")
    void analyze_returnsExpectedDurationAndCriticalPath() {
        List<Workpackage> nodes = buildExerciseNetwork();
        CriticalPath cp = new CriticalPath();
        cp.setWorkpackages(nodes);

        CriticalPath.Result result = cp.analyze();

        assertEquals(21, result.getProjectDuration(), "Project duration should be 21");

        List<String> expected = List.of("A01", "A05", "A12", "A14");
        List<String> actual = result.getCriticalPath().stream().map(Workpackage::getId).toList();
        assertEquals(expected, actual, "Critical path is incorrect");
    }

    /**
     * Tests whether {@code analyze()} correctly sets ES/EF/LS/LF and slack values for selected nodes.
     */
    @Test
    @DisplayName("analyze(): ES/EF/LS/LF/Slack for key nodes")
    void analyze_setsExpectedTimesOnNodes() {
        List<Workpackage> nodes = buildExerciseNetwork();
        CriticalPath cp = new CriticalPath();
        cp.setWorkpackages(nodes);

        cp.analyze();

        Workpackage A01 = byId(nodes, "A01");
        assertAll("A01",
                () -> assertEquals(0, A01.getEarliestStart()),
                () -> assertEquals(3, A01.getEarliestFinish()),
                () -> assertEquals(0, A01.getLatestStart()),
                () -> assertEquals(3, A01.getLatestFinish()),
                () -> assertEquals(0, A01.getSlack())
        );

        Workpackage A05 = byId(nodes, "A05");
        assertAll("A05",
                () -> assertEquals(3, A05.getEarliestStart()),
                () -> assertEquals(12, A05.getEarliestFinish()),
                () -> assertEquals(3, A05.getLatestStart()),
                () -> assertEquals(12, A05.getLatestFinish()),
                () -> assertEquals(0, A05.getSlack())
        );

        Workpackage A12 = byId(nodes, "A12");
        assertAll("A12",
                () -> assertEquals(12, A12.getEarliestStart()),
                () -> assertEquals(14, A12.getEarliestFinish()),
                () -> assertEquals(12, A12.getLatestStart()),
                () -> assertEquals(14, A12.getLatestFinish()),
                () -> assertEquals(0, A12.getSlack())
        );

        Workpackage A14 = byId(nodes, "A14");
        assertAll("A14",
                () -> assertEquals(14, A14.getEarliestStart()),
                () -> assertEquals(21, A14.getEarliestFinish()),
                () -> assertEquals(14, A14.getLatestStart()),
                () -> assertEquals(21, A14.getLatestFinish()),
                () -> assertEquals(0, A14.getSlack())
        );

        Workpackage A16 = byId(nodes, "A16");
        assertAll("A16",
                () -> assertEquals(16, A16.getEarliestStart()),
                () -> assertEquals(20, A16.getEarliestFinish()),
                () -> assertEquals(17, A16.getLatestStart()),
                () -> assertEquals(21, A16.getLatestFinish()),
                () -> assertEquals(1, A16.getSlack())
        );
    }
}