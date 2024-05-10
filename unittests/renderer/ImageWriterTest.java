package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter Class
 * @author Ariel Atias
 */
public class ImageWriterTest {

    /**
     * Test method for
     * {@link renderer.ImageWriter#ImageWriter (renderer.ImageWriter)}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("TestYellowAndBlack",800,500);
        for (int i = 0; i <800 ; i++)
            for (int j = 0; j < 500; j++) {
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                } else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                } else {
                    imageWriter.writePixel(i, j, new Color(255, 255, 0));
                }
            }
        imageWriter.writeToImage();
    }
}