package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ScrollIteratorTest
{
    Scroll<String> abc_de_7; // [A, B, C][D, E]:7
    Scroll<String> empty_3; // [][]:3

    ScrollIterator<String> iterator;
    ScrollIterator<String> iterator2;

    @Before
    public void setUp() throws Exception
    {
        abc_de_7 = new StackScroll<>(7);
        iterator = new ScrollIterator<>(abc_de_7);

        abc_de_7.insert("E");
        abc_de_7.insert("D");
        abc_de_7.insert("C");
        abc_de_7.insert("B");
        abc_de_7.insert("A");
        abc_de_7.reset();
        abc_de_7.advance();
        abc_de_7.advance();
        abc_de_7.advance();

        empty_3 = new StackScroll<>(3);
        iterator2 = new ScrollIterator<>(empty_3);
    }

    /* --- TESTS FOR INITIAL SETUP --- */

    @Test
    public void testIteratorInitSetup()
    {
        // Setup
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : abc_de_7)
        {
            stringBuilder.append(s); // ACTION
        }

        assertEquals("DE", stringBuilder.toString());
        assertEquals(0, abc_de_7.rightLength());
    }

    @Test
    public void testIteratorEmptyScroll()
    {
        assertEquals(0, empty_3.leftLength());
        assertEquals(0, empty_3.rightLength());
        assertEquals(-1, iterator2.previousIndex());
        assertEquals(0, iterator2.nextIndex());
        assertFalse(iterator2.hasNext());
        assertFalse(iterator2.hasPrevious());
    }

    /* --- TESTS FOR HAS NEXT METHOD --- */
    @Test
    public void testHasNextInit()
    {
        Boolean bool = iterator.hasNext();

        assertTrue(bool);
    }

    @Test
    public void testHasNextIsFalse()
    {
        // Setup
        abc_de_7.advanceToEnd();

        Boolean bool = iterator.hasNext(); // ACTION

        assertFalse(bool);
    }

    /* --- TESTS FOR NEXT METHOD --- */

    @Test(expected = NoSuchElementException.class)
    public void testNextWhenCursorIsAtEndOfScroll()
    {
        // Setup
        abc_de_7.advanceToEnd();

        iterator.next(); // ACTION
    }

    @Test()
    public void testNextInit()
    {
        String nextElement = iterator.next();

        assertEquals(1, abc_de_7.rightLength());
        assertEquals("D", nextElement);
    }

    /* --- TESTS FOR HAS PREVIOUS METHOD --- */
    @Test
    public void testHasPreviousInit()
    {
        Boolean bool = iterator.hasPrevious();

        assertTrue(bool);
    }

    @Test
    public void testHasPreviousIsFalse()
    {
        // Setup
        abc_de_7.reset();

        Boolean bool = iterator.hasPrevious(); // ACTION

        assertFalse(bool);
    }

    /* --- TESTS FOR PREVIOUS METHOD --- */

    @Test(expected = NoSuchElementException.class)
    public void testPreviousWhenCursorIsAtBeginningOfScroll()
    {
        // Setup
        abc_de_7.reset();

        iterator.previous(); // ACTION
    }

    @Test()
    public void testPreviousInit()
    {
        String previousElement = iterator.previous();

        assertEquals(2, abc_de_7.leftLength());
        assertEquals("C", previousElement);
    }

    /* --- TESTS FOR NEXT INDEX --- */

    @Test
    public void testNextIndexInit()
    {
        int index = iterator.nextIndex();

        assertEquals(3, index);
    }

    @Test
    public void testNextIndexAfterAdvance()
    {
        // Setup
        abc_de_7.advance();

        int index = iterator.nextIndex(); // ACTION

        assertEquals(4, index);
    }

    /* --- TESTS FOR PREVIOUS INDEX --- */

    @Test
    public void testPreviousIndexInit()
    {
        int index = iterator.previousIndex();

        assertEquals(2, index);
    }

    @Test
    public void testPreviousIndexAfterRetreat()
    {
        // Setup
        abc_de_7.retreat();

        int index = iterator.previousIndex(); // ACTION

        assertEquals(1, index);
    }

    /* --- TESTS FOR UNSUPPORTED METHODS (JUST FOR COVERAGE) --- */

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove()
    {
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet()
    {
        iterator.set(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd()
    {
        iterator.add(null);
    }
}