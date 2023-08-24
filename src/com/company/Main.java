package com.company;

import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CharacterDarknessComparator comparator = new CharacterDarknessComparator("SansSerif");
        String chars = " .,/:;^%*?~`()2x0";
        char[] sorted = comparator.sortChars(chars, comparator.getFontName());

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь файла:");

        File inputFile = new File(sc.nextLine());
        BufferedImage source;
        try {
            source = ImageIO.read(inputFile);
        } catch (IOException e) {
            e.getMessage();
            return;
        }

        try {
            File output = new File("art" + new Date().getTime() + ".txt");
            FileWriter fw = new FileWriter(output, false);
            int compression = 16;
            for(int y = 0; y < source.getHeight() - compression*2 + 1; y+=compression*2) {
                for(int x = 0; x < source.getWidth() - compression + 1; x+=compression) {
                    int sum = 0;
                    for(int i = 0; i < compression*2; i++) {
                        for (int j = 0; j < compression; j++) {
                            Color color = new Color(source.getRGB(x+j, y+i));
                            int grey = (int) (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114);
                            sum += grey;
                        }
                    }
                    int newGrey = sum / (compression * compression * 2);
                    float k = (float) newGrey / 255;
                    int index = sorted.length - ((int) (k * (sorted.length - 1))) - 1;
                    fw.append(sorted[sorted.length - 1 - index]);
                }
                fw.append('\n');
            }
            System.out.println("Готово! Ваш арт находится в папке " + output.getAbsolutePath());
            fw.flush();
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
