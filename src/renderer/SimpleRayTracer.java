package renderer;

import geometries.Triangle;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The SimpleRayTracer class is a basic implementation of the RayTracerBase,
 * providing a simple template for ray tracing in a 3D scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    //####################################################################################
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INITIAL_K = Double3.ONE;

    //####################################################################################

    /**
     * Constructs a SimpleRayTracer object with the specified scene.
     *
     * @param scene The Scene object representing the 3D scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and calculates the color at the intersection point.
     *
     * @param ray The Ray object representing the traced ray.
     * @return The Color representing the calculated color at the intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    //############################################################################################
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    //############################################################################################

    /**
     * Calculates the local lighting effects at the intersection point.
     *
     * @param gp  The GeoPoint representing the intersection point.
     * @param ray The Ray object representing the traced ray.
     * @return The Color representing the calculated local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return color;
        }
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) && unshaded(gp, lightSource, l, n)) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive reflection component.
     *
     * @param material The Material object representing the material properties.
     * @param nl       The dot product of the normal vector and light vector.
     * @return The calculated diffusive reflection component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection component.
     *
     * @param material The Material object representing the material properties.
     * @param n        The normal vector at the intersection point.
     * @param l        The light vector.
     * @param nl       The dot product of the normal vector and light vector.
     * @param v        The view vector.
     * @return The calculated specular reflection component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        return material.kS.scale(Math.pow(Math.max(0, -v.dotProduct(r)), material.nShininess));
    }

    /**
     * Checks if the given point is unshaded by the specified light source.
     * A point is considered unshaded if there are no objects between it and the light source.
     *
     * @param gp          The GeoPoint representing the point to be checked.
     * @param lightSource The LightSource object representing the light source.
     * @param l           The direction vector from the point to the light source.
     * @param n           The normal vector at the point.
     * @return True if the point is unshaded by the light source, false otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n){
        Vector lightDirection= l.scale(-1); // from point to light source

        Ray lightray = new Ray(gp.point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightray);

        if (intersections == null){
            return true;
        }
        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint p : intersections){
            if (alignZero(p.point.distance(gp.point) - lightDistance) <= 0){
                return false;
            }
        }
        return true;
    }

    //##########################################################################################
    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray){
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirection();

        // r = v - 2 * (v * n) * n
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));

        return new Ray(geoPoint.point, r);
    }

    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray){
        return new Ray(geoPoint.point, ray.getDirection());
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR, level, k));
    }


    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)){
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray){
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }
    //##########################################################################################
}