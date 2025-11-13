import com.github.skywa04885.MySortAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface SortAlgorithmTests {
    MySortAlgorithm createSortAlgorithm();

    @Test
    @DisplayName("Should sort empty array")
    default void shouldSortEmptyArray() {
        final MySortAlgorithm sortAlgorithm = createSortAlgorithm();

        final Integer[] unsorted = {};

        final Integer[] sorted = sortAlgorithm.sort(unsorted);

        assertThat(sorted).isEmpty();
    }

    @Test
    @DisplayName("Should sort integers")
    default void shouldSortIntegers() {
        final MySortAlgorithm sortAlgorithm = createSortAlgorithm();

        final Integer[] unsorted = {
                27, -83, 14, 99, -42, 0, -7, 63, -100, 58,
                -34, 72, -19, 5, 88, -56, 31, -3, 100, -27,
                44, -68, 12, -95, 7, -11, 53, -22, 96, -74
        };

        final Integer[] sorted = sortAlgorithm.sort(unsorted);

        assertThat(sorted).containsExactly(
                -100, -95, -83, -74, -68, -56, -42, -34, -27, -22,
                -19, -11, -7, -3, 0, 5, 7, 12, 14, 27,
                31, 44, 53, 58, 63, 72, 88, 96, 99, 100
        );
    }

    @Test
    @DisplayName("Should sort floats")
    default void shouldSortFloats() {
        final MySortAlgorithm sortAlgorithm = createSortAlgorithm();

        final Float[] unsorted = {
                -32.5f, 77.1f, -4.8f, 99.3f, -56.2f, 13.4f, -88.9f, 45.6f,
                0.0f, -17.3f, 62.4f, -100.0f, 38.9f, -73.2f, 81.5f, 5.7f,
                -22.8f, 94.2f, -11.6f, 100.0f, -66.4f, 27.3f, 12.1f, -95.7f,
                53.8f, -7.4f, 68.2f, -34.1f, 8.9f, 41.0f
        };

        final Float[] sorted = sortAlgorithm.sort(unsorted);

        assertThat(sorted).containsExactly(
                -100.0f, -95.7f, -88.9f, -73.2f, -66.4f, -56.2f, -34.1f, -32.5f,
                -22.8f, -17.3f, -11.6f, -7.4f, -4.8f, 0.0f, 5.7f, 8.9f,
                12.1f, 13.4f, 27.3f, 38.9f, 41.0f, 45.6f, 53.8f, 62.4f,
                68.2f, 77.1f, 81.5f, 94.2f, 99.3f, 100.0f
        );
    }
}
