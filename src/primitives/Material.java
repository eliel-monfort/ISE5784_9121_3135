package primitives;

/**
 * Represents the material properties of an object in a 3D scene.
 * The material determines how the object interacts with light and affects its visual appearance.
 */
public class Material {

    /** The diffuse reflection coefficient of the material. */
    public Double3 kD = Double3.ZERO;

    /** The specular reflection coefficient of the material. */
    public Double3 kS = Double3.ZERO;

    /** The shininess factor of the material, affecting the size and sharpness of specular highlights. */
    public int nShininess = 0;

    /** The transmission (refraction) coefficient of the material. */
    public Double3 kT = Double3.ZERO;

    /** The reflection coefficient of the material. */
    public Double3 kR = Double3.ZERO;

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material with a single value.
     *
     * @param kD The diffuse reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material with a single value.
     *
     * @param kS The specular reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess The shininess factor to set.
     * @return The updated Material instance.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the transmission (refraction) coefficient of the material.
     *
     * @param kT The transmission coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the transmission (refraction) coefficient of the material with a single value.
     *
     * @param kT The transmission coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the reflection coefficient of the material with a single value.
     *
     * @param kR The reflection coefficient to set.
     * @return The updated Material instance.
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}