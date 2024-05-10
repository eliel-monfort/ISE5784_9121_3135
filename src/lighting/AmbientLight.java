package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a 3D environment.
 * Ambient light is a uniform and constant light that illuminates all objects in the scene.
 */
public class AmbientLight extends Light {

    /** A constant representing no ambient light, indicated by a black color and zero intensity. */
    public final static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an AmbientLight object with a specified intensity and scaling factor.
     *
     * @param IA The color representing the intensity of the ambient light.
     * @param KA The scaling factor applied to the ambient light intensity.
     */
    public AmbientLight(Color IA, Double3 KA){
        super(IA.scale(KA));
    }

    /**
     * Constructs an AmbientLight object with a specified intensity and scaling factor.
     *
     * @param IA The color representing the intensity of the ambient light.
     * @param KA The scaling factor applied to the ambient light intensity.
     */
    public AmbientLight(Color IA, double KA){
        super(IA.scale(KA));
    }
}