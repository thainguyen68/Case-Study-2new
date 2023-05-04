package model;

public class Color {
    private static final long serialVersionUID = 3L;
    public static int idUpColor;
    private int id;
    private String nameColor;

    public Color() {

    }
    public Color(int id, String nameColor) {
        this.id = id;
        this.nameColor = nameColor;
    }

    public Color(String nameColor) {
        this.id = ++idUpColor;
        this.nameColor = nameColor;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @Override
    public String toString() {
        return  id + "," + nameColor;
    }
    public void display() {
        System.out.printf("%-10s%s", this.id, this.nameColor + "\n");
    }
}

