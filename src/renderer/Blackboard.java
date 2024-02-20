package renderer;

import primitives.Color;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {

    private boolean AntiAliasing = false;

    private int rays;

    public Blackboard ENABLE(int rays){
        if (rays < 0){
            throw new IllegalArgumentException("The amount of rays given is Illegal. Negative amount.");
        }
        if (rays != 0 && rays != 1) {
            AntiAliasing = true;
            this.rays = rays;
        }
        return this;
    }

    public Blackboard DISABLE(){
        AntiAliasing = false;
        return this;
    }

    public boolean isAntiAliasing() {
        return AntiAliasing;
    }

    public int getRays() {
        return rays;
    }

    public Color AverageColors(List<Color> colors){
        Color result = colors.get(0);
        for (int i = 1; i < colors.size(); i++){
            result.add(colors.get(i));
        }
        return result.reduce(colors.size());
    }
}
