import com.github.skywa04885.MyMergeSort;
import com.github.skywa04885.MySortAlgorithm;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyMergeSort Tests")
public class MyMergeSortTests implements SortAlgorithmTests {
    @Override
    public MySortAlgorithm createSortAlgorithm() {
        return MyMergeSort.getInstance();
    }
}
