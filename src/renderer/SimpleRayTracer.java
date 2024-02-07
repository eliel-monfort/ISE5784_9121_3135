package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


import java.util.List;

/**
 * The SimpleRayTracer class is a basic implementation of the RayTracerBase,
 * providing a simple template for ray tracing in a 3D scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer object with the specified scene.
     *
     * @param scene The Scene object representing the 3D scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the color at a specified point/pixel in the scene based on a geometry.
     *
     * @param point The Point in the scene for which to calculate the color.
     * @return The Color of the point.
     */
    private Color calcColor(GeoPoint geoPoint){
        return geoPoint.geometry.getEmission().add(scene.ambientLight.getIntensity());
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null){
            return this.scene.background;
        }
        GeoPoint closestP = ray.findClosestGeoPoint(intersections);
        return calcColor(closestP);
    }
}
