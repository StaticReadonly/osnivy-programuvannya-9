import java.util.*;

class Color {
    private int red;
    private int green;
    private int blue;

    public Color(int red, int green, int blue) {

        if ((red < 0 || red > 255) || (green < 0 || green > 255) || (blue < 0 || blue > 255))
            throw new IllegalArgumentException("RGB values must be in range from 0 to 255");

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String toString() {
        return "RGB(" + red + ", " + green + ", " + blue + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Color other = (Color) obj;
        return red == other.red && green == other.green && blue == other.blue;
    }
}


abstract class Pixel {
    protected int x;
    protected int y;
    protected Color color;

    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public String toString() {
        return "Pixel at (" + x + ", " + y + ") with color " + color.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pixel other = (Pixel) obj;
        return x == other.x && y == other.y && color.equals(other.color);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void setColor(Color color);
}


class MutablePixel extends Pixel {
    public MutablePixel(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void setColor(Color color) {
        super.color = color;
    }
}

public class Main {
    public static void main(String[] args) {
        Pixel[] pixels = {
                new MutablePixel(5, 3, new Color(255, 0, 0)),
                new MutablePixel(2, 7, new Color(0, 255, 0)),
                new MutablePixel(8, 1, new Color(0, 0, 255)),
                new MutablePixel(3, 5, new Color(255, 255, 255))
        };

        //sort by x in ascending order
        Arrays.sort(pixels, (Pixel p1, Pixel p2) -> Integer.compare(p1.getX(), p2.getX()));

        System.out.println("Sort by x-coordinates in ascending order:");
        for (Pixel pixel : pixels) {
            System.out.println(pixel);
        }

        //sort by x in reverse order
        TreeSet<Pixel> reverseXSet = new TreeSet<>(Comparator.comparing((Pixel p) -> p.getX()).reversed());
        reverseXSet.addAll(Arrays.asList(pixels));

        System.out.println("\nSort by x coordinates in reverse order:");
        for (Pixel pixel : reverseXSet) {
            System.out.println(pixel);
        }

        
        //sort by x, if equality - by y
        Comparator<Pixel> comparator = Comparator.comparing((Pixel p) -> p.getX()).thenComparing((Pixel p) -> p.getY());
        Arrays.sort(pixels, comparator);

        System.out.println("\nSort by x-coordinates, and in case of equality - by y-coordinates:");
        for (Pixel pixel : pixels) {
            System.out.println(pixel);
        }

        //sort by x with null-references allowed
        Pixel[] pixelsWithNull = {
                null,
                new MutablePixel(5, 3, new Color(255, 0, 0)),
                null,
                new MutablePixel(2, 7, new Color(0, 255, 0)),
                new MutablePixel(8, 1, new Color(0, 0, 255)),
                null,
                new MutablePixel(3, 5, new Color(255, 255, 255)),
                null
        };
        Arrays.sort(pixelsWithNull, Comparator.nullsFirst((p1, p2) -> {
            if (p1 == null && p2 == null) {
                return 0;
            } else if (p1 == null) {
                return -1;
            } else if (p2 == null) {
                return 1;
            }
            return Integer.compare(p1.getX(), p2.getX());
        }));


        System.out.println("\nSort by x coordinates with null references allowed:");
        for (Pixel pixel : pixelsWithNull) {
            System.out.println(pixel);
        }
    }
}
