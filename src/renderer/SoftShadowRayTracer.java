package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.Double3;
import primitives.Vector;
import scene.Scene;

public class SoftShadowRayTracer extends SimpleRayTracer{

    /**
     * Constructs a SimpleRayTracer object with the specified scene.
     *
     * @param scene The Scene object representing the 3D scene to be rendered.
     */
    public SoftShadowRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    protected Double3 transparency(Intersectable.GeoPoint gp, LightSource light, Vector l, Vector n) {
        if (light.isSoftShadowed()) {
            return super.transparency(gp, light, l, n);
        }

        // TODO: Soft Shadow calculation.

        return null;
    }
}
