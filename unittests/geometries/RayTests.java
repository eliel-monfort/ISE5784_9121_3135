package geometries;

public class RayTests {
     void testPositiveDistance() {
        Ray ray = new Ray(/* הכנס את הנתונים המתאימים */);
        Point point = ray.getPoint(2.0);
        // בדיקות אם הנקודה מתקבלת כמצופה
          assertTrue(point.equals(new Point(3.0, 4.0, 5.0)));
    }

    @Test
     void testZeroDistance() {
        Ray ray = new Ray(/* הכנס את הנתונים המתאימים */);
        Point point = ray.getPoint(0.0);
        // בדיקות אם הנקודה מתקבלת כמצופה
        assertTrue(point.equals(ray.getOrigin()));
    }

    @Test
    public void testNegativeDistance() {
        Ray ray = new Ray(/* הכנס את הנתונים המתאימים */);
        assertThrows(IllegalArgumentException.class, () -> ray.getPoint(-1.0));
    }
}
