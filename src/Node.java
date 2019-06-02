import java.util.Objects;

public class Node
{
    private Point pos;
    private double f;
    private double g;
    private double h;
    private Node prev;

    Node(Point p, double h, Node prev)
    {
        this.pos = p;
        this.prev = prev;
        if(prev == null)
        {
            this.g = 0;
        }
        else
        {
            this.g = this.prev.getG() + 1;
        }
        this.h = h;
        this.f = this.h + this.g;
    }

    public double getF()
    {
        return f;
    }

    public double getG()
    {
        return g;
    }

    public double getH()
    {
        return h;
    }

    public Point getPos()
    {
        return pos;
    }

    public Node getPrev()
    {
        return prev;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        else if(obj.getClass() != this.getClass())
            return false;
        Node other = (Node) obj;
        return Objects.equals(pos, other.pos);
    }
}
