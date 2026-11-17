	package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("GCD zero iterations: x 13-20, y 13-15")
    void gcdZeroIterations() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("GCD one iteration: x 13-16, 17-20; y 13-16, 13-17; tmp 16-18")
    void gcdOneIteration() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }

    @Test
    @DisplayName("GCD multiple iterations: x 17-16; y 18-15, 18-16, 18-17")
    void gcdMultipleIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
        @Test
    @DisplayName("GCD four iterations: repeated def-clear paths")
    void gcdFourIterations() {
        assertEquals(1, DataFlowTargets.gcd(8, 5));
    }
}

