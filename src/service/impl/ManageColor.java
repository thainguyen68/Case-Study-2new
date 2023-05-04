package service.impl;

import ioFile.IOFile;
import model.Color;
import model.Product;
import service.Manage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageColor implements Manage<Color>, IOFile<Color> {
    private Scanner scanner;
    private List<Color> colorList = new ArrayList<>();
    private final String PATH_FILE = "E:\\Case-Study-2\\src\\data\\Color.tet";


    public ManageColor(Scanner scanner) {
        this.scanner = scanner;
//        addColor();
        colorList = read(PATH_FILE);
        checkDefaultIndex();
    }


    public void addColor(){
        Color color1 = new Color(1,"red");
        Color color2 = new Color(2,"blue");
        Color color3 = new Color(3, "white");
        colorList.add(color1);
        colorList.add(color2);
        colorList.add(color3);
        write(colorList,PATH_FILE);
    }
    private void checkDefaultIndex() {
        if (colorList.isEmpty()) {
            Product.idUp = 0;
        } else {
            Product.idUp = colorList.get(colorList.size() - 1).getId();
        }
    }

    public Color create(){
        System.out.println("Enter the color name you want:");
        String colorName = scanner.nextLine();
        Color colorNew = new Color(colorName);
        colorList.add(colorNew);
        return new Color();
    }

    public Color update(){
        Color color = getById();
        if (color != null){
            System.out.println("Change the color name you want");
            String nameColorChange = scanner.nextLine();
            if (!nameColorChange.equals("")){
                color.setNameColor(nameColorChange);
            }
        }
        return color;
    }

    public Color delete(){
        Color color = getById();
        if (color != null){
            colorList.remove(color);
        }
        return color;
    }

    public Color getById(){
        int id;
        do {
            try {
                System.out.println("Input id you want: ");
                id = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Have error, please try again!");
            }
        } while (true);

        for (Color color : colorList) {
            if (color.getId() == id) {
                return color;
            }
        }
        return null;
    }

    public void displayAll(){
        System.out.printf("%-10s%s","Id","Color\n");
        for (Color c:colorList){
            System.out.println(c);
        }
    }


    public void write(List<Color> colorList, String path) {
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Color color : colorList) {
                bufferedWriter.write(color.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }

    public List<Color> read(String path) {
        File file = new File(path);
        List<Color> colorList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] strings = data.split(",");
                Color color = new Color(Integer.parseInt(strings[0]), strings[1]);
                colorList.add(color);
            }
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        return colorList;
    }
}
