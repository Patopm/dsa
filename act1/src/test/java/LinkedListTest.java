import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListTest {

    @Test
    void singlyLinkedListExtendsLinkedList() {
        assertTrue(LinkedList.class.isAssignableFrom(SinglyLinkedList.class));
    }

    @Test
    void doublyLinkedListExtendsLinkedList() {
        assertTrue(LinkedList.class.isAssignableFrom(DoublyLinkedList.class));
    }

    @Test
    void circularLinkedListExtendsLinkedList() {
        assertTrue(LinkedList.class.isAssignableFrom(CircularLinkedList.class));
    }

    @Test
    void circularDoublyLinkedListExtendsLinkedList() {
        assertTrue(LinkedList.class.isAssignableFrom(CircularDoublyLinkedList.class));
    }

    @Test
    void linkedListIsAbstract() {
        assertTrue(Modifier.isAbstract(LinkedList.class.getModifiers()));
    }

    @Test
    void declaresAbstractMutatingOperations() throws Exception {
        assertAbstractOperation("insert", void.class, Object.class);
        assertAbstractOperation("delete", void.class, Object.class);
    }

    @Test
    void searchAndDisplayAreSharedConcreteOperations() throws Exception {
        assertConcreteOperation("search", boolean.class, Object.class);
        assertConcreteOperation("display", void.class);
    }

    private static void assertAbstractOperation(String name, Class<?> returnType, Class<?>... parameterTypes)
            throws Exception {
        Method method = LinkedList.class.getDeclaredMethod(name, parameterTypes);
        assertTrue(Modifier.isAbstract(method.getModifiers()));
        assertEquals(returnType, method.getReturnType());
    }

    private static void assertConcreteOperation(String name, Class<?> returnType, Class<?>... parameterTypes)
            throws Exception {
        Method method = LinkedList.class.getDeclaredMethod(name, parameterTypes);
        assertFalse(Modifier.isAbstract(method.getModifiers()));
        assertEquals(returnType, method.getReturnType());
    }
}
