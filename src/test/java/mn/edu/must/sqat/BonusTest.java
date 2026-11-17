
package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusTest {

    @Test
    @DisplayName("All-defs: missed day, streak reset, score 49 to 56")
    void bonusAllDefsNoReward() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 1, 0, 1}, 5));
    }

    @Test
    @DisplayName("All-defs: long streak, score 51 to 54 to 56")
    void bonusAllDefsFullReward() {
        assertEquals(8, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 5));
    }
    
    @Test
    void bonusNullAttendance() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(null, 3)
        );
    }

    @Test
    void bonusNegativeThreshold() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1, 0, 1}, -1)
        );
    }
        @Test
    void bonusLongStreakBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 6));
    }
        @Test
    void bonusAttendanceThresholdOnly() {
        assertEquals(5, DataFlowTargets.bonus(
                new int[]{1, 0, 1}, 2));
    }

    @Test
    void bonusNoAttendance() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{0, 0, 0}, 1));
    }
        @Test
    void bonusEmptyAttendance() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{}, 1));
    }

    @Test
    void bonusSeparatedAttendanceNoStreakReward() {
        assertEquals(0, DataFlowTargets.bonus(
            new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1}, 6));
    }
}

