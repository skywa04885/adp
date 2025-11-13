import com.github.skywa04885.MyBinarySearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MyBinarySearch Tests")
public class MyBinarySearchTests {
    @Test
    @DisplayName("Should find in even array")
    public void shouldFindInEvenArray() {
        final Integer[] sorted = { 100, 40, 20, 10, 5, 2, -8, -100 };

        final int index = MyBinarySearch.binarySearch(sorted, 10);

        assertThat(index).isEqualTo(3);
    }

    @Test
    @DisplayName("Should find in odd array")
    public void shouldFindInOddArray() {
        final Integer[] sorted = { 100, 40, 20, 10, 5, 2, -8 };

        final int index = MyBinarySearch.binarySearch(sorted, 10);

        assertThat(index).isEqualTo(3);
    }

    @Test
    @DisplayName("Should return negative one if not in array")
    public void shouldReturnNegativeOneIfNotInArray() {
        final Integer[] sorted = { 100, 40, 20, 10, 5, 2, -8 };

        final int index = MyBinarySearch.binarySearch(sorted, -6);

        assertThat(index).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should return negative one for empty array")
    public void shouldReturnNegativeOneForEmptyArray() {
        final Integer[] empty = {};

        final int index = MyBinarySearch.binarySearch(empty, 1);

        assertThat(index).isEqualTo(-1);
    }
}
