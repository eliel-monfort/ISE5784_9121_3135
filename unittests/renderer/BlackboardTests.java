package renderer;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackboardTests {
    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder camera = Camera.getBuilder()
            .setVpSize(10, 10)
            .setImageWriter(new ImageWriter("testBlackboard", 5,5))
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(0, 0, 1))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setBlackboard(2,2);

}
