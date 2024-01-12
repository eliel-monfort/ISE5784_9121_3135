package geometries;

public class Geometries implements Intersectable {

    // שדה פרטי של רשימת גופים
    private List<Intersectable> geometriesList;

    // בנאי ברירת מחדל
    public Geometries() {
        geometriesList = new LinkedList<>();
    }

    // בנאי עם החתימה המבוקשת
    public Geometries(Intersectable... geometries) {
        this(); // קריאה לבנאי ברירת המחדל
        add(geometries);
    }

    // מתודה להוספת גופים לרשימה
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            geometriesList.add(geometry);
        }
    }

    // מימודה המממשת את הממשק Intersectable
    @Override
    public List<Intersection> findIntersections(Ray ray) {
        List<Intersection> intersections = new LinkedList<>();
        for (Intersectable geometry : geometriesList) {
            intersections.addAll(geometry.findIntersections(ray));
        }
        return intersections;
    }
}
