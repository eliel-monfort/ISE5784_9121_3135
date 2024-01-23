package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * The Scene class represents a 3D scene, which includes a name, background color,
 * ambient light, and geometries.
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     * Constructs a Scene object with a specified name.
     *
     * @param _name The name of the scene.
     */
    public Scene(String _name){
        this.name = _name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The color to set as the background.
     * @return This Scene object for method chaining.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set in the scene.
     * @return This Scene object for method chaining.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries in the scene.
     *
     * @param geometries The geometries to set in the scene.
     * @return This Scene object for method chaining.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}