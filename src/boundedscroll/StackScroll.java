package boundedscroll;

import java.util.Stack;

public class StackScroll<E> extends AbstractScroll<E>
{
    private final Stack<E> left;
    private Stack<E> right;

    public StackScroll(int capacity)
    {
        super(capacity);
        left = new Stack<>();
        right = new Stack<>();
    }

    @Override
    public void insert(E elem)
    {
        if (elem == null)
        {
            throw new IllegalArgumentException();
        }

        if (left.size() + right.size() == capacity())
        {
            throw new IllegalStateException();
        }

        right.push(elem);
    }

    @Override
    public E delete()
    {
        if (right.isEmpty())
        {
            throw new IllegalStateException();
        }

        return right.pop();
    }

    @Override
    public void advance() throws IllegalStateException
    {
        if (right.isEmpty())
        {
            throw new IllegalStateException();
        }

        left.push(right.pop());
    }

    @Override
    public void retreat()
    {
        if (left.isEmpty())
        {
            throw new IllegalStateException();
        }

        right.push(left.pop());
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

        if (this.left.size() + that.rightLength() > capacity()
                || that.leftLength() + this.right.size() > that.capacity())
        {
            throw new IllegalStateException();
        }

        if (that instanceof StackScroll<?>)
        {
            StackScroll<E> stackScrollThat = (StackScroll<E>) that;
            Stack<E> temp = this.right;
            this.right = stackScrollThat.right;
            stackScrollThat.right = temp;
        }

        else
        {
            super.swapRights(that);
        }
    }

    @Override
    public int leftLength()
    {
        return left.size();
    }

    @Override
    public int rightLength()
    {
        return right.size();
    }

    @Override
    public Scroll<E> newInstance()
    {
        return new StackScroll<>(capacity());
    }
}