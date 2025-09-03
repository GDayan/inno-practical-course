import org.innowise.MyLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyLinkedListTest {
    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void testAddFirstAndGetFirst(){
        list.addFirst(10);
        assertEquals(10, list.getFirst());
        list.addFirst(20);
        assertEquals(20,list.getFirst());
        assertEquals(2, list.size());
    }

    @Test
    void testAddLastAndGetLast(){
        list.addLast(40);
        assertEquals(40, list.getLast());
        list.addLast(50);
        assertEquals(50, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddByIndex(){
        list.addFirst(40);
        list.addFirst(20);
        list.addFirst(10);
        list.add(2, 30);
        assertEquals(30, list.get(2));
        assertEquals(4, list.size());
    }

    @Test
    void testGetByIndex(){
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testRemoveFirst(){
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        int remove = list.removeFirst();
        assertEquals(10, remove);
        assertEquals(2, list.size());
        assertEquals(20, list.getFirst());
    }

    @Test
    void testRemoveLast(){
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        int remove = list.removeLast();
        assertEquals(30, remove);
        assertEquals(2, list.size());
        assertEquals(20, list.getLast());
    }

    @Test
    void testRemoveByIndex(){
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        int remove = list.remove(1);
        assertEquals(20, remove);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    void testSize(){
        assertEquals(0, list.size());
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        assertEquals(3, list.size());
    }

    @Test
    void testExceptions() {
        assertThrows(IllegalStateException.class, () -> list.getFirst());
        assertThrows(IllegalStateException.class, () -> list.getLast());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }
}
