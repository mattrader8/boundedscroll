package boundedscroll;

import java.util.ArrayList;
import java.util.List;

public class ListScroll<E> extends AbstractScroll<E>
{
    private final List<E> elements;
    private int pos;

    public ListScroll(int capacity)
    {
        super(capacity);

        elements = new ArrayList<>();
    }

    @Override
    public void insert(E elem)
    {
        if (elem == null)
        {
            throw new IllegalArgumentException();
        }

        if (leftLength() + rightLength() == capacity())
        {
            throw new IllegalStateException();
        }

        elements.add(pos, elem);
    }

    @Override
    public E delete()
    {
        if (rightLength() == 0)
        {
            throw new IllegalStateException();
        }

        return elements.remove(pos);
    }

    @Override
    public void advance()
    {
        if (rightLength() == 0)
        {
            throw new IllegalStateException();
        }

        pos = pos + 1;
    }

    @Override
    public void retreat()
    {
        if (leftLength() == 0)
        {
            throw new IllegalStateException();
        }

        pos = pos - 1;
    }

    @Override
    public void reset()
    {
        while (leftLength() != 0)
        {
            retreat();
        }
    }

    @Override
    public void advanceToEnd()
    {
        while (rightLength() != 0)
        {
            advance();
        }
    }

    @Override
    public void swapRights(Scroll<E> that)
    {
        if (that == null)
        {
            throw new IllegalArgumentException();
        }

        super.swapRights(that);
    }

    @Override
    public int leftLength()
    {
        return pos;
    }

    @Override
    public int rightLength()
    {
        return elements.size() - pos;
    }

    @Override
    public Scroll<E> newInstance()
    {
        return new ListScroll<>(capacity());
    }
}