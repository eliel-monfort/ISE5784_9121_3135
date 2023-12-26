package primitives;

public class Ray {
    final Point head;
    final Vector direction;

    public Ray(Point _head, Vector _direction){
        this.head = new Point(_head.xyz);
        this.direction = new Vector(_direction.normalize().xyz);
    }
}
