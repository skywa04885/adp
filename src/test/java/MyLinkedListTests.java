import com.github.skywa04885.MyLinkedList;
import com.github.skywa04885.MyList;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyLinkedList Tests")
public class MyLinkedListTests implements MyListTests {
    @Override
    public MyList<Integer> createList() {
        return new MyLinkedList<>();
    }
}
