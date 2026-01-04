package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedScrollTest
{

    private Scroll<String> ab_cd_6; // [A, B][C, D]:6
    private Scroll<String> w_xyz_8; // [W][X, Y, Z]:8
    private Scroll<String> empty_3; // [][]:3
    private Scroll<String> nullLinkedScroll;

    @Before
    public void setUp() throws Exception
    {
        ab_cd_6 = new LinkedScroll<>(6);
        ab_cd_6.insert("D");
        ab_cd_6.insert("C");
        ab_cd_6.insert("B");
        ab_cd_6.insert("A");
        ab_cd_6.reset();
        ab_cd_6.advance();
        ab_cd_6.advance();

        w_xyz_8 = new LinkedScroll<>(8);
        w_xyz_8.insert("Z");
        w_xyz_8.insert("Y");
        w_xyz_8.insert("X");
        w_xyz_8.insert("W");
        w_xyz_8.reset();
        w_xyz_8.advance();

        empty_3 = new LinkedScroll<>(3);

        nullLinkedScroll = null;
    }

    /* --- TESTS FOR INITIAL SETUP --- */

    @Test
    public void testInitSetUp()
    {
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(2, ab_cd_6.rightLength());
        assertEquals(6, ab_cd_6.capacity());
    }

    @Test
    public void testInitSetUpGetNext()
    {
        assertEquals("C", ab_cd_6.getNext());
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
    public void testDeleteWhenRightLinkedScrollIsEmpty()
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
    public void testAdvanceWhenRightLinkedScrollIsEmpty()
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
    public void testRetreatWhenLeftLinkedScrollIsEmpty()
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
        ab_cd_6.swapRights(nullLinkedScroll);
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
    public void testSwapRightsWithEmptyList()
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
    public void testSwapRightsThatExceedsCapacityWithEmptyLinkedScroll()
    {
        // Setup
        w_xyz_8.insert("A");

        empty_3.swapRights(w_xyz_8); // ACTION
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

        Scroll<String> newLinkedScroll = empty_3.newInstance(); // ACTION

        assertEquals(3, newLinkedScroll.capacity());
        assertTrue(newLinkedScroll.leftLength() == 0 && newLinkedScroll.rightLength() == 0);
    }
}