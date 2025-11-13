import com.github.skywa04885.MyBubbleSort;
import com.github.skywa04885.MyMergeSort;
import com.github.skywa04885.MySortAlgorithm;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyMergeSort Tests")
public class MyBubbleSortTests implements SortAlgorithmTests {
    @Override
    public MySortAlgorithm createSortAlgorithm() {
        return MyBubbleSort.getInstance();
    }
}
