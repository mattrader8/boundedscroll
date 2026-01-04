package boundedscroll;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StackScrollTest
{
    private Scroll<String> ab_cd_6; // [A, B][C, D]:6
    private Scroll<String> ab_cd_6_duplicate; // [A, B][C, D]:6
    private Scroll<String> ab_cd_10; // [A, B][C, D]:10
    private Scroll<String> ab_ed_6; // [A, B][E, D]:6
    private Scroll<String> w_xyz_8; // [W][X, Y, Z]:8
    private Scroll<String> empty_3; // [][]:3
    private Scroll<String> nullStack; // null

    private Scroll<String> listScroll; // [A][B, C, D]:10
    private Scroll<String> listScroll_6; // [A, B][C, D]:6

    @Before
    public void setUp() throws Exception
    {
        ab_cd_6 = new StackScroll<>(6);
        ab_cd_6.insert("D");
        ab_cd_6.insert("C");
        ab_cd_6.insert("B");
        ab_cd_6.insert("A");
        ab_cd_6.reset();
        ab_cd_6.advance();
        ab_cd_6.advance();

        ab_cd_6_duplicate = new StackScroll<>(6);
        ab_cd_6_duplicate.insert("D");
        ab_cd_6_duplicate.insert("C");
        ab_cd_6_duplicate.insert("B");
        ab_cd_6_duplicate.insert("A");
        ab_cd_6_duplicate.reset();
        ab_cd_6_duplicate.advance();
        ab_cd_6_duplicate.advance();

        ab_cd_10 = new StackScroll<>(10);
        ab_cd_10.insert("D");
        ab_cd_10.insert("C");
        ab_cd_10.insert("B");
        ab_cd_10.insert("A");
        ab_cd_10.reset();
        ab_cd_10.advance();
        ab_cd_10.advance();

        ab_ed_6 = new StackScroll<>(6);
        ab_ed_6.insert("D");
        ab_ed_6.insert("E");
        ab_ed_6.insert("B");
        ab_ed_6.insert("A");
        ab_ed_6.reset();
        ab_ed_6.advance();
        ab_ed_6.advance();

        w_xyz_8 = new StackScroll<>(8);
        w_xyz_8.insert("Z");
        w_xyz_8.insert("Y");
        w_xyz_8.insert("X");
        w_xyz_8.insert("W");
        w_xyz_8.reset();
        w_xyz_8.advance();

        empty_3 = new StackScroll<>(3);

        nullStack = null;

        listScroll = new ListScroll<>(10);
        listScroll.insert("D");
        listScroll.insert("C");
        listScroll.insert("B");
        listScroll.insert("A");
        listScroll.reset();
        listScroll.advance();

        listScroll_6 = new ListScroll<>(6);
        listScroll_6.insert("D");
        listScroll_6.insert("C");
        listScroll_6.insert("B");
        listScroll_6.insert("A");
        listScroll_6.reset();
        listScroll_6.advance();
        listScroll_6.advance();
    }

    /* --- TESTS FOR INITIAL SETUP --- */

    @Test
    public void testInitSetUp()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
        assertEquals(6, ab_cd_6.capacity());
        assertEquals("C", ab_cd_6.getNext());
        assertEquals("B", ab_cd_6.getPrevious());
    }

    /* --- TESTS FOR INSERT METHOD --- */

    @Test(expected = IllegalArgumentException.class)
    public void testInsertWhenElementIsNull()
    {
        ab_cd_6.insert(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testInsertWhenCapacityIsFull()
    {
        // Setup
        ab_cd_6.insert("X");
        ab_cd_6.insert("Y");

        ab_cd_6.insert("Z"); // ACTION
    }

    @Test
    public void testInsertInitSetup()
    {
        ab_cd_6.insert("X");

        assertEquals(3, ab_cd_6.rightLength());
        assertEquals("X", ab_cd_6.getNext());
    }

    @Test
    public void testInsertAfterCursorAdvances()
    {
        // Setup
        ab_cd_6.advance();

        ab_cd_6.insert("X"); // ACTION

        assertEquals(2, ab_cd_6.rightLength());
        assertEquals("X", ab_cd_6.getNext());
    }

    @Test
    public void testInsertWithEmptyScroll()
    {
        empty_3.insert("X");
    }

    /* --- TESTS FOR DELETE METHOD --- */

    @Test(expected = IllegalStateException.class)
    public void testDeleteWhenRightStackIsEmpty()
    {
        // Setup
        ab_cd_6.delete();
        ab_cd_6.delete();

        ab_cd_6.delete(); // ACTION
    }

    @Test
    public void testDeleteInitSetup()
    {
        String deletedElement = ab_cd_6.delete();

        assertEquals(1, ab_cd_6.rightLength());
        assertEquals("C", deletedElement);
    }

    @Test
    public void testDeleteAfterCursorRetreats()
    {
        // Setup
        ab_cd_6.retreat();

        String deletedElement = ab_cd_6.delete(); // ACTION

        assertEquals(2, ab_cd_6.rightLength());
        assertEquals("B", deletedElement);
    }

    /* --- TESTS FOR ADVANCE METHOD --- */

    @Test(expected = IllegalStateException.class)
    public void testAdvanceWhenRightStackIsEmpty()
    {
        // Setup
        ab_cd_6.delete();
        ab_cd_6.delete();

        ab_cd_6.advance(); // ACTION
    }

    @Test
    public void testAdvanceInitSetup()
    {
        ab_cd_6.advance();

        assertEquals("D", ab_cd_6.getNext());
    }

    /* --- TESTS FOR RETREAT METHOD --- */

    @Test(expected = IllegalStateException.class)
    public void testRetreatWhenLeftStackIsEmpty()
    {
        // Setup
        ab_cd_6.reset();

        ab_cd_6.retreat(); // ACTION
    }

    @Test()
    public void testRetreatInitSetup()
    {
        ab_cd_6.retreat();

        assertEquals("A", ab_cd_6.getPrevious());
    }

    /* --- TESTS FOR RESET METHOD --- */

    @Test()
    public void testResetInitSetup()
    {
        ab_cd_6.reset();

        assertEquals("A", ab_cd_6.getNext());
    }

    @Test()
    public void testAdvanceToEndInitSetup()
    {
        ab_cd_6.advanceToEnd();

        assertEquals("D", ab_cd_6.getPrevious());
    }

    /* --- TESTS FOR SWAP RIGHTS METHOD --- */

    @Test(expected = IllegalArgumentException.class)
    public void testSwapRightsWhenThatIsNull()
    {
        ab_cd_6.swapRights(nullStack);
    }

    @Test()
    public void testSwapRightsInitSetup()
    {
        ab_cd_6.swapRights(w_xyz_8);

        assertEquals("X", ab_cd_6.getNext());
        assertEquals("C", w_xyz_8.getNext());
    }

    @Test()
    public void testSwapRightsInitSetupAndCheckIfRightLengthsChanged()
    {
        // Setup
        assertEquals(2, ab_cd_6.rightLength());
        assertEquals(3, w_xyz_8.rightLength());

        ab_cd_6.swapRights(w_xyz_8);

        assertEquals(3, ab_cd_6.rightLength());
        assertEquals(2, w_xyz_8.rightLength());
    }

    @Test
    public void testSwapRightsWithEmptyStack()
    {
        empty_3.swapRights(ab_cd_6);

        assertEquals("C", empty_3.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testSwapRightsThatExceedsCapacityForThis()
    {
        // Setup
        w_xyz_8.insert("A");
        w_xyz_8.insert("B");

        ab_cd_6.swapRights(w_xyz_8); // ACTION
    }

    @Test(expected = IllegalStateException.class)
    public void testSwapRightsThatExceedsCapacityForThat()
    {
        // Setup
        empty_3.insert("V");
        empty_3.insert("W");
        empty_3.insert("X");
        empty_3.insert("Y");
        empty_3.insert("Z");

        empty_3.swapRights(ab_cd_6); // ACTION
    }

    @Test(expected = IllegalStateException.class)
    public void testSwapRightsThatExceedsCapacityWithEmptyStack()
    {
        // Setup
        w_xyz_8.insert("A");

        empty_3.swapRights(w_xyz_8); // ACTION
    }

    @Test
    public void testSwapRightsWithStackAndList()
    {
        w_xyz_8.swapRights(listScroll);

        assertEquals("B", w_xyz_8.getNext());
        assertEquals("X", listScroll.getNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testSwapRightsWithStackAndListThatExceedsCapacityForThis()
    {
        // Setup
        w_xyz_8.insert("X");
        w_xyz_8.insert("Y");

        listScroll_6.swapRights(w_xyz_8); // ACTION
    }

    @Test(expected = IllegalStateException.class)
    public void testSwapRightsWithStackAndListThatExceedsCapacityForThat()
    {
        // Setup
        empty_3.insert("V");
        empty_3.insert("W");
        empty_3.insert("X");
        empty_3.insert("Y");
        empty_3.insert("Z");

        empty_3.swapRights(listScroll_6); // ACTION
    }

    /* --- TESTS FOR NEW INSTANCE METHOD ---*/

    @Test
    public void testNewInstanceForEmpty3()
    {
        empty_3.newInstance();

        assertEquals(3, empty_3.capacity());
        assertTrue(empty_3.leftLength() == 0 && empty_3.rightLength() == 0);
    }

    @Test
    public void testNewInstanceForEmpty3AfterInsertingElement()
    {
        // Setup
        empty_3.insert("A");

        assertEquals("A", empty_3.getNext());

        Scroll<String> newStackScroll = empty_3.newInstance(); // ACTION

        assertEquals(3, newStackScroll.capacity());
        assertTrue(newStackScroll.leftLength() == 0 && newStackScroll.rightLength() == 0);
    }

    /* --- TESTS FOR GET NEXT AND GET PREVIOUS METHOD EXCEPTIONS --- */

    @Test(expected = IllegalStateException.class)
    public void testGetNextForException()
    {
        ab_cd_6.advanceToEnd();

        ab_cd_6.getNext();
    }

    @Test(expected = IllegalStateException.class)
    public void testGetPreviousForException()
    {
        ab_cd_6.reset();

        ab_cd_6.getPrevious();
    }

    /* --- TESTS FOR REPLACE METHOD FROM ABSTRACT SCROLL --- */

    @Test(expected = IllegalArgumentException.class)
    public void testReplaceWhenArgumentIsNull()
    {
        ab_cd_6.replace(null);
    }

    @Test
    public void testReplaceInitSetup()
    {
        String result = ab_cd_6.replace("X");

        assertEquals("X", ab_cd_6.getNext());
        assertEquals("C", result);
    }

    @Test
    public void testReplaceAfterAdvancingCursor()
    {
        // Setup
        ab_cd_6.advance();

        String result = ab_cd_6.replace("X"); // ACTION

        assertEquals("X", ab_cd_6.getNext());
        assertEquals("D", result);
    }

    /* --- TESTS FOR SPLICE METHOD FROM ABSTRACT SCROLL --- */

    @Test(expected = IllegalArgumentException.class)
    public void testSpliceInitSetup()
    {
        w_xyz_8.splice(ab_cd_6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpliceWhenThatIsNull()
    {
        ab_cd_6.splice(nullStack);
    }

    @Test
    public void testSpliceWhenPreConditionsAreMet()
    {
        // Setup
        ab_cd_6.reset();

        w_xyz_8.splice(ab_cd_6); // ACTION

        assertEquals("D", w_xyz_8.getPrevious());
        assertEquals("X", w_xyz_8.getNext());
        assertEquals(0, ab_cd_6.leftLength() + ab_cd_6.rightLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testSpliceWhenSizeBecomesGreaterThanCapacity()
    {
        // Setup
        w_xyz_8.reset();

        ab_cd_6.splice(w_xyz_8); // ACTION
    }

    /* --- TESTS FOR REVERSE METHOD FROM ABSTRACT SCROLL --- */

    @Test(expected = IllegalStateException.class)
    public void testReverseWhenCursorIsNotAtBeginning()
    {
        ab_cd_6.reverse();
    }

    @Test
    public void testReverseWhenPreConditionIsMet()
    {
        // Setup
        ab_cd_6.reset();

        assertEquals("A", ab_cd_6.getNext());

        ab_cd_6.reverse(); // ACTION

        assertEquals(0, ab_cd_6.rightLength());
        assertEquals("A", ab_cd_6.getPrevious());
    }

    /* --- TESTS FOR EQUALS METHOD FROM ABSTRACT SCROLL --- */

    @Test
    public void testEqualsIsNull()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertFalse(ab_cd_6.equals(nullStack));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
    }

    @Test
    public void testEqualsSelf()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertTrue(ab_cd_6.equals(ab_cd_6));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
    }

    @Test
    public void testEqualsNonStack()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertFalse(ab_cd_6.equals("[A, B][C, D]:6"));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
    }

    @Test
    public void testEqualsWithDifferentStacksButSameRepresentation()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_6_duplicate.leftLength());
        assertEquals(2, ab_cd_6_duplicate.rightLength());

        assertTrue(ab_cd_6.equals(ab_cd_6_duplicate));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_6_duplicate.leftLength());
        assertEquals(2, ab_cd_6_duplicate.rightLength());
    }

    @Test
    public void testEqualsTwoStacksWithSameRepresentationButDifferentCapacities()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());

        assertFalse(ab_cd_6.equals(ab_cd_10));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());
    }

    @Test
    public void testEqualsTwoStacksWithSameCapacitiesAndLeftLengthButDifferentRightLengths()
    {
        // Setup
        ab_cd_10.insert("X");

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(3, ab_cd_10.rightLength());

        assertFalse(ab_cd_6.equals(ab_cd_10));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(3, ab_cd_10.rightLength());
    }

    @Test
    public void testEqualsTwoStacksWithSameRepresentationButDifferentCursorPositions()
    {
        // Setup
        ab_cd_6_duplicate.advance();

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(3, ab_cd_6_duplicate.leftLength());
        assertEquals(1, ab_cd_6_duplicate.rightLength());

        assertFalse(ab_cd_6.equals(ab_cd_6_duplicate)); // ACTION

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(3, ab_cd_6_duplicate.leftLength());
        assertEquals(1, ab_cd_6_duplicate.rightLength());
    }

    @Test
    public void testEqualsWithTwoDifferentStacks()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(1, w_xyz_8.leftLength());
        assertEquals(3, w_xyz_8.rightLength());

        assertFalse(ab_cd_6.equals(w_xyz_8));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(1, w_xyz_8.leftLength());
        assertEquals(3, w_xyz_8.rightLength());
    }

    @Test
    public void testEqualsWithStackAndList()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, listScroll_6.leftLength());
        assertEquals(2, listScroll_6.rightLength());

        assertTrue(ab_cd_6.equals(listScroll_6));

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, listScroll_6.leftLength());
        assertEquals(2, listScroll_6.rightLength());
    }

    @Test
    public void testEqualsWithSimilarStacksButOneElementIsOff()
    {
        assertFalse(ab_cd_6.equals(ab_ed_6));
    }

    /* --- TESTS FOR HASHCODE METHOD FROM ABSTRACT SCROLL --- */

    @Test
    public void testHashCodeWithTwoDifferentStacks()
    {
        assertFalse(ab_cd_6.hashCode() == w_xyz_8.hashCode());
    }

    @Test
    public void testHashCodeWithDifferentStacksButSameRepresentation()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_6_duplicate.leftLength());
        assertEquals(2, ab_cd_6_duplicate.rightLength());

        assertTrue(ab_cd_6.hashCode() == ab_cd_6_duplicate.hashCode());

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_6_duplicate.leftLength());
        assertEquals(2, ab_cd_6_duplicate.rightLength());
    }

    @Test
    public void testHashCodeWithSimilarStacksButDifferentCapacities()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());

        assertFalse(ab_cd_6.hashCode() == ab_cd_10.hashCode());

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());
    }

    @Test
    public void testHashCodeWithStackAndList()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, listScroll_6.leftLength());
        assertEquals(2, listScroll_6.rightLength());

        assertTrue(ab_cd_6.hashCode() == listScroll_6.hashCode());

        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());

        assertEquals(2, listScroll_6.leftLength());
        assertEquals(2, listScroll_6.rightLength());
    }

    /* --- TESTS FOR TO STRING METHOD FROM ABSTRACT SCROLL --- */

    @Test
    public void testToStringWithEmptyStack()
    {
        assertEquals("[][]:3", empty_3.toString());
    }

    @Test
    public void testToStringInit()
    {
        assertEquals("[A, B][C, D]:6", ab_cd_6.toString());
    }

    @Test
    public void testToStringAfterAdvance()
    {
        // Setup
        ab_cd_6.advance();

        assertEquals("[A, B, C][D]:6", ab_cd_6.toString()); // ACTION
    }

    @Test
    public void testToStringWXYZ()
    {
        assertEquals("[W][X, Y, Z]:8", w_xyz_8.toString());
    }
}