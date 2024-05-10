package renderer;

import primitives.Point;
import primitives.Vector;
import static primitives.Util.isZero;
import static primitives.Util.random;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Blackboard` class represents a virtual blackboard used for rendering optimization.
 * It provides methods for generating jittered rays to improve Anti-Aliasing and Soft-Shadows effects.
 */
public class Blackboard {

    /** The width of the blackboard. */
    private double width = 0;

    /** The height of the blackboard. */
    private double height = 0;

    /** The number of divisions in the horizontal direction. */
    private int Nx = 1;

    /** The number of divisions in the vertical direction. */
    private int Ny = 1;

    /** The center point of the blackboard. */
    private Point centerBoard;

    /**
     * Gets the width of the blackboard.
     *
     * @return The width of the blackboard.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the blackboard.
     *
     * @return The height of the blackboard.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the center point of the blackboard.
     *
     * @param centerBoard The center point to be set.
     * @return This Blackboard instance for method chaining.
     */
    public Blackboard setCenterPoint(Point centerBoard) {
        this.centerBoard = centerBoard;
        return this;
    }

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
     */
    public Blackboard(double width, double height, int row, int column) {
        if (width < 0 || height < 0){
            throw new IllegalArgumentException("The sizes of the blackboard, width and height, is invalid");
        }
        this.width = width;
        this.height = height;
        if (row < 1 || column < 1){
            throw new IllegalArgumentException("The division of the blackboard into rows and columns is invalid");
        }
        this.Nx = row;
        this.Ny = column;
    }

    /**
     * Generates jittered points on the blackboard.
     *
     * @param vectorX The X direction vector.
     * @param vectorY The Y direction vector.
     * @return A list of jittered points.
     */
    public List<Point> jittered(Vector vectorX, Vector vectorY){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < this.Ny; i++){
            for (int j = 0; j < this.Nx; j++){
                Point centerArea = findCenter(vectorX, vectorY, j, i);
                double randomX = random(-((this.width - 1) / this.Nx) / 2, ((this.width - 1) / this.Nx) / 2);
                double randomY = random(-((this.height - 1) / this.Ny) / 2, ((this.height - 1) / this.Ny) / 2);
                if (!isZero(randomX)){
                    centerArea = centerArea.add(vectorX.scale(randomX));
                }
                if (!isZero(randomY)){
                    centerArea = centerArea.add(vectorY.scale(randomY));
                }
                points.add(centerArea);
            }
        }
        return points;
    }

    /**
     * Generates gride points on the blackboard.
     *
     * @param vectorX The X direction vector.
     * @param vectorY The Y direction vector.
     * @return A list of jittered points.
     */
    public List<Point> grid(Vector vectorX, Vector vectorY){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < this.Ny; i++){
            for (int j = 0; j < this.Nx; j++){
                Point centerArea = findCenter(vectorX, vectorY, j, i);
                double X = (j - (this.Nx - 1) / 2d) * (width/this.Nx);
                double Y = -(i - (this.Ny - 1) / 2d) * (height/this.Ny);
                if (!isZero(X)){
                    centerArea = centerArea.add(vectorX.scale(X));
                }
                if (!isZero(Y)){
                    centerArea = centerArea.add(vectorY.scale(Y));
                }
                points.add(centerArea);
            }
        }
        return points;
    }

    /**
     * Calculates the center point of a given division.
     *
     * @param vectorX The X direction vector.
     * @param vectorY The Y direction vector.
     * @param j The horizontal division index.
     * @param i The vertical division index.
     * @return The center point of the specified division.
     */
    private Point findCenter(Vector vectorX, Vector vectorY, int j, int i){
        double Rx = (this.width) / this.Nx;
        double Ry = (this.height) / this.Ny;
        double Xj = ((j * Rx) - ((this.width - 1) / 2)) + (Rx / 2);
        double Yi = ((-i * Ry) + ((this.height - 1) / 2d)) - (Ry / 2);
        Point Pij = this.centerBoard;
        if (Xj != 0){
            Pij = Pij.add(vectorX.scale(Xj));
        }
        if (Yi != 0){
            Pij = Pij.add(vectorY.scale(Yi));
        }
        return Pij;
    }
}