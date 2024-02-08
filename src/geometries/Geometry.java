package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 *  The Geometry interface representing a generic geometry in three-dimensional space.
 */
abstract public class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Retrieves the emission color.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return this.emission;
    }

    /**
     * Return the material of this geometry.
     *
     * @return The Material object representing the visual properties of the object.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to be set.
     * @return This geometry object with the updated emission color.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Sets the material for this geometry and returns the updated instance.
     *
     * @param material The Material object to set for the geometry.
     *                 It defines the visual properties of the geometry.
     * @return The updated Geometry instance with the new material.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Abstract method to retrieve the normal vector to the geometry at a specified point on its surface.
     *
     * @param point_on_body The point on the surface of the geometry for which to find the normal vector.
     * @return The normal vector to the geometry at the specified point.
     */
    abstract public Vector getNormal(Point point_on_body);
}