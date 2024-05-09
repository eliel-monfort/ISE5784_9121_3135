package renderer;

import lighting.LightSource;
import lighting.PointLight;
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

    /**
     * The maximum recursion level for calculating color during ray tracing.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum threshold for the coefficient 'k' used in color calculation.
     * Values below this threshold are considered negligible and contribute little to the final color.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The initial coefficient 'k' for color calculation, represented as a 3D vector.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

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
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the final color at a given intersection point, including both local and global lighting effects,
     * and ambient light contribution.
     *
     * @param closestPoint The GeoPoint representing the closest intersection point.
     * @param ray The incident ray.
     * @return The final color at the intersection point, considering local and global effects, and ambient light.
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given intersection point, considering local and global lighting effects.
     *
     * @param geoPoint The GeoPoint representing the intersection point.
     * @param ray The incident ray.
     * @param level The recursion level for global effects.
     * @param k The coefficient vector for color calculation.
     * @return The resulting color at the intersection point, considering local and global lighting effects.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the local effects of light on a given intersection point, considering diffuse and specular reflections.
     *
     * @param gp The GeoPoint representing the intersection point.
     * @param ray The incident ray.
     * @param k The coefficient vector for color calculation.
     * @return The resulting color at the intersection point, considering local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0){
            return color;
        }
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
                }
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
        return material.kS.scale(Math.pow(Math.max(0, -r.dotProduct(v)), material.nShininess));
    }

    /**
     * Constructs a reflected ray at a given intersection point based on the incident ray and the surface normal.
     *
     * @param geoPoint The GeoPoint representing the intersection point.
     * @param ray The incident ray.
     * @return The reflected ray at the intersection point.
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray){
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirection();
        // r = v - 2 * (v * n) * n
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(geoPoint.point, r, n);
    }

    /**
     * Constructs a refracted ray at a given intersection point based on the incident ray and the surface normal.
     *
     * @param geoPoint The GeoPoint representing the intersection point.
     * @param ray The incident ray.
     * @return The refracted ray at the intersection point.
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray){
        return new Ray(geoPoint.point, ray.getDirection(), geoPoint.geometry.getNormal(geoPoint.point));
    }

    /**
     * Calculates the global lighting effects at a given intersection point by considering both
     * refracted and reflected rays recursively.
     *
     * @param gp The GeoPoint representing the intersection point.
     * @param ray The incident ray.
     * @param level The recursion level for global effects.
     * @param k The coefficient vector for color calculation.
     * @return The resulting color at the intersection point, considering global lighting effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR, level, k));
    }

    /**
     * Calculates the global lighting effect for a given ray, considering reflection or refraction,
     * and recursively determines the color contribution at the intersection point.
     *
     * @param ray The ray for which to calculate the global lighting effect.
     * @param kx The coefficient vector associated with the reflection (kR) or refraction (kT).
     * @param level The recursion level for global effects.
     * @param k The coefficient vector for color calculation.
     * @return The resulting color contribution from the global lighting effect.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Finds the closest intersection point between the given ray and the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection point.
     * @return The closest intersection point as a GeoPoint, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Calculates the transparency factor (ktr) at a given intersection point, considering the transparency of
     * the material along the path from the intersection point to a specified light source.
     *
     * @param gp The GeoPoint representing the intersection point.
     * @param light The LightSource to which transparency is calculated.
     * @param l The direction vector from the intersection point to the light source.
     * @param n The surface normal vector at the intersection point.
     * @return The transparency factor along the path from the intersection point to the light source.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        if(light.isSoftShadowed()){
            return this.softShadow(gp, light, lightDirection, n);
        }
        Ray ray = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));
        if (intersections == null) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint intersection : intersections) {
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * Calculates soft shadow for a point light source.
     * @param gp The GeoPoint representing the intersection point.
     * @param light The LightSource to calculate soft shadow for.
     * @param lightDirection The direction vector from the intersection point to the light source.
     * @param n The surface normal vector at the intersection point.
     * @return The transparency factor along the path from the intersection point to the light source.
     */
    private Double3 softShadow(GeoPoint gp, LightSource light, Vector lightDirection, Vector n){
        Vector vectorX, vectorY;
        if (lightDirection.equals(new Vector(1,0,0)) || lightDirection.equals(new Vector(-1,0,0))) {
            vectorY = lightDirection.crossProduct(new Vector(0,0,1));
        }
        else {
            vectorY = lightDirection.crossProduct(new Vector(1,0,0));
        }
        vectorX = lightDirection.crossProduct(vectorY);
        Double3 ktr = Double3.ZERO;
        PointLight PosLight = (PointLight) light;
        // TODO
        List<Point> points = PosLight.blackboard.jittered(vectorX, vectorY);
        for(Point point : points){
            List<GeoPoint> intersections = scene.geometries.
                    findGeoIntersections(new Ray(gp.point, point.subtract(gp.point),n), light.getDistance(gp.point));
            if (intersections == null) {
                ktr = ktr.add(Double3.ONE);
            }
            else {
                Double3 ktr_temp = Double3.ONE;
                for (GeoPoint geopoint : intersections) {
                    ktr_temp = ktr_temp.product(geopoint.geometry.getMaterial().kT);
                }
                ktr = ktr.add(ktr_temp);
            }
        }
        return ktr.scale(1d/(PosLight.blackboard.raysInBean()));
    }
}