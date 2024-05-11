package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import static primitives.Util.isZero;

/**
 * The `Camera` class represents a virtual camera in a three-dimensional space,
 * providing methods to construct rays for ray tracing.
 * The camera is defined by its position (`p0`), viewing direction (`vTo`),
 * up vector (`vUp`), right vector (`vRight`), screen width and height, and distance from the viewer to the screen.
 * This class implements the `Cloneable` interface to support creating a deep copy of the camera.
 */
public class Camera implements Cloneable {

    /** The position of the camera. */
    private Point p0;

    /** The up vector of the camera. */
    private Vector vUp;

    /** The direction vector the camera is looking at. */
    private Vector vTo;

    /** The right vector of the camera. */
    private Vector vRight;

    /** The width of the view plane. */
    private double width = 0.0;

    /** The height of the view plane. */
    private double height = 0.0;

    /** The distance between the camera and the view plane. */
    private double distance = 0.0;

    /** The ImageWriter used for rendering. */
    private ImageWriter imageWriter;

    /** The RayTracerBase used for rendering. */
    private RayTracerBase rayTracer;

    /** The Blackboard used for rendering a pixel with a beam of rays. */
    private Blackboard blackboard;

    /** The center of the Pixel used for helping rendering the current pixel with a beam of rays. */
    private Point centerPixel;

    /**The division of the pixel on the X axis*/
    private int nXpixel = 1;

    /**The division of the pixel on the Y axis*/
    private int nYpixel = 1;

    /** Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * <ul>
     */
    private PixelManager pixelManager;

    /** Represents the count of threads in the program. */
    private int threadsCount = 0;

    /** The interval for debug print of progress percentage in Console window/tab. */
    private double printInterval = 0;

    /** The depth of the recursion of the recursive function for adaptive Anti-Aliasing. */
    private int AdaptiveDepth = 0;

    /**
     * Constructs a new Camera instance.
     */
    private Camera() {}

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
     * Gets the ImageWriter used for rendering.
     *
     * @return The ImageWriter used for rendering.
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
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
     * The `Builder` class facilitates the construction of a `Camera` instance with specified parameters.
     * It ensures that necessary rendering data is provided and performs validations on input values.
     */
    public static class Builder{
        final private Camera camera = new Camera();

        /**
         * Sets the image writer for the associated camera.
         *
         * @param imageWriter The ImageWriter to be set for the camera.
         * @return This Builder object for method chaining.
         */
        public Builder setImageWriter(ImageWriter imageWriter){
            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the associated camera.
         *
         * @param rayTracer The RayTracerBase to be set for the camera.
         * @return This Builder object for method chaining.
         */
        public Builder setRayTracer(RayTracerBase rayTracer){
            this.camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the location of the camera.
         *
         * @param p0 The position of the camera.
         * @return This `Builder` instance for method chaining.
         */
        public Builder setLocation(Point p0){
            this.camera.p0 = p0;
            return this;
        }

        /**
         * Sets the viewing direction and up vector of the camera.
         *
         * @param vTo The viewing direction vector.
         * @param vUp The up vector.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if the provided vectors are not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp){
            if (!isZero(vTo.dotProduct(vUp))){
                throw new IllegalArgumentException("The two vectors (parameters) are not orthogonal.");
            }
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the virtual screen.
         *
         * @param width The width of the virtual screen.
         * @param height The height of the virtual screen.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if either width or height is negative.
         */
        public Builder setVpSize(double width, double height){
            if (width < 0 || height < 0){
                throw new IllegalArgumentException("One of the parameters is Illegal. Negative width or Negative height.");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the virtual screen.
         *
         * @param distance The distance from the camera to the virtual screen.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if the distance is negative.
         */
        public Builder setVpDistance(double distance){
            if (distance < 0){
                throw new IllegalArgumentException("The distance given is Illegal. Negative distance.");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the number of pixel divisions for rendering optimization.
         *
         * @param nXpixel The number of divisions in the horizontal direction of the pixel.
         * @param nYpixel The number of divisions in the vertical direction of the pixel.
         * @return This `Builder` instance for method chaining.
         * @throws IllegalArgumentException if either nXpixel or nYpixel is less than 1.
         */
        public Builder setAntiAliasing(int nXpixel, int nYpixel){
            if (nXpixel < 1 || nYpixel < 1){
                throw new IllegalArgumentException("Pixel division given is Illegal.");
            }
            this.camera.nXpixel = nXpixel;
            this.camera.nYpixel = nYpixel;
            return this;
        }

        /**
         * Sets the depth of the adaptive Anti-Aliasing for the recursive function.
         *
         * @param AdaptiveDepth The depth of the adaptive Anti-Aliasing to be set for the recursive function.
         * @return This Builder object for method chaining.
         */
        public Builder setAntiAliasing(int AdaptiveDepth) {
            if (AdaptiveDepth < 0){
                throw new IllegalArgumentException("Adaptive depth given is Illegal.");
            }
            this.camera.AdaptiveDepth = AdaptiveDepth;
            return this;
        }

        /**
         * Sets the number of threads for multithreading in the camera.
         *
         * @param threadsCount The number of threads to be set for multithreading.
         * @return The updated Builder instance.
         */
        public Builder setMultithreading(int threadsCount) {
            if (threadsCount < 0){
                throw new IllegalArgumentException("Threads count given is Illegal.");
            }
            this.camera.threadsCount = threadsCount;
            return this;
        }

        /**
         * Sets the print interval for debug information in the camera.
         *
         * @param printInterval The print interval to be set for debug information.
         * @return The updated Builder instance.
         */
        public Builder setDebugPrint(double printInterval) {
            this.camera.printInterval = printInterval;
            return this;
        }

        /**
         * Builds and returns the configured `Camera` instance.
         *
         * @return The configured `Camera` instance.
         * @throws MissingResourceException if any essential rendering data is missing or equals to zero.
         */
        public Camera build(){
            String ERORR = "Camera: Missing rendering data: ";

            if (this.camera.p0 == null){
                throw new MissingResourceException(ERORR + "p0 is not defined.", "Camera", "");
            }

            if (this.camera.vUp == null){
                throw new MissingResourceException(ERORR + "vUp is not defined.", "Camera", "");
            }

            if (this.camera.vTo == null){
                throw new MissingResourceException(ERORR + "vTo is not defined.", "Camera", "");
            }

            if (this.camera.imageWriter == null){
                throw new MissingResourceException(ERORR + "imageWriter is not defined.", "Camera", "");
            }

            if (this.camera.rayTracer == null){
                throw new MissingResourceException(ERORR + "rayTracer is not defined.", "Camera", "");
            }

            if (isZero(this.camera.width)){
                throw new MissingResourceException(ERORR + "The width of the screen is zero.", "Camera", "");
            }

            if (isZero(this.camera.height)){
                throw new MissingResourceException(ERORR + "The height of the screen is zero.", "Camera", "");
            }

            if (isZero(this.camera.distance)){
                throw new MissingResourceException(ERORR + "The distance of the screen is zero.", "Camera", "");
            }

            this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

            if (this.camera.nXpixel > 1 && this.camera.nYpixel > 1){
                this.camera.blackboard = new Blackboard(
                        this.camera.getWidth() / this.camera.getImageWriter().getNx(),
                        this.camera.getHeight() / this.camera.getImageWriter().getNy(),
                        this.camera.nXpixel,
                        this.camera.nYpixel);
            }
            else {
                this.camera.blackboard = new Blackboard();
            }

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This function renders image's pixel color map from the scene
     * included in the ray tracer object
     *
     * @return the camera object itself
     */
    public Camera renderImage(){
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, this.printInterval);
        if (this.threadsCount == 0) {
            for (int i = 0; i < this.imageWriter.getNy(); i++) {
                for (int j = 0; j < this.imageWriter.getNx(); j++) {
                    this.castRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
                }
            }
        }
        else { // see further... option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (this.threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try { for (var thread : threads) thread.join(); }
            catch (InterruptedException ignore) {}
        }
        return this;
    }

    /**
     * Casts a ray for the specified pixel coordinates and performs rendering.
     *
     * @param nX The number of pixels in the horizontal direction.
     * @param nY The number of pixels in the vertical direction.
     * @param j The horizontal pixel index.
     * @param i The vertical pixel index.
     */
    private void castRay(int nX, int nY, int j, int i) {
        Color color = Color.BLACK;
        Ray ray = this.constructRay(nX, nY, j, i);
        this.blackboard.setCenterPoint(this.centerPixel);
        List<Point> points = new ArrayList<>();
        if (this.AdaptiveDepth > 0 || (this.nXpixel > 1 && this.nYpixel > 1)){
            if (this.AdaptiveDepth > 0){
                points = this.AdaptiveAntiAliasing(this.centerPixel, this.width / this.getImageWriter().getNx(),
                        this.height / this.getImageWriter().getNy(),
                        points, this.AdaptiveDepth);
            }
            else {
                points = this.blackboard.jittered(this.vRight, this.vUp);
            }
            for (Point point : points){
                color = color.add(this.rayTracer.traceRay(new Ray(this.p0, point.subtract(this.p0))));
            }
            color = color.reduce(points.size());
        }
        else {
            color = this.rayTracer.traceRay(ray);
        }
        this.imageWriter.writePixel(j, i, color);
        pixelManager.pixelDone();
    }

    /**
     * Performs adaptive Anti-Aliasing for the camera.
     *
     * @param centerBoard   The center point for the anti-aliasing grid.
     * @param w             The width of the anti-aliasing grid.
     * @param h             The height of the anti-aliasing grid.
     * @param points        The list of points to perform anti-aliasing on.
     * @param AdaptiveDepth The depth of adaptive anti-aliasing.
     * @return The list of points after performing adaptive anti-aliasing.
     */
    private List<Point> AdaptiveAntiAliasing(Point centerBoard, double w, double h, List<Point> points, int AdaptiveDepth) {
        List<Point> tempPoints = new ArrayList<>();
        Blackboard tempBoard = new Blackboard(w, h, 2, 2);
        tempBoard.setCenterPoint(centerBoard);
        tempPoints = tempBoard.grid(this.vRight, this.vUp);
        if (AdaptiveDepth == 0) {
            return tempPoints;
        }
        Color midColor = this.rayTracer.traceRay(new Ray(this.p0, centerBoard.subtract(this.p0)));
        for (int i = 0; i < 4; i++) {
            if (!this.rayTracer.traceRay(new Ray(this.p0, tempPoints.get(i).subtract(this.p0))).equals(midColor)) {
                points.addAll(this.AdaptiveAntiAliasing(tempPoints.get(i), w / 2, h / 2, points, AdaptiveDepth - 1));
            }
        }
        return tempPoints;
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
        double Ry = height/nY;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        Point Pij = PC;
        if (!isZero(Xj)){
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)){
            Pij = Pij.add(vUp.scale(Yi));
        }
        this.centerPixel = Pij;
        return new Ray(p0, Pij.subtract(p0));
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