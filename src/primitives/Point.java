package primitives;

public class Point {
    public static final Point ZERO = new Point(0, 0, 0);
    final protected Double3 xyz;

    public Point(double x, double y, double z){
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 _new_obj){
        this.xyz = new Double3(_new_obj.d1, _new_obj.d2, _new_obj.d3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other) && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    public Vector subtract(Point A){
        return new Vector(this.xyz.subtract(A.xyz));
    }

    public Point add(Vector v){
        return new Point(this.xyz.add(v.xyz));
    }

    public double distanceSquared(Point A){
        return (this.xyz.d1 - A.xyz.d1) * (this.xyz.d1 - A.xyz.d1)
                + (this.xyz.d2 - A.xyz.d2) * (this.xyz.d2 - A.xyz.d2)
                + (this.xyz.d3 - A.xyz.d3) * (this.xyz.d3 - A.xyz.d3);
    }

    public double distance(Point A){
        return Math.sqrt(distanceSquared(A));
    }
}
