package boundedscroll;

public class LinkedScroll<E> extends AbstractScroll<E>
{
    private final Node guard, cursor;

    private int leftLength, rightLength;

    class Node
    {
        E contents;
        Node next;
        Node prev;

        public Node(E contents)
        {
            this.contents = contents;
        }
    }

    public LinkedScroll(int capacity)
    {
        super(capacity);

        guard = new Node(null);
        guard.next = guard;
        guard.prev = guard;

        cursor = new Node(null);
        cursor.next = guard;
        cursor.prev = guard;
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

        Node newNode = new Node(elem);

        if (cursor.next == guard && cursor.prev == guard)
        {
            guard.next = newNode;
            guard.prev = newNode;

            newNode.next = guard;
            newNode.prev = guard;

            cursor.next = newNode;
        }

        newNode.next = cursor.next;
        newNode.prev = cursor.prev;

        cursor.next.prev = newNode;
        cursor.prev.next = newNode;

        cursor.next = newNode;

        rightLength++;
    }

    @Override
    public E delete()
    {
        if (rightLength() == 0)
        {
            throw new IllegalStateException();
        }

        Node nodeToRemove = cursor.next;

        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev;

        cursor.next = nodeToRemove.next;

        rightLength--;

        return nodeToRemove.contents;
    }

    @Override
    public void advance()
    {
        if (rightLength() == 0)
        {
            throw new IllegalStateException();
        }

        Node cursorNext = cursor.next;

        cursor.prev = cursorNext;
        cursor.next = cursor.prev.next;

        rightLength--;
        leftLength++;
    }

    @Override
    public void retreat()
    {
        if (leftLength() == 0)
        {
            throw new IllegalStateException();
        }

        Node cursorPrev = cursor.prev;

        cursor.next = cursorPrev;
        cursor.prev = cursor.next.prev;

        rightLength++;
        leftLength--;
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
        return leftLength;
    }

    @Override
    public int rightLength()
    {
        return rightLength;
    }

    @Override
    public Scroll<E> newInstance()
    {
        return new LinkedScroll<>(capacity());
    }
}