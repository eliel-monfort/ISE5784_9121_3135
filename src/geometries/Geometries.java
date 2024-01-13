package geometries;

public class Geometries {
    private list<Intersectable> geometriesList = new LinkedList<>();
    public Geometries() {
    }
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
    public List<Intersection> findIntersections(Ray ray) {
        return null;
    }
}
