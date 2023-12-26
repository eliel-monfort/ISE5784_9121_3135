package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z){
        super(x, y, z);
        if(this.xyz == Double3.ZERO) {
            throw new IllegalArgumentException("the zero vector not ok");
        }
    }

    public Vector(Double3 new_xyz){
        super(new_xyz.d1, new_xyz.d2, new_xyz.d3);
        if(new_xyz == Double3.ZERO){
            throw new IllegalArgumentException("The zero vector is Illegal");
        }
    }

    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }

    public Vector scale(double num){
        return new Vector(this.xyz.scale(num));
    }

    public double dotProduct(Vector v){
        return (this.xyz.d1 * v.xyz.d1) + (this.xyz.d2 * v.xyz.d2) + (this.xyz.d3 * v.xyz.d3);
    }

    public Vector crossProduct(Vector v){
        double THIS_x = this.xyz.d1;
        double THIS_y = this.xyz.d2;
        double THIS_z = this.xyz.d3;
        double V_x = v.xyz.d1;
        double V_y = v.xyz.d2;
        double V_z = v.xyz.d3;
        return new Vector((THIS_y * V_z) - (THIS_z * V_y), (THIS_z * V_x) - (THIS_x * V_z), (THIS_x * V_y) - (THIS_y * V_x));
    }

    public double lengthSquared(){
        return this.dotProduct(this);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize(){
        return new Vector(this.xyz.reduce(this.length()));
    }
}
