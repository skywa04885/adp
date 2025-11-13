import com.github.skywa04885.MyLinkedList;
import com.github.skywa04885.MyList;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyLinkedList Tests")
public class MyLinkedListTests implements ListTests {
    @Override
    public MyList<Integer> createList() {
        return new MyLinkedList<>();
    }
}
