package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.ArrayList;
import java.util.List;


public class Blackboard {
    private double width;
    private double height;
    private int Nx = 1;
    private int Ny = 1;
    private Point centerPoint;
    private Vector vectorX;
    private Vector vectorY;
    private Point p0;

    public Blackboard(){}

    public Blackboard(double width, double height, int row, int column, Vector vectorX, Vector vectorY, Point p0) {
        this.width = width;
        this.height = height;
        this.Nx = row;
        this.Ny = column;
        this.vectorX = vectorX;
        this.vectorY = vectorY;
        this.p0 = p0;
    }

    public Blackboard setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
        return this;
    }

    public boolean isAntiAliasing(){
        return (this.Nx > 1) && (this.Ny > 1);
    }

    public List<Ray> jittered(){
        List<Ray> rays = new ArrayList<>();
        for (int i = 0; i < this.Ny; i++){
            for (int j = 0; j < this.Nx; j++){
                rays.add(this.jitteredHelper(j, i));
            }
        }
        return rays;
    }

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
