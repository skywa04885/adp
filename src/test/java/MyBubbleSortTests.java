import com.github.skywa04885.MyBubbleSort;
import com.github.skywa04885.MySorter;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyBubbleSort Tests")
public class MyBubbleSortTests implements MySorterTests {
    @Override
    public MySorter createSortAlgorithm() {
        return MyBubbleSort.getInstance();
    }
}
