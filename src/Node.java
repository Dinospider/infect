public class Node {

    private Point point;

    private int g;
    private int h;
    private int f;
    private Node priorNode;

    public Node(Point point) {
        this.point = point;
        this.g = -1;
        this.h = -1;
        this.f = -1;
        this.priorNode = null;
    }

    public int getG() {
        return this.g;
    }

    public int getH() {
        return this.h;
    }

    public int getF() {
        return this.f;
    }

    public Node getPriorNode() {
        return this.priorNode;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setPriorNode(Node priorNode) {
        this.priorNode = priorNode;
    }

    public boolean equals(Object other)
    {
        return other instanceof Node &&
                ((Node)other).point.equals(this.point);
    }

    public int hashCode()
    {
        int result = 17;
        result = result * 31 + point.x;
        result = result * 31 + point.y;
        return result;
    }
}
