import com.github.skywa04885.MyArrayList;
import com.github.skywa04885.MyLinkedList;
import com.github.skywa04885.MyList;
import org.junit.jupiter.api.DisplayName;

@DisplayName("MyArrayList Tests")
public class MyArrayListTests implements ListTests {
    @Override
    public MyList<Integer> createList() {
        return new MyArrayList<>(Integer.class);
    }
}
