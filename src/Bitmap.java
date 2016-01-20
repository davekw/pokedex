public class Bitmap {
    public final int width;
    public final int height;

    //each pixel has its own singular value coordinate ex. (0,0) = 0, (1,0) = 1 & (2,0) = 2
    public final int[] pixels;
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789.,;:()!?'\"+=x/\\*" +
            "abcdefghijklmnopqrstuvwxyz";

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void draw(Bitmap bitmap, int xOffs, int yOffs) {
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue; //skips the rest if off canvas

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue; //skips the rest if off canvas

                int src = bitmap.pixels[x + y * bitmap.width];
                if (src > 0)
                    pixels[xPix + yPix * width] = src; //set this bitmap pixel = to pixel from source bitmap
            }
        }
    }

    public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
        for (int y = 0; y < h; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
                if (src >= 0) {
                    pixels[xPix + yPix * width] = src * col;
                }
            }
        }
    }

    public void draw(String msg, int x, int y, int col) {
        int length = msg.length();
        for (int i = 0; i < length; i++) {
            int c = chars.indexOf(msg.charAt(i));
            if (c < 0) continue;
            int xx = c % 26;
            int yy = c / 26;
            draw(Art.font, x + i * 8, y, xx * 8, yy * 8, 8, 8, col);
        }
    }

    public void drawFront(int index, int x, int y) {
        index--;
        int xx = index % 31;
        int yy = index / 31;
        draw(Art.spritesFront, x, y, xx * 96, yy * 96, 96, 96, 1);
    }

    public void drawBack(int index, int x, int y) {
        index--;
        int xx = index % 31;
        int yy = index / 31;
        draw(Art.spritesBack, x, y, xx * 96, yy * 96, 96, 96, 1);
    }
}
