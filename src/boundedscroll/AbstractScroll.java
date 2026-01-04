package boundedscroll;

import java.util.Iterator;
import java.util.ListIterator;

public abstract class AbstractScroll<E> implements Scroll<E>
{
    private final int capacity;

    public AbstractScroll(int capacity)
    {
        this.capacity = capacity;
    }

    @Override
    public int capacity()
    {
        return capacity;
    }

    @Override
    public void swapRights(Scroll<E> that)
    {
        if (leftLength() + that.rightLength() > capacity()
                || that.leftLength() + rightLength() > that.capacity())
        {
            throw new IllegalStateException();
        }

        Scroll<E> temp = this.newInstance();

        transferRight(this, temp);
        transferRight(that, this);
        transferRight(temp, that);
    }

    private void transferRight(Scroll<E> scrollOne, Scroll<E> scrollTwo)
    {
        int scrollOneCursor = scrollOne.leftLength();

        ScrollIterator<E> iterator = new ScrollIterator<>(scrollOne);

        scrollOne.advanceToEnd();

        while(iterator.hasPrevious() && scrollOne.leftLength() > scrollOneCursor)
        {
            E elem = (iterator.previous());
            scrollOne.delete();
            scrollTwo.insert(elem);
        }
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ScrollIterator<>(this);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return new ScrollIterator<>(this);
    }

    @Override
    public E getNext()
    {
        if (rightLength() == 0)
        {
            throw new IllegalStateException();
        }

        E result = delete();
        insert(result);

        return result;
    }

    @Override
    public E getPrevious()
    {
        if (leftLength() == 0)
        {
            throw new IllegalStateException();
        }

        retreat();

        E result = delete();
        insert(result);

        advance();

        return result;
    }

    @Override
    public E replace(E element)
    {
        if (element == null)
        {
            throw new IllegalArgumentException();
        }

        E nextElement = delete();
        insert(element);
        return nextElement;
    }

    @Override
    public void splice(Scroll<E> that)
    {
        if (that == null)
        {
            throw new IllegalArgumentException();
        }

        if (that.leftLength() != 0)
        {
            throw new IllegalArgumentException();
        }

        if (that.rightLength() + (leftLength() + rightLength()) > capacity())
        {
            throw new IllegalStateException();
        }

        if (that.rightLength() == 0)
        {
            return;
        }

        E elem = that.delete();

        insert(elem);
        advance();
        splice(that);
    }

    @Override
    public void reverse()
    {
        if (leftLength() != 0)
        {
            throw new IllegalStateException();
        }

        Scroll<E> temp = newInstance();

        while(iterator().hasNext())
        {
            E elem = delete();
            temp.insert(elem);
        }

        this.swapRights(temp);

        advanceToEnd();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (o == this)
        {
            return true;
        }

        if (! (o instanceof Scroll<?>))
        {
            return false;
        }

        Scroll<?> that = (Scroll<?>) o;

        if (this.capacity() != that.capacity() || this.leftLength() != that.leftLength() ||
            this.rightLength() != that.rightLength())
        {
            return false;
        }

        int cursor = leftLength();

        this.reset();
        that.reset();

        Iterator<E> thisIterator = new ScrollIterator<>(this);
        Iterator<?> thatIterator = new ScrollIterator<>(that);

        while (thisIterator.hasNext())
        {
            E elem = thisIterator.next();
            Object obj = thatIterator.next();
            if (!elem.equals(obj))
            {
                return false;
            }
        }

        this.reset();
        that.reset();

        for (int i = 0; i < cursor; i++)
        {
            this.advance();
            that.advance();
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int cursor = leftLength();
        int result = 17;

        reset();

        Iterator<E> iterator = listIterator();

        result += cursor + rightLength() + capacity();

        while(iterator.hasNext())
        {
            result = 31 * result + iterator.next().hashCode();
        }

        result = 31 * result;

        reset();

        for (int i = 0; i < cursor; i++)
        {
            advance();
        }

        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        int cursor = leftLength();
        Iterator<E> iterator = new ScrollIterator<>(this);

        reset();

        stringBuilder.append("[");

        while(iterator.hasNext() && leftLength() < cursor)
        {
            E elem = iterator.next();
            stringBuilder.append(elem);
            if (leftLength() != cursor)
            {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        stringBuilder.append("[");

        while(iterator.hasNext())
        {
            E elem = iterator.next();
            stringBuilder.append(elem);
            if (iterator.hasNext())
            {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]:" + capacity());

        reset();

        for (int i = 0; i < cursor; i++)
        {
            advance();
        }

        return stringBuilder.toString();
    }
}