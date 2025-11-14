import com.github.skywa04885.MyList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Interface containing all the tests for list implementations.
 */
public interface MyListTests {
    MyList<Integer> createList();

    @Test
    @DisplayName("Should be empty when created")
    default void shouldBeEmptyWhenCreated() {
        final MyList<Integer> list = createList();

        assertThat(list.size()).isEqualTo(0);
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should add value")
    default void shouldAddValue() {
        final MyList<Integer> list = createList();

        list.add(42);

        assertThat(list.size()).isEqualTo(1);
        assertThat(list.isEmpty()).isFalse();

        assertThat(list.get(0)).isEqualTo(42);
    }

    @Test
    @DisplayName("Should add values")
    default void shouldAddValues() {
        final MyList<Integer> list = createList();

        list.addAll(7, 2, 6);

        assertThat(list.size()).isEqualTo(3);
        assertThat(list.isEmpty()).isFalse();

        assertThat(list.get(0)).isEqualTo(7);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(6);
    }

    @Test
    @DisplayName("Should generate string")
    default void shouldGenerateString() {
        final MyList<Integer> list = createList();

        list.addAll(3, 5, 7);

        assertThat(list.toString()).isEqualTo("{3, 5, 7}");
    }

    @Test
    @DisplayName("Should remove only value")
    default void shouldRemoveOnlyValue() {
        final MyList<Integer> list = createList();

        list.add(42);

        list.remove(42);

        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should be able to add after going empty")
    default void shouldBeAbleTOAddAfterGoingEmpty() {
        final MyList<Integer> list = createList();

        list.add(0);

        list.remove(0);

        list.add(10);

        assertThat(list.get(0)).isEqualTo(10);
    }

    @Test
    @DisplayName("Should remove inner value")
    default void shouldRemoveInnerValue() {
        final MyList<Integer> list = createList();

        list.addAll(82, 72, 64, 21, 54, 67, 86, 122, 54);

        assertThat(list.remove(67)).isTrue();

        assertThat(list.size()).isEqualTo(8);
        assertThat(list.toArray(new Integer[0])).containsExactly(82, 72, 64, 21, 54, 86, 122, 54);
    }

    @Test
    @DisplayName("Should generate string for empty list")
    default void shouldGenerateStringForEmptyList() {
        final MyList<Integer> list = createList();

        assertThat(list.toString()).isEqualTo("{}");
    }

    @Test
    @DisplayName("Should remove head value")
    default void shouldRemoveHeadValue() {
        final MyList<Integer> list = createList();

        list.addAll(97, 34, 21);

        assertThat(list.remove(97)).isTrue();

        assertThat(list.size()).isEqualTo(2);
        assertThat(list.toArray(new Integer[0])).containsExactly(34, 21);
    }

    @Test
    @DisplayName("Should remove tail value")
    default void shouldRemoveTailValue() {
        final MyList<Integer> list = createList();

        list.addAll(97, 34, 21);

        assertThat(list.remove(21)).isTrue();

        assertThat(list.size()).isEqualTo(2);
        assertThat(list.toArray(new Integer[0])).containsExactly(97, 34);
    }

    @Test
    @DisplayName("Should not remove non existent value")
    default void shouldNotRemoveNonExistentValue() {
        final MyList<Integer> list = createList();

        list.addAll(82, 72, 64, 21);

        assertThat(list.remove(67)).isFalse();

        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Should remove only first occurrence")
    default void shouldRemoveOnlyFirstOccurrence() {
        final MyList<Integer> list = createList();

        list.addAll(5, 7, 7, 9);

        list.remove(7);

        assertThat(list.toArray(new Integer[0])).containsExactly(5, 7, 9);
    }

    @Test
    @DisplayName("Should throw when index out of bounds")
    default void shouldThrowWhenIndexOutOfBounds() {
        final MyList<Integer> list = createList();

        list.add(1);

        assertThatThrownBy(() -> list.get(1))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("Should handle many insertions")
    default void shouldHandleManyInsertions() {
        final MyList<Integer> list = createList();
        for (int i = 0; i < 1000; i++) list.add(i);
        assertThat(list.size()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Should return index of existing value")
    default void shouldReturnIndexOfExistingValue() {
        final MyList<Integer> list = createList();

        list.addAll(10, 20, 30);

        assertThat(list.indexOf(10)).isEqualTo(0);
        assertThat(list.indexOf(20)).isEqualTo(1);
        assertThat(list.indexOf(30)).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return index of first occurrence when duplicates exist")
    default void shouldReturnIndexOfFirstOccurrenceWhenDuplicatesExist() {
        final MyList<Integer> list = createList();

        list.addAll(5, 7, 7, 9);

        assertThat(list.indexOf(7)).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return minus one when value does not exist")
    default void shouldReturnMinusOneWhenValueDoesNotExist() {
        final MyList<Integer> list = createList();

        list.addAll(1, 2, 3);

        assertThat(list.indexOf(999)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should return minus one on empty list")
    default void shouldReturnMinusOneOnEmptyList() {
        final MyList<Integer> list = createList();

        assertThat(list.indexOf(5)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should return index for head and tail values")
    default void shouldReturnIndexForHeadAndTailValues() {
        final MyList<Integer> list = createList();

        list.addAll(42, 100, 200);

        assertThat(list.indexOf(42)).isEqualTo(0);
        assertThat(list.indexOf(200)).isEqualTo(2);
    }

}
