import com.github.skywa04885.MyMergeSort;
import com.github.skywa04885.MySorter;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyMergeSort Tests")
public class MyMergeSortTests implements MySorterTests {
    @Override
    public MySorter createSortAlgorithm() {
        return MyMergeSort.getInstance();
    }
}
