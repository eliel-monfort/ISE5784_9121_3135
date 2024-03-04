package renderer;

import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackboardTests {
    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder camera = Camera.getBuilder()
            .setVpSize(300, 300)
            .setImageWriter(new ImageWriter("softShadowsTestImage", 1000,1000))
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Point(10, -5, 100))
            .setDirection(new Vector(0, 0, -1), new Vector(1, 0, 0))
            .setVpDistance(1000)
            .setAntiAliasing(4,4);

    @Test
    void softShadowsTest() {
        scene.geometries.add(
                new Plane(Point.ZERO, new Point(0, 0, 1), new Point(0, 1, 0))
                        .setEmission(new Color(129, 133, 137))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere(5.001d, new Point(5, 0, 0)).setEmission(new Color(137, 148, 153))
                        .setMaterial(new Material().setKs(0.4).setShininess(70))
                );

        scene.setAmbientLight(new AmbientLight(new Color(BLACK), 0));

        scene.lights.add(
                new SpotLight(new Color(255,230,89), new Point(15, 5, 0), new Vector(-1, 0.1, 0)).setKl(0.1).setKq(0.002)//.setLightRadius(2)
        );

        camera.build()
                .renderImage()
                .writeToImage();
    }
}
