package boundedscroll;

import java.util.ListIterator;

/**
 * <p>
 * A bounded Scroll data structure that is similar to a list in that it stores its elements in a sequence.
 * </p>
 *
 * <p>
 * Unlike a list, a scroll keeps track of a distinguished position in the sequence called
 * the "cursor position".
 * </p>
 *
 * <p>
 * A typical string representation of a bounded scroll is
 * [e_1, e_2][e_3, e_4, ... e_n-1, e_n]:c
 * where e_1 is the first element in the scroll, ][ is the "cursor position", e_n is the last element in the scroll,
 * and c is the capacity.
 * </p>
 */
public interface Scroll<E> extends Iterable<E>
{
    /**
     * <p>
     * Adds the specified element to the right of the cursor in this scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> <em>and</em> <code>x = X</code> }<br>
     * <code>s.insert(x)</code>
     * { <code>s = [A, B, C][X, D, E, F]:8</code> <em>and</em> <code>x = X</code> }<br>
     * </p>
     *
     * @param elem element to be added to this scroll
     *
     * @throws IllegalArgumentException if the specified element is null
     * @throws IllegalStateException if the capacity is full
     */
    public void insert(E elem) throws IllegalArgumentException;


    /**
     * <p>
     * Deletes and returns the element to the right of the cursor.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> }<br>
     * <code>s.delete()</code>
     * { <code>s = [A, B, C][E, F]:8</code> }<br>
     * </p>
     *
     * @return the element that was deleted
     *
     * @throws IllegalStateException if there are no elements to be deleted
     */
    public E delete() throws IllegalStateException;


    /**
     * <p>
     * Moves the cursor one element to the right.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> }<br>
     * <code>s.advance()</code>
     * { <code>s = [A, B, C, D][E, F]:8</code> }<br>
     * </p>
     *
     * @throws IllegalStateException if there are no remaining elements to advance to
     */
    public void advance() throws IllegalStateException;


    /**
     * <p>
     * Moves the cursor one element to the left.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> }<br>
     * <code>s.retreat()</code>
     * { <code>s = [A, B][C, D, E, F]:8</code> }<br>
     * </p>
     *
     * @throws IllegalStateException if there are no remaining elements to retreat to
     */
    public void retreat() throws IllegalStateException;


    /**
     * <p>
     * Moves the cursor to the beginning of the scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> }<br>
     * <code>s.reset()</code>
     * { <code>s = [][A, B, C, D, E, F]:8</code> }<br>
     * </p>
     */
    public void reset();


    /**
     * <p>
     * Moves the cursor to the end of the scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8</code> }<br>
     * <code>s.advanceToEnd()</code>
     * { <code>s = [A, B, C, D, E, F][]:8</code> }<br>
     * </p>
     */
    public void advanceToEnd();


    /**
     * <p>
     * Swaps the right part of a scroll with the right part of a specified scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>this = [A, B, C][D, E, F]:8 <em>and</em> that = [X, Y][Z]:6</code> }<br>
     * <code>this.swapRights(that)</code>
     * { <code>this = [A, B][Z]:8</code> <em>and</em> that = [X, Y][D, E, F]:6</code> }<br>
     * </p>
     *
     * @param that scroll to swap rights with
     *
     * @throws IllegalArgumentException if the specified scroll is null
     * @throws IllegalStateException if the swap makes the scroll length longer than the capacity
     */
    public void swapRights(Scroll<E> that) throws IllegalArgumentException;


    /**
     * <p>
     * Returns the number of elements to the left of the cursor.
     * </p>
     *
     * @return int number of elements to the left of the cursor
     */
    public int leftLength();


    /**
     * <p>
     * Returns the number of elements to the right of the cursor.
     * </p>
     *
     * @return int number of elements to the right of the cursor
     */
    public int rightLength();


    /**
     * <p>
     * Creates a new instance of a scroll.
     * </p>
     *
     * <p>
     * The new scroll has the same concrete type that "this" scroll does, and it also has the same capacity.
     * However, the new scroll is empty – it does not contain any elements.
     * </p>
     *
     * @return Scroll<E> new instance of a scroll
     */
    public Scroll<E> newInstance();


    /**
     * <p>
     * Returns the maximum number of elements a scroll can hold.
     * </p>
     *
     * @return int maximum number of elements a scroll can hold
     */
    public int capacity();


    /**
     * <p>
     * A list iterator that starts at the cursor position.
     * </p>
     *
     * <p>
     * Note that list iterators can move forward and backwards.
     * Also note that movement of the cursor in the list iterator *will* affect the cursor
     * position of the scroll and vice-versa.
     * </p>
     *
     * @return an instance of ListIterator
     */
    public ListIterator<E> listIterator();


    /**
     * <p>
     * Gets a handle (i.e. does not remove) to the element to the right of the cursor.
     * </p>
     *
     * <p>
     * The scroll does not change; nor does the cursor change.
     * </p>
     *
     * @return a handle to the next element
     *
     * @throws IllegalStateException if the right length of the scroll is equal to zero.
     */
    public E getNext() throws IllegalStateException;


    /**
     * <p>
     * Gets a handle (i.e. does not remove) to the element to the left of the cursor.
     * </p>
     *
     * <p>
     * The scroll does not change; nor does the cursor change.
     * </p>
     *
     * @return a handle to the previous element
     *
     * @throws IllegalStateException if the left length of the scroll is equal to zero.
     */
    public E getPrevious() throws IllegalStateException;


    /**
     * <p>
     * Replaces the element to the right of the cursor and returns the original.
     *
     * Example:<br>
     * { <code>s = [A, B, C][D, E, F]:8 <em>and</em> x = X</code> }<br>
     * <code>s.replace(x)</code>
     * { <code>this = [A, B, C][X, E, F]:8</code> <em>and</em> and x = X</code> }<br>
     * </p>
     *
     * @return a handle to the original element
     *
     * @throws IllegalArgumentException if the element to be replaced is null
     */
    public E replace(E element) throws IllegalArgumentException;


    /**
     * <p>
     * Adds all the elements from a scroll to the left of the cursor in the specified scroll in the same order.
     * </p>
     *
     * <p>
     * This results in the specified scroll being empty when the call completes, and the cursor of the specified
     * scroll must be at the beginning of the scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>this = [A, B, C][D, E, F]:10 <em>and</em> that = [][X, Y, Z]:6</code> }<br>
     * <code>this.splice(that)</code>
     * { <code>this = [A, B, C, X, Y, Z][D, E, F]:10</code> <em>and</em> that = [X, Y][D, E, F]:6</code> }<br>
     * </p>
     *
     * @param that specified scroll to perform splice with
     *
     * @throws IllegalArgumentException if the specified scroll is null
     * @throws IllegalArgumentException if the cursor of the specified scroll is not at the beginning
     * @throws IllegalStateException if the splice makes the scroll length longer than the capacity
     */
    public void splice(Scroll<E> that) throws IllegalArgumentException;


    /**
     * <p>
     * Moves all the elements from the right of the cursor to the left of the cursor in reverse order.
     * </p>
     *
     * <p>
     * The cursor of the current scroll (this) must be at the beginning of the scroll when the method is called.
     * The cursor will be at the end of the current scroll when the call is complete.
     * </p>
     *
     * <p>
     * Example:<br>
     * { <code>s = [][A, B, C, D]:10</code> }<br>
     * <code>s.reverse()</code>
     * { <code>s= [D, C, B, A][]:10</code> }<br>
     * </p>
     *
     * @throws IllegalStateException if the cursor is not at the beginning of the scroll
     */
    public void reverse() throws IllegalStateException;


    /**
     * <p>
     * Checks for equality between the scroll and another object.
     * </p>
     *
     * @param o object to compare equality
     */
    public boolean equals(Object o);


    /**
     * <p>
     * Returns the hash code of the scroll.
     * </p>
     */
    public int hashCode();

    /**
     * <p>
     * Returns a string representation of the scroll.
     * </p>
     *
     * <p>
     * Example:<br>
     * s = [A, B][C, D]:6 where [A, B] is the left part of the scroll, [C, B] is the right side of the scroll, and
     * 6 is the scroll's capacity.
     * </p>
     */
    public String toString();
}