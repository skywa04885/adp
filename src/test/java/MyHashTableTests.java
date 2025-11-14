import com.github.skywa04885.MyHashTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.within;

class MyHashTableTests {
    @Test
    @DisplayName("should insert and retrieve values")
    void shouldInsertAndRetrieveValues() {
        final MyHashTable<String, Integer> table = new MyHashTable<>();

        table.insert("A", 1);
        table.insert("B", 2);

        assertThat(table.get("A")).contains(1);
        assertThat(table.get("B")).contains(2);
    }

    @Test
    @DisplayName("should return empty optional for missing keys")
    void shouldReturnEmptyOptionalForMissingKeys() {
        final MyHashTable<String, Integer> table = new MyHashTable<>();

        assertThat(table.get("missing")).isEmpty();
    }

    @Test
    @DisplayName("should overwrite value when key already exists")
    void shouldOverwriteValueWhenKeyExists() {
        final MyHashTable<String, Integer> table = new MyHashTable<>();

        table.insert("A", 10);
        table.insert("A", 20);

        assertThat(table.get("A")).contains(20);
    }

    @Test
    @DisplayName("should delete existing key and return its value")
    void shouldDeleteKeyAndReturnValue() {
        final MyHashTable<String, String> table = new MyHashTable<>();

        table.insert("X", "value");
        final Optional<String> deleted = table.delete("X");

        assertThat(deleted).contains("value");
        assertThat(table.get("X")).isEmpty();
    }

    @Test
    @DisplayName("should return empty when deleting non-existing key")
    void shouldReturnEmptyWhenDeletingNonExistingKey() {
        final MyHashTable<String, String> table = new MyHashTable<>();

        assertThat(table.delete("nope")).isEmpty();
    }

    @Test
    @DisplayName("should handle collisions via chaining")
    void shouldHandleCollisionsWithChaining() {
        final MyHashTable<Integer, String> table = new MyHashTable<>(1);

        table.insert(1, "one");
        table.insert(2, "two");
        table.insert(3, "three");

        assertThat(table.get(1)).contains("one");
        assertThat(table.get(2)).contains("two");
        assertThat(table.get(3)).contains("three");
    }

    @Test
    @DisplayName("should rehash and still contain all entries")
    void shouldRehashAndRetainEntries() {
        final MyHashTable<Integer, Integer> table = new MyHashTable<>(2);

        table.insert(1, 1);
        table.insert(2, 2);
        table.insert(3, 3); // loadFactor > 0.75 → triggers rehash

        assertThat(table.get(1)).contains(1);
        assertThat(table.get(2)).contains(2);
        assertThat(table.get(3)).contains(3);
    }

    @Test
    @DisplayName("should compute load factor correctly")
    void shouldComputeLoadFactorCorrectly() {
        final MyHashTable<String, String> table = new MyHashTable<>(4);

        table.insert("A", "a");
        table.insert("B", "b");

        assertThat(table.loadFactor()).isEqualTo(0.5);
    }

    @Test
    @DisplayName("should correctly handle negative hash codes")
    void shouldHandleNegativeHashCodes() {
        final MyHashTable<Object, String> table = new MyHashTable<>(8);

        final Object weirdKey = new Object() {
            @Override
            public int hashCode() {
                return -123456789;
            }

            @Override
            public boolean equals(final Object obj) {
                return this == obj;
            }
        };

        table.insert(weirdKey, "works");

        assertThat(table.get(weirdKey)).contains("works");
    }

    @Test
    @DisplayName("should decrease size on deletion")
    void shouldDecreaseSizeOnDeletion() {
        final MyHashTable<String, Integer> table = new MyHashTable<>();

        table.insert("A", 1);
        table.insert("B", 2);
        table.delete("A");

        assertThat(table.loadFactor()).isCloseTo(1.0 / 16.0, within(0.0001));
    }
}
