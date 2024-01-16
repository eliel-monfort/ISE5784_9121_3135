package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraRayIntersectionsTests {
    private void assertEqualsCountOfIntersections(Camera camera, Intersectable geometry, int expected, String msg){
        int count = 0;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                var intersections = geometry.findIntersections(camera.constructRay(3, 3, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        assertEquals(expected, count, msg);
    }

    private final Camera.Builder camera1 = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1);

//    private final Camera.Builder camera2 = Camera.getBuilder()
//            .setLocation(new Point(0, 0, 0.5))
//            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
//            .setVpSize(3, 3)
//            .setVpDistance(1);
    @Test
    void CameraSphereIntersections() {
        //
        assertEqualsCountOfIntersections(camera1.setLocation(Point.ZERO).build(),
                new Sphere(1, new Point(0, 0, -3)), 2,
                "Wrong number of camera's intersection points in sphere.");

        //
        assertEqualsCountOfIntersections(camera1.setLocation(new Point(0, 0, 0.5)).build(),
                new Sphere(2.5, new Point(0, 0, -2.5)), 18,
                "Wrong number of camera's intersection points in sphere.");

        //
        assertEqualsCountOfIntersections(camera1.setLocation(new Point(0, 0, 0.5)).build(),
                new Sphere(2, new Point(0, 0, -2)), 10,
                "Wrong number of camera's intersection points in sphere.");

        //
        assertEqualsCountOfIntersections(camera1.setLocation(new Point(0, 0, 0.5)).build(),
                new Sphere(4, new Point(0, 0, -2)), 9,
                "Wrong number of camera's intersection points in sphere.");

        //
        assertEqualsCountOfIntersections(camera1.setLocation(new Point(0, 0, 0.5)).build(),
                new Sphere(0.5, new Point(0, 0, 1)), 0,
                "Wrong number of camera's intersection points in sphere.");
    }

    @Test
    void CameraPlaneIntersections() {

    }

    @Test
    void CameraTriangleIntersections() {

    }
}
