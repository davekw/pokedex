public class Screen extends Bitmap {

    private Pokemon pokemon;


    public Screen(int width, int height, Pokemon pokemon) {
        super(width, height);
        this.pokemon = pokemon;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0;
    }

    private void drawBars() {
        final int BARHEIGHT = 6;
        Bitmap hpBar = new Bitmap((int) (pokemon.hp / 255.0 * 80), BARHEIGHT);
        Bitmap attBar = new Bitmap((int) (pokemon.att / 255.0 * 80), BARHEIGHT);
        Bitmap defBar = new Bitmap((int) (pokemon.def / 255.0 * 80), BARHEIGHT);
        Bitmap spABar = new Bitmap((int) (pokemon.spA / 255.0 * 80), BARHEIGHT);
        Bitmap spDBar = new Bitmap((int) (pokemon.spD / 255.0 * 80), BARHEIGHT);
        Bitmap speedBar = new Bitmap((int) (pokemon.speed / 255.0 * 80), BARHEIGHT);

        for (int y = 0; y < BARHEIGHT; y++) {
            for (int x = 0; x < hpBar.width; x++)
                hpBar.pixels[y + x * 6] =
                        (int) (hpBar.width / 80.0 * 0x0000ff) + 0x997700;
            for (int x = 0; x < attBar.width; x++)
                attBar.pixels[y + x * 6] =
                        (int) (attBar.width / 80.0 * 0x0000ff) + 0x997700;
            for (int x = 0; x < defBar.width; x++)
                defBar.pixels[y + x * 6] =
                        (int) (defBar.width / 80.0 * 0x0000ff) + 0x997700;
            for (int x = 0; x < spABar.width; x++)
                spABar.pixels[y + x * 6] =
                        (int) (spABar.width / 80.0 * 0x0000ff) + 0x997700;
            for (int x = 0; x < spDBar.width; x++)
                spDBar.pixels[y + x * 6] =
                        (int) (spDBar.width / 80.0 * 0x0000ff) + 0x997700;
            for (int x = 0; x < speedBar.width; x++)
                speedBar.pixels[y + x * 6] =
                        (int) (speedBar.width / 80.0 * 0x0000ff) + 0x997700;
        }

        draw(hpBar, 64, 1);
        draw(attBar, 64, 9);
        draw(defBar, 64, 17);
        draw(spABar, 64, 25);
        draw(spDBar, 64, 33);
        draw(speedBar, 64, 41);
    }

    public void drawRating() {
        Bitmap rating = new Bitmap(8, (int) (pokemon.rating * 8));
        for (int i = rating.width * rating.height - 1; i >= 0; i--)
            rating.pixels[i] =
                    (int) (rating.height / 80.0 * 0x0000ff) + 0x997700;
        draw(rating, width - 8, 8 - rating.height);
        String r = "Rating:" + (int)(pokemon.rating * 100);
        draw(r, width - 8 * r.length() - 8, 0, 0xffffff);
    }

    public void render() {
        clear();

        //draw frame of backdrop
        Bitmap bdFrame = new Bitmap(100, 100);
        for (int i = 0; i < bdFrame.pixels.length; i++)
            bdFrame.pixels[i] = 0xadebe6;
        draw(bdFrame, (width - bdFrame.width) / 2, (height - bdFrame.width + 40) / 2);

        //draw backdrop
        Bitmap backdrop = new Bitmap(96, 96);
        for (int y = 0; y < backdrop.height; y++)
            for (int x = 0; x < backdrop.width; x++)
                backdrop.pixels[x + y * backdrop.width] =
                        (int) ((backdrop.width - y) / 96.0 * 0x0000ff) + 0x997700;
        draw(backdrop, (width - backdrop.width) / 2, (height - backdrop.width + 40) / 2);

        //draw picture
        if (pokemon.front)
            drawFront(pokemon.index, (width - 96) / 2, (height - 96 + 40) / 2);
        else
            drawBack(pokemon.index, (width - 96) / 2, (height - 96 + 40) / 2);

        //draw bars
        drawBars();

        //draw rating
        drawRating();

        //draw scroll bar
        Bitmap scrollbar = new Bitmap(2, 8);
        for (int i = 0; i < scrollbar.pixels.length; i++)
            scrollbar.pixels[i] = 0xdaff66;
        draw(scrollbar, (int) (pokemon.index / 649.0 * (width - 2)), height - 8);

        //draw words
        String name = pokemon.name;
        String index = "" + pokemon.index;
        String hp = "" + pokemon.hp;
        String att = "" + pokemon.att;
        String def = "" + pokemon.def;
        String spA = "" + pokemon.spA;
        String spD = "" + pokemon.spD;
        String speed = "" + pokemon.speed;

        draw(index, (width - index.length() * 8) / 2, height - 8, 0xadebeb);
        draw(name, (width - name.length() * 8) / 2, height - 16, 0xadebe6);

        draw("HP:", 0, 0, 0xadebe6);
        draw("Att:", 0, 8, 0xadebeb);
        draw("Def:", 0, 16, 0xadebe6);
        draw("Sp.A:", 0, 24, 0xadebeb);
        draw("Sp.D:", 0, 32, 0xadebe6);
        draw("Spd:", 0, 40, 0xadebeb);

        draw(hp, width - hp.length() * 8 - width * 3 / 4, 0, 0xadebe6);
        draw(att, width - att.length() * 8 - width * 3 / 4, 8, 0xadebeb);
        draw(def, width - def.length() * 8 - width * 3 / 4, 16, 0xadebe6);
        draw(spA, width - spA.length() * 8 - width * 3 / 4, 24, 0xadebeb);
        draw(spD, width - spD.length() * 8 - width * 3 / 4, 32, 0xadebe6);
        draw(speed, width - speed.length() * 8 - width * 3 / 4, 40, 0xadebeb);
    }
}

