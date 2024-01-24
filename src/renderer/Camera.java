package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The `Camera` class represents a virtual camera in a three-dimensional space,
 * providing methods to construct rays for ray tracing.
 * The camera is defined by its position (`p0`), viewing direction (`vTo`),
 * up vector (`vUp`), right vector (`vRight`), screen width and height, and distance from the viewer to the screen.
 *
 * This class implements the `Cloneable` interface to support creating a deep copy of the camera.
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width = 0.0;
    private double height = 0.0;
    private double distance = 0.0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private Camera(){}

    /**
     * Gets the position of the camera.
     *
     * @return The position of the camera (`p0`).
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Gets the up vector of the camera.
     *
     * @return The up vector of the camera (`vUp`).
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the viewing direction vector of the camera.
     *
     * @return The viewing direction vector of the camera (`vTo`).
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the right vector of the camera.
     *
     * @return The right vector of the camera (`vRight`).
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the width of the camera's virtual screen.
     *
     * @return The width of the camera's virtual screen.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the camera's virtual screen.
     *
     * @return The height of the camera's virtual screen.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the distance from the camera to the virtual screen.
     *
     * @return The distance from the camera to the virtual screen.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns a new instance of the `Builder` class for constructing a `Camera`.
     *
     * @return A new instance of the `Builder` class.
     */
    public static Builder getBuilder(){
        return new Builder();
    }

    /**
     * Constructs a ray for the specified pixel coordinates on the virtual screen.
     *
     * @param nX The number of pixels in the horizontal direction.
     * @param nY The number of pixels in the vertical direction.
     * @param j The horizontal pixel index.
     * @param i The vertical pixel index.
     * @return A ray corresponding to the specified pixel coordinates.
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point PC = p0.add(vTo.scale(distance));

        double Rx = width/nX;
        double Ry = height/nX;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        Point Pij = PC;
        if (Xj != 0){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (Yi != 0){
            Pij = Pij.add(vUp.scale(Yi));
        }

        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * The `Builder` class facilitates the construction of a `Camera` instance with specified parameters.
     * It ensures that necessary rendering data is provided and performs validations on input values.
     */
    public static class Builder{
        final private Camera camera = new Camera();

        /**
         * Sets the image writer for the associated camera.
         *
         * @param _imageWriter The ImageWriter to be set for the camera.
         * @return This Builder object for method chaining.
         */
        public Builder setImageWriter(ImageWriter _imageWriter){
            this.camera.imageWriter = _imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the associated camera.
         *
         * @param _rayTracer The RayTracerBase to be set for the camera.
         * @return This Builder object for method chaining.
         */
        public Builder setRayTracer(RayTracerBase _rayTracer){
            this.camera.rayTracer = _rayTracer;
            return this;
        }

        /**
         * Sets the location of the camera.
         *
         * @param _p0 The position of the camera.
         * @return This `Builder` instance for method chaining.
         */
        public Builder setLocation(Point _p0){
            this.camera.p0 = _p0;
            return this;
        }

        /**
         * Sets the viewing direction and up vector of the camera.
         *
         * @param _vTo The viewing direction vector.
         * @param _vUp The up vector.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if the provided vectors are not orthogonal.
         */
        public Builder setDirection(Vector _vTo, Vector _vUp){
            if (!isZero(_vTo.dotProduct(_vUp))){
                throw new IllegalArgumentException("The two vectors (parameters) are not orthogonal.");
            }
            this.camera.vTo = _vTo.normalize();
            this.camera.vUp = _vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the virtual screen.
         *
         * @param _width The width of the virtual screen.
         * @param _height The height of the virtual screen.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if either width or height is negative.
         */
        public Builder setVpSize(double _width, double _height){
            if (_width < 0 || _height < 0){
                throw new IllegalArgumentException("One of the parameters is Illegal. Negative width or Negative height.");
            }
            this.camera.width = _width;
            this.camera.height = _height;
            return this;
        }

        /**
         * Sets the distance from the camera to the virtual screen.
         *
         * @param _distance The distance from the camera to the virtual screen.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if the distance is negative.
         */
        public Builder setVpDistance(double _distance){
            if (_distance < 0){
                throw new IllegalArgumentException("The distance given is Illegal. Negative distance.");
            }
            this.camera.distance = _distance;
            return this;
        }

        /**
         * Builds and returns the configured `Camera` instance.
         *
         * @return The configured `Camera` instance.
         * @throws MissingResourceException if any essential rendering data is missing or equals to zero.
         */
        public Camera build(){
            String ERORR = "Missing rendering data";

            if (this.camera.p0 == null){
                throw new MissingResourceException(ERORR, "Camera", "p0 is not defined.");
            }

            if (this.camera.vUp == null){
                throw new MissingResourceException(ERORR, "Camera", "vUp is not defined.");
            }

            if (this.camera.vTo == null){
                throw new MissingResourceException(ERORR, "Camera", "vTo is not defined.");
            }

            if (this.camera.imageWriter == null){
                throw new MissingResourceException(ERORR, "Camera", "imageWriter is not defined.");
            }

            if (this.camera.rayTracer == null){
                throw new MissingResourceException(ERORR, "Camera", "rayTracer is not defined.");
            }

            if (isZero(this.camera.width)){
                throw new MissingResourceException(ERORR, "Camera", "The width of the screen is zero.");
            }

            if (isZero(this.camera.height)){
                throw new MissingResourceException(ERORR, "Camera", "The height of the screen is zero.");
            }

            if (isZero(this.camera.distance)){
                throw new MissingResourceException(ERORR, "Camera", "The distance of the screen is zero.");
            }

            this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Renders the image by casting rays for each pixel and tracing them to determine the color.
     * @return The camera object after rendering the image.
     */
    public Camera renderImage(){
        for (int i = 0; i < this.imageWriter.getNy(); i++) {
            for (int j = 0; j < this.imageWriter.getNx(); j++) {
                this.castRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
            }
        }
        return this;
    }

    /**
     * Casts a ray for a specific pixel and traces it to determine the color, then writes the color to the image.
     * @param nX The width of the image.
     * @param nY The height of the image.
     * @param j The x-coordinate of the pixel.
     * @param i The y-coordinate of the pixel.
     */
    private void castRay(int nX, int nY, int j, int i){
        Ray ray = this.constructRay(nX, nY, j, i);
        Color color = this.rayTracer.traceRay(ray);
        this.imageWriter.writePixel(j, i, color);
    }

    /**
     * Prints a grid on the image with a specified interval and color.
     * @param interval The interval for the grid lines.
     * @param color The color of the grid lines.
     * @return The camera object after printing the grid.
     */
    public Camera printGrid(int interval, Color color){
        for (int i = 0; i < this.imageWriter.getNy(); i++) {
            for (int j = 0; j < this.imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0){
                    this.imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * Writes the rendered image to a file or display.
     */
    public void writeToImage(){
        this.imageWriter.writeToImage();
    }
}