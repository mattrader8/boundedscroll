package boundedscroll;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ScrollIterator<E> implements ListIterator<E> {

    Scroll<E> scroll;

    public ScrollIterator(Scroll<E> scroll)
    {
        this.scroll = scroll;
    }

    @Override
    public boolean hasNext()
    {
        return scroll.rightLength() != 0;
    }

    @Override
    public E next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException();
        }

        E elem = scroll.getNext();
        scroll.advance();

        return elem;
    }

    @Override
    public boolean hasPrevious()
    {
        return scroll.leftLength() != 0;
    }

    @Override
    public E previous()
    {
        if (!hasPrevious())
        {
            throw new NoSuchElementException();
        }

        E elem = scroll.getPrevious();
        scroll.retreat();

        return elem;
    }

    @Override
    public int nextIndex()
    {
        return scroll.leftLength();
    }

    @Override
    public int previousIndex()
    {
        return scroll.leftLength() - 1;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(Object o)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Object o)
    {
        throw new UnsupportedOperationException();
    }
}