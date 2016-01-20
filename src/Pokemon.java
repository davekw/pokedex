import java.io.File;
import java.util.Scanner;

public class Pokemon {
    public int index = 1;
    public String name;
    public int hp;
    public int att;
    public int def;
    public int spA;
    public int spD;
    public int speed;
    public double rating;
    public boolean front = true;
    private Scanner scanner;

    public Pokemon() {
        openFile();
        readFile();
        closeFile();
    }

    public void tick(InputHandler input) {
        input.update();
        if (input.right && index < 649) {
            index++;
            openFile();
            readFile();
            closeFile();
        }
        if (input.left && index > 1) {
            index--;
            openFile();
            readFile();
            closeFile();
        }
        if (input.up) {
            front = false;
        }
        if (input.down) {
            front = true;
        }
        rating = (att * 0.191 + def * 0.191 + spA * 0.238 + spD * 0.191 + speed * 0.191)/120.24;
    }

    public void openFile() {
        try {
            scanner = new Scanner(new File("res/text.txt"));
        } catch (Exception e) {
            System.out.println("could not find file");
        }
    }

    public void readFile() {
        for (int i = 0; i < index - 1; i++) {
            scanner.nextLine();
        }
        scanner.next();

        name = scanner.next();

        if (scanner.hasNextInt())
            hp = scanner.nextInt();
        if (scanner.hasNextInt())
            att = scanner.nextInt();
        if (scanner.hasNextInt())
            def = scanner.nextInt();
        if (scanner.hasNextInt())
            spA = scanner.nextInt();
        if (scanner.hasNextInt())
            spD = scanner.nextInt();
        if (scanner.hasNextInt())
            speed = scanner.nextInt();
    }

    public void closeFile() {
        scanner.close();
    }
}
