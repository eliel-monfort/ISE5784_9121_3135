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
import static java.awt.Color.BLACK;
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
        assertEquals(4, blackboard1.jittered(new Vector(0, 1, 0), new Vector(0, 0, 1)).size(),
                "ERROR: The number of rays in a beam is incorrect (for even number of rays)");

        // A beam with an odd number of rays
        assertEquals(15, blackboard2.jittered(new Vector(0, 1, 0), new Vector(0, 0, 1)).size(),
                "ERROR: The number of rays in a beam is incorrect (for odd number of rays)");

        // A beam with a large number of rays
        assertEquals(289, blackboard3.jittered(new Vector(0, 1, 0), new Vector(0, 0, 1)).size(),
                "ERROR: The number of rays in a beam is incorrect (for a large number of rays)");

        // =============== Boundary Values Tests ==================
        // A beam with one beam
        assertEquals(1, blackboard4.jittered(new Vector(0, 1, 0), new Vector(0, 0, 1)).size(),
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
        scene.geometries.add(

                // The part for the house
                new Triangle(new Point(0, 40, 0), new Point(-20, 50, 0), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-20, 50, 0), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-10, 20, 0), new Point(-30, 30, 0), new Point(-10, 20, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-30, 30, 0), new Point(-10, 20, 20), new Point(-30, 30, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 0), new Point(-30, 30, 0), new Point(-30, 30, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-30, 30, 20), new Point(-20, 50, 0))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                        .setEmission(new Color(128,128,128)),

                new Triangle(new Point(-20, 50, 20), new Point(-15, 35, 35), new Point(0, 40, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(0, 40, 20), new Point(-15, 35, 35), new Point(-10, 20, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(-10, 20, 20), new Point(-30, 30, 20), new Point(-15, 35, 35))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Triangle(new Point(-30, 30, 20), new Point(-15, 35, 35), new Point(-20, 50, 20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10))
                        .setEmission(new Color(255,0,0)),

                new Sphere(8d, new Point(-15, 35, 43))
                        .setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The two triangular to the ground
                new Triangle(new Point(-30, 110, 0.1), new Point(12, -40, 0.1), new Point(77, 55, 0.1))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
                        .setEmission(new Color(1, 54, 32)),

                new Triangle(new Point(-30, 110, 0.1), new Point(12, -40, 0.1), new Point(-90, 0, 0.1))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
                        .setEmission(new Color(1, 54, 32)),

                // the plain for the sea
                new Plane(new Point(1, 1, 0), new Point(1, 0, 0), new Point(0, 0, 0))
                        .setMaterial(new Material().setKd(0).setKs(0).setShininess(10)
                                .setKt(new Double3(0.5, 0, 0))).setEmission(new Color(1, 67, 104))
                        .setMaterial(new Material().setKr(0.02)),

                // The parts for the man behind the house
                new Sphere(8d, new Point(-50, 20, 7.9))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(5d, new Point(-50, 20, 20))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(4d, new Point(-55, 10, 12))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(4d, new Point(-45, 30, 10))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The parts for the second man next to the house
                new Sphere(8d, new Point(15, 60, 7.9))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(5d, new Point(15, 60, 20))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(4d, new Point(18, 69, 11))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                new Sphere(4d, new Point(18, 52, 15))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

                // The ball in the house
                new Sphere(5d, new Point(-2, 30, 4.9))
                        .setEmission(new Color(135, 103, 90))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );

        scene.setAmbientLight(new AmbientLight(new Color(BLACK), 0.5)).setBackground(new Color(135, 206, 235));

        scene.lights.add(
                new DirectionalLight(new Color(253, 184, 19), new Vector(0.5, -1, -0.9))
        );

        scene.lights.add(
                new PointLight(new Color(253, 184, 19), new Point(-15, 35, 30), 4, 4, 9, 9)
                        .setKl(8E-10).setKq(4E-14)
        );

        scene.lights.add(
                new SpotLight(new Color(253, 184, 19), new Point(-10, 60, 50), new Vector(1, 0, -1), 10, 10, 9, 9)
                        .setKl(8E-10).setKq(4E-14)
        );

        camera
                .setLocation(new Point(300, 30, 75))
                .setDirection(new Vector(-1, 0, -0.2), new Vector(-0.2, 0, 1))
                .setVpSize(400, 400)
                .setImageWriter(new ImageWriter("AntiAliasing+SoftShadowsTestImage", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .setAntiAliasing(9, 9)
                .build()
                .renderImage()
                .writeToImage();
    }
}