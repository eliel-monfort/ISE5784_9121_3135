package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class serves as a base class for ray tracing algorithms in a 3D scene.
 * It provides a common structure for ray tracers and requires subclasses to implement the
 * specific logic for tracing rays.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param scene The Scene object representing the 3D scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract method that must be implemented by subclasses to define the
     * ray tracing logic for rendering a color based on a given Ray.
     *
     * @param ray The Ray object representing the ray to be traced.
     * @return The Color representing the result of tracing the given ray.
     */
    abstract public Color traceRay(Ray ray);
}
