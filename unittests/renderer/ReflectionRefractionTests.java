/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
      .setRayTracer(new SimpleRayTracer(scene));

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                          .setKl(0.0004).setKq(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
         .setKl(0.00001).setKq(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setKl(4E-5).setKq(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(200, 200)
         .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
         .build()
         .renderImage()
         .writeToImage();
   }

   /**
   * The picture we created + BONUS!!!
    *
    * @author Eliel monfort & Ariel Atias*/
   @Test
   public void impressivePicture(){
      scene.geometries.add(

              // The part for the house
              new Triangle(new Point(0, 40, 0), new Point(-20, 50, 0), new Point(0, 40, 20))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                      .setEmission(new Color(128,128,128)),

              new Triangle(new Point(-20, 50, 20), new Point(-20, 50, 0), new Point(0, 40, 20))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                      .setEmission(new Color(128,128,128)),

              new Triangle(new Point(0, 40, 0), new Point(-10, 20, 0), new Point(0, 40, 20))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(5))
                      .setEmission(new Color(128,128,128)),

              new Triangle(new Point(-10, 20, 0), new Point(-10, 20, 20), new Point(0, 40, 20))
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
              new Triangle(new Point(-50, 80, 0.1), new Point(-5, -15, 0.1), new Point(20, 60, 0.1))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
                      .setEmission(new Color(1, 54, 32)),

              new Triangle(new Point(-5, -15, 0.1), new Point(-75, 5, 0.1), new Point(-50, 80, 0.1))
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

              new Sphere(5d, new Point(-50, 20, 20.8))
                      .setEmission(new Color(135, 103, 90))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

              new Sphere(4d, new Point(-55, 10, 12))
                      .setEmission(new Color(135, 103, 90))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

              new Sphere(4d, new Point(-45, 30, 10))
                      .setEmission(new Color(135, 103, 90))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),

              // The sun - The point light source
              new Sphere(12d, new Point(-15, 75, 143))
                      .setEmission(new Color(253, 184, 19))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7)),

              // The mirror triangle that shows the back of the house
              new Triangle(new Point(-100, 50, 20), new Point(0, -100, 20), new Point(0, 0, 120))
                      .setEmission(new Color(20, 20, 20))
                      .setMaterial(new Material().setKr(1))
      );

      scene.setAmbientLight(new AmbientLight(new Color(BLACK), 0.5)).setBackground(new Color(135, 206, 235));

      scene.lights.add(
              new PointLight(new Color(253, 184, 19), new Point(-15, 75, 143))
                      .setKl(4E-5).setKq(2E-7));

      cameraBuilder.setLocation(new Point(600, 10, 40)).setVpDistance(1000)
              .setDirection(new Vector(-1, 0, 0), new Vector(0, 0, 1))
              .setVpSize(400, 400)
              .setImageWriter(new ImageWriter("ThePictureWeCreated", 1000, 1000))
              .build()
              .renderImage()
              .writeToImage();
   }
}
