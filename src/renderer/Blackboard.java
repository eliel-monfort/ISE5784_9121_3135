package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Blackboard` class represents a virtual blackboard used for rendering optimization.
 * It provides methods for generating jittered rays to improve Anti-Aliasing effects.
 */
public class Blackboard {

    /** The width of the blackboard. */
    private double width;

    /** The height of the blackboard. */
    private double height;

    /** The number of divisions in the horizontal direction. */
    private int Nx = 1;

    /** The number of divisions in the vertical direction. */
    private int Ny = 1;

    /** The center point of the blackboard. */
    private Point centerPoint;

    /** The X-axis vector of the blackboard. */
    private Vector vectorX;

    /** The Y-axis vector of the blackboard. */
    private Vector vectorY;

    /** The point for helping create rays. */
    private Point p0;

    /**
     * Constructs a new Blackboard instance with default values.
     */
    public Blackboard(){}

    /**
     * Constructs a new Blackboard instance with specified parameters.
     *
     * @param width The width of the blackboard.
     * @param height The height of the blackboard.
     * @param row The number of divisions in the horizontal direction.
     * @param column The number of divisions in the vertical direction.
     * @param vectorX The X-axis vector of the blackboard.
     * @param vectorY The Y-axis vector of the blackboard.
     * @param p0 The reference point of the blackboard.
     */
    public Blackboard(double width, double height, int row, int column, Vector vectorX, Vector vectorY, Point p0) {
        this.width = width;
        this.height = height;
        this.Nx = row;
        this.Ny = column;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
        this.p0 = p0;
    }

    /**
     * Sets the center point of the blackboard.
     *
     * @param centerPoint The center point to be set.
     * @return This Blackboard instance for method chaining.
     */
    public Blackboard setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
        return this;
    }

    /**
     * Checks if anti-aliasing is enabled based on the blackboard configuration.
     *
     * @return True if anti-aliasing is enabled, false otherwise.
     */
    public boolean isAntiAliasing(){
        return (this.Nx > 1) && (this.Ny > 1);
    }

    /**
     * Generates jittered rays for anti-aliasing.
     *
     * @return A list of jittered rays.
     */
    public List<Ray> jittered(){
        List<Ray> rays = new ArrayList<>();
        for (int i = 0; i < this.Ny; i++){
            for (int j = 0; j < this.Nx; j++){
                rays.add(this.jitteredHelper(j, i));
            }
        }
        return rays;
    }

    /**
     * Helper method to generate a single jittered ray.
     *
     * @param j The horizontal division index.
     * @param i The vertical division index.
     * @return The jittered ray.
     */
    private Ray jitteredHelper(int j, int i){
        Point centerArea = findCenter(j, i);
        double randomX = random(-((this.width - 1) / this.Nx) / 2, ((this.width - 1) / this.Nx) / 2);
        double randomY = random(-((this.height - 1) / this.Ny) / 2, ((this.height - 1) / this.Ny) / 2);
        if (!isZero(randomX)){
            centerArea = centerArea.add(this.vectorX.scale(randomX));
        }
        if (!isZero(randomY)){
            centerArea = centerArea.add(this.vectorY.scale(randomY));
        }
        return new Ray(this.p0, centerArea.subtract(this.p0));
    }

    /**
     * Calculates the center point of a given division.
     *
     * @param j The horizontal division index.
     * @param i The vertical division index.
     * @return The center point of the specified division.
     */
    public Point findCenter(int j, int i){
        double Rx = (this.width) / this.Nx;
        double Ry = (this.height) / this.Ny;
        double Xj = ((j * Rx) - ((this.width - 1) / 2)) + (Rx / 2);
        double Yi = ((-i * Ry) + ((this.height - 1) / 2d)) - (Ry / 2);
        Point Pij = this.centerPoint;
        if (Xj != 0){
            Pij = Pij.add(this.vectorX.scale(Xj));
        }
        if (Yi != 0){
            Pij = Pij.add(this.vectorY.scale(Yi));
        }
        return Pij;
    }
}