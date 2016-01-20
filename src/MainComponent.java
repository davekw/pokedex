import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class MainComponent extends Canvas implements Runnable {
    /**
     * fields
     */
    private static final int WIDTH = 64 * 4;
    private static final int HEIGHT = 64 * 4;
    private static final int SCALE = 2;
    private Screen screen;
    private Pokemon pokemon;
    private BufferedImage img;
    private int[] pixels;
    private boolean running;
    private Thread thread;
    private InputHandler inputHandler;

    /**
     * constructor
     */
    public MainComponent() {
        //size
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //screen
        pokemon = new Pokemon();
        screen = new Screen(WIDTH, HEIGHT, pokemon);

        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        setFocusable(true);

        //image
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    /**
     * start
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * stop
     */
    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int frames = 0;

        double unprocessedSeconds = 0;
        long lastTime = System.nanoTime();
        double secondsPerTick = 1 / 20.0;
        int tickCount = 0;

        while (running) {
            long now = System.nanoTime();
            long passedTime = now - lastTime;

            lastTime = now;

            if (passedTime < 0)
                passedTime = 0;

            if (passedTime > 100000000)
                passedTime = 100000000;

            unprocessedSeconds += passedTime / 1000000000.0;

            boolean ticked = false;
            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;

                tickCount++;
                if (tickCount % 20 == 0) {
                    System.out.println(frames + " fps");
                    lastTime += 1000;
                    frames = 0;
                }
            }

            if (ticked) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * game logic processing
     */
    private void tick() {
        pokemon.tick(inputHandler);
    }

    /**
     * render
     */
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        //render screen
        screen.render();

        //update canvas img
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
        bs.show();
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        MainComponent game = new MainComponent();
        JFrame frame = new JFrame("Pokedex by Dave");
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(game, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }
}
