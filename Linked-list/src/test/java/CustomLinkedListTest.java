import org.innowise.CustomLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomLinkedListTest {
    private CustomLinkedList<Integer> list;

    @BeforeEach
    void setUp(){
        list = new CustomLinkedList<>();
    }

    @Test
    void testSize(){
        assertEquals(0, list.size());
        list.addFirst(1);
        assertEquals(1, list.size());
        list.addLast(2);
        assertEquals(2, list.size());
    }

    @Test
    void testAddFirst(){
        list.addFirst(1);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.getLast());

        list.addFirst(2);
        assertEquals(2, list.getFirst());
        assertEquals(1, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddLast() {
        list.addLast(1);
        assertEquals(1, list.getFirst());
        assertEquals(1, list.getLast());

        list.addLast(2);
        assertEquals(1, list.getFirst());
        assertEquals(2, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddAtIndex() {
        list.add(0, 1); // [1]
        list.add(1, 3); // [1, 3]
        list.add(1, 2); // [1, 2, 3]

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void testAddAtIndexInvalid() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));
    }

    @Test
    void testGetFirst() {
        list.addFirst(10);
        assertEquals(10, list.getFirst());

        list.addFirst(20);
        assertEquals(20, list.getFirst());
    }

    @Test
    void testGetFirstEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.getFirst());
    }

    @Test
    void testGetLast() {
        list.addLast(10);
        assertEquals(10, list.getLast());

        list.addLast(20);
        assertEquals(20, list.getLast());
    }

    @Test
    void testGetLastEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.getLast());
    }

    @Test
    void testGet() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testGetInvalidIndex() {
        list.addLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void testRemoveFirst() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());

        assertEquals(2, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(3, list.getFirst());
    }

    @Test
    void testRemoveFirstEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.removeFirst());
    }

    @Test
    void testRemoveLast() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(3, list.removeLast());
        assertEquals(2, list.size());
        assertEquals(2, list.getLast());

        assertEquals(2, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(1, list.getLast());
    }

    @Test
    void testRemoveLastEmptyList() {
        assertThrows(IllegalStateException.class, () -> list.removeLast());
    }

    @Test
    void testRemoveAtIndex() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        assertEquals(2, list.remove(1));
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(4, list.get(2));

        assertEquals(1, list.remove(0));
        assertEquals(2, list.size());
        assertEquals(3, list.getFirst());
    }

    @Test
    void testRemoveAtIndexInvalid() {
        list.addLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void testComplexScenario() {
        // Тестируем различные операции вместе
        list.addFirst(2);     // [2]
        list.addLast(4);      // [2, 4]
        list.add(1, 3);       // [2, 3, 4]
        list.addFirst(1);     // [1, 2, 3, 4]
        list.addLast(5);      // [1, 2, 3, 4, 5]

        assertEquals(5, list.size());
        assertEquals(1, list.getFirst());
        assertEquals(5, list.getLast());

        assertEquals(1, list.removeFirst()); // [2, 3, 4, 5]
        assertEquals(5, list.removeLast());  // [2, 3, 4]
        assertEquals(3, list.remove(1));     // [2, 4]

        assertEquals(2, list.size());
        assertEquals(2, list.getFirst());
        assertEquals(4, list.getLast());
    }
}
