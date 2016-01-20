import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Art {
    public static Bitmap font = loadBitmap2("/font.png");
    public static Bitmap spritesFront = loadBitmap("/spritesFront.png");
    public static Bitmap spritesBack = loadBitmap("/spritesBack.png");

    /**
     *  pictures
     */
    public static Bitmap loadBitmap(String fileName) {
        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);
            img.getRGB(0, 0, w, h, result.pixels, 0, w);
            for (int i = 0; i < result.pixels.length; i++) {
                int in = result.pixels[i];
                int col = (in & 0xffffff);
                if (in == 0xffff00ff) col = -1;
                result.pixels[i] = col;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * words
     */
    public static Bitmap loadBitmap2(String fileName) {
        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);
            img.getRGB(0, 0, w, h, result.pixels, 0, w);
            for (int i = 0; i < result.pixels.length; i++) {
                int in = result.pixels[i];
                int col = (in & 0xf) >> 2;
                if (in == 0xffff00ff) col = -1;
                result.pixels[i] = col;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
