package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {

    private boolean AntiAliasing = false;

    private int rXc = 1;

    private void setAntiAliasing(boolean antiAliasing) {
        AntiAliasing = antiAliasing;
    }

    public void setRays(int rays) {
        if (rays < 0){
            throw new IllegalArgumentException("The amount of rays given is Illegal. Negative amount.");
        }
        if (rays != 0 && rays != 1) {
            this.rXc = (int)(Math.sqrt(rays));
            this.setAntiAliasing(true);
        }
    }

    public boolean isAntiAliasing() {
        return AntiAliasing;
    }

    public int getrXc() {
        return this.rXc;
    }

    public Point findCenter(int nX, int nY, int j, int i, Camera camera){
        Point PC = camera.getP0().add(camera.getvTo().scale(camera.getDistance()));

        double Rx = camera.getWidth()/nX;
        double Ry = camera.getHeight()/nY;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        Point Pij = PC;
        if (Xj != 0){
            Pij = Pij.add(camera.getvRight().scale(Xj));
        }
        if (Yi != 0){
            Pij = Pij.add(camera.getvUp().scale(Yi));
        }

        return Pij;
    }



    public Color AverageColors(List<Color> colors){
        Color result = colors.get(0);
        for (int i = 1; i < colors.size(); i++){
            result.add(colors.get(i)).reduce(2);
        }
        return result;
    }
}
