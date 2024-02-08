package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene, which includes a name, background color,
 * ambient light, and geometries.
 */
public class Scene {

    /** The name of the scene. */
    public String name;

    /** The background color of the scene. */
    public Color background = Color.BLACK;

    /** The ambient light in the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The collection of geometries in the scene. */
    public Geometries geometries = new Geometries();

    /** The list of light sources in the scene. */
    public List<LightSource> lights = new LinkedList<>();

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

    /**
     * Sets the list of light sources of the scene object.
     *
     * @param lights A List of LightSource objects to set for this object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}