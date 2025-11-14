import com.github.skywa04885.MyPriorityQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MyPriorityQueueTests {

    @Test
    @DisplayName("should return empty when dequeue is called on an empty queue")
    void shouldReturnEmptyWhenDequeueCalledOnEmptyQueue() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        Optional<String> result = pq.dequeue();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return the same element when single item is enqueued and dequeued")
    void shouldReturnSameElementWhenSingleItemEnqueuedAndDequeued() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        pq.enqueue(10, "A");
        Optional<String> result = pq.dequeue();

        assertThat(result).contains("A");
    }

    @Test
    @DisplayName("should dequeue elements in increasing priority order")
    void shouldDequeueElementsInIncreasingPriorityOrder() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        pq.enqueue(50, "C");
        pq.enqueue(10, "A");
        pq.enqueue(30, "B");

        assertThat(pq.dequeue()).contains("A");
        assertThat(pq.dequeue()).contains("B");
        assertThat(pq.dequeue()).contains("C");
        assertThat(pq.dequeue()).isEmpty();
    }

    @Test
    @DisplayName("should bubble up elements with lower priority")
    void shouldBubbleUpElementsWithLowerPriority() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        pq.enqueue(100, "X");
        pq.enqueue(90, "Y");
        pq.enqueue(80, "Z");
        pq.enqueue(70, "W"); // should bubble up to root

        assertThat(pq.dequeue()).contains("W");
        assertThat(pq.dequeue()).contains("Z");
        assertThat(pq.dequeue()).contains("Y");
        assertThat(pq.dequeue()).contains("X");
    }

    @Test
    @DisplayName("should bubble down elements after removing root")
    void shouldBubbleDownElementsAfterRemovingRoot() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();

        pq.enqueue(5, 5);
        pq.enqueue(1, 1);
        pq.enqueue(3, 3);
        pq.enqueue(2, 2);
        pq.enqueue(4, 4);

        assertThat(pq.dequeue()).contains(1);
        assertThat(pq.dequeue()).contains(2);
        assertThat(pq.dequeue()).contains(3);
        assertThat(pq.dequeue()).contains(4);
        assertThat(pq.dequeue()).contains(5);
    }

    @Test
    @DisplayName("should handle duplicate priorities correctly")
    void shouldHandleDuplicatePrioritiesCorrectly() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        pq.enqueue(10, "A");
        pq.enqueue(10, "B");
        pq.enqueue(10, "C");

        assertThat(pq.dequeue()).isPresent();
        assertThat(pq.dequeue()).isPresent();
        assertThat(pq.dequeue()).isPresent();
        assertThat(pq.dequeue()).isEmpty();
    }

    @Test
    @DisplayName("should maintain heap correctness during interleaved operations")
    void shouldMaintainHeapCorrectnessDuringInterleavedOperations() {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();

        pq.enqueue(20, "C");
        pq.enqueue(5, "A");
        assertThat(pq.dequeue()).contains("A");

        pq.enqueue(10, "B");
        assertThat(pq.dequeue()).contains("B");

        pq.enqueue(1, "D");
        pq.enqueue(2, "E");
        assertThat(pq.dequeue()).contains("D");
        assertThat(pq.dequeue()).contains("E");

        assertThat(pq.dequeue()).contains("C");
        assertThat(pq.dequeue()).isEmpty();
    }

    @Test
    @DisplayName("should correctly dequeue many elements inserted in reverse order")
    void shouldCorrectlyDequeueManyElementsInsertedInReverseOrder() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        int count = 1000;

        for (int i = count - 1; i >= 0; i--) {
            pq.enqueue(i, i);
        }

        for (int i = 0; i < count; i++) {
            assertThat(pq.dequeue()).contains(i);
        }

        assertThat(pq.dequeue()).isEmpty();
    }
}
