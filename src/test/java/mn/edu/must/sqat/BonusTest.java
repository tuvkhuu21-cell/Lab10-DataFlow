
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
}

