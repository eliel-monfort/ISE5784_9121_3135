package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source in a 3D scene.
 * Lights contribute to the illumination of objects in the scene by emitting light.
 */
abstract class Light {
    protected Color intensity;

    /**
     * Constructs a Light with the specified intensity.
     *
     * @param intensity The Color representing the intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light.
     *
     * @return The Color representing the intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}