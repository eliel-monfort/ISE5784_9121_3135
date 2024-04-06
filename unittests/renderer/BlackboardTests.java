package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
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
    final Camera.Builder camera = Camera.getBuilder()
            .setVpSize(300, 300)
            .setLocation(new Point(100, 0, 10))
            .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
            .setVpDistance(1000);

    @Test
    void jitteredTest() {

        Blackboard blackboard1 = new Blackboard(1, 1, 2, 2).setCenterPoint(new Point(10, 0, 5));
        Blackboard blackboard2 = new Blackboard(1, 1, 3, 5).setCenterPoint(new Point(10, 0, 5));
        Blackboard blackboard3 = new Blackboard(4, 4, 17, 17).setCenterPoint(new Point(10, 0, 5));
        Blackboard blackboard4 = new Blackboard(0, 0, 1, 1).setCenterPoint(new Point(10, 0, 5));

        // ============ Equivalence Partitions Tests ==============
        // A beam with an even number of rays
        assertEquals(4, blackboard1.jittered(new Point(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), null).size(),
                "ERROR: The number of rays in a beam is incorrect (for even number of rays)");

        // A beam with an odd number of rays
        assertEquals(15, blackboard2.jittered(new Point(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), null).size(),
                "ERROR: The number of rays in a beam is incorrect (for odd number of rays)");

        // A beam with a large number of rays
        assertEquals(289, blackboard3.jittered(new Point(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), null).size(),
                "ERROR: The number of rays in a beam is incorrect (for a large number of rays)");

        // =============== Boundary Values Tests ==================
        // A beam with one beam
        assertEquals(1, blackboard4.jittered(new Point(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1), null).size(),
                "ERROR: The number of rays in a beam is incorrect (for a large number of rays)");
    }

    @Test
    void AntiAliasingTest() {

        final Scene scene = new Scene("Test Anti-Aliasing");
        scene.geometries.add(
                new Sphere(10d, new Point(20, 0, 10))
                        .setEmission(new Color(0,0,250))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );
        scene.lights.add(new PointLight(new Color(255,255,255), new Point(40, 10, 30)));

        // Low Anti-Aliasing for 2X2 for testing
        camera.setImageWriter(new ImageWriter("AntiAliasingTestImage2X2", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .setAntiAliasing(2, 2)
                .build()
                .renderImage()
                .writeToImage();

        // High Anti-Aliasing for 9X9 for testing
        camera.setImageWriter(new ImageWriter("AntiAliasingTestImage9X9", 1000,1000))
                .setAntiAliasing(9, 9)
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    void softShadowsTest() {

        // A Soft-Shadows with a blackboard divided into 2x2 (the Soft-Shadows effect is weak)
        final Scene scene1 = new Scene("Test Soft-Shadows");
        scene1.geometries.add(
                new Plane(Point.ZERO, new Point(0, 0, 1), new Point(0, 1, 0))
                        .setEmission(new Color(129, 133, 137))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere(5d, new Point(0, 0, 5)).setEmission(new Color(137, 148, 153))
                        .setMaterial(new Material().setKs(0.4).setShininess(70))
        );
        scene1.lights.add(
                new SpotLight(new Color(255,230,89), new Point(15, 0, -10), new Vector(-1, 0, 1), 2, 2, 2, 2).setKl(0.1).setKq(0.002)
        );
        camera
                .setImageWriter(new ImageWriter("SoftShadowsTestImage2X2", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene1))
                .build()
                .renderImage()
                .writeToImage();


        //A Soft-Shadows with a blackboard divided into 9x9 (the Soft-Shadows effect is strong)
        final Scene scene2 = new Scene("Test Soft-Shadows");
        scene2.geometries.add(
                new Plane(Point.ZERO, new Point(0, 0, 1), new Point(0, 1, 0))
                        .setEmission(new Color(129, 133, 137))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere(5d, new Point(0, 0, 5)).setEmission(new Color(137, 148, 153))
                        .setMaterial(new Material().setKs(0.4).setShininess(70))
        );
        scene2.lights.add(
                new SpotLight(new Color(255,230,89), new Point(15, 0, -10), new Vector(-1, 0, 1), 2, 2, 9, 9).setKl(0.1).setKq(0.002)
        );
        camera
                .setImageWriter(new ImageWriter("SoftShadowsTestImage9X9", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene2))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    void AntiAliasing_And_SoftShadowTest() {
        final Scene scene = new Scene("Test Anti-Aliasing and Soft-Shadows");
        scene.setBackground(new Color(135, 206, 235));
        scene.geometries.add(
                new Plane(Point.ZERO, new Point(1, 0, 0), new Point(0, 1, 0))
                        .setEmission(new Color(55, 127, 3))
                        .setMaterial(new Material().setKd(0.5)),

                new Sphere(2d, new Point(0, -4, 2.1)).setEmission(new Color(255, 0, 0))
                        .setMaterial(new Material().setKs(0.4).setShininess(70)),

                new Triangle(new Point(6, -10, 0), new Point(-6, -10, 0), new Point(6, -5, 10))
                        .setEmission(new Color(0, 0, 255)),

                new Triangle(new Point(-6, -5, 10), new Point(-6, -10, 0), new Point(6, -5, 10))
                        .setEmission(new Color(0, 0, 255)),

                new Triangle(new Point(6, -10, 0), new Point(8, -5, 0), new Point(6, -5, 10))
                        .setEmission(new Color(0, 0, 255)),

                new Triangle(new Point(-6, -10, 0), new Point(-6, -5, 10), new Point(-8, -5, 0))
                        .setEmission(new Color(0, 0, 255))
        );
        scene.lights.add(
                new SpotLight(new Color(255,230,89), new Point(4, -5, 6), new Vector(-1, 0, -1)).setKl(0.1).setKq(0.002)
        );

        scene.lights.add(
                new PointLight(new Color(255,230,89), new Point(-4, -5, 6)).setKl(0.1).setKq(0.002)
        );

        scene.lights.add(new DirectionalLight(new Color(80, 50, 0), new Vector(0, 1, -1))
        );
        camera
                .setLocation(new Point(0, 55, 60))
                .setDirection(new Vector(0, -1, -1), new Vector(0, -1, 1))
                .setImageWriter(new ImageWriter("AntiAliasing+SoftShadowsTestImage", 1000,1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }
}