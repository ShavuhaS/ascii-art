package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.awt.image.BufferedImage;

public class CharacterDarknessComparator implements Comparator<Character> {
    private String fontName;

    public CharacterDarknessComparator(String fontName) {
        this.fontName = fontName;
    }

    @Override
    public int compare(Character c1, Character c2) {
        return getDarkness(c1) > getDarkness(c2) ? 1 : -1;
    }

    private int getDarkness(Character c) {
        int darkness = 0;

        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = img.createGraphics();
        g2d.setFont(new Font(fontName, Font.PLAIN, 60));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 100, 100);
        g2d.setColor(Color.WHITE);
        g2d.drawString(c.toString() , 10, 60);

        for(int y = 0; y < img.getHeight(); y++) {
            for(int x = 0; x < img.getWidth(); x++) {
                darkness += img.getRGB(x, y) &0xff;
            }
        }

        return darkness;
    }

    public static char[] sortChars(String chars, String fontName) {
        ArrayList<Character> charsList = new ArrayList<Character>();
        for(char c : chars.toCharArray()) {
            charsList.add(c);
        }

        Collections.sort(charsList, new CharacterDarknessComparator(fontName));

        char[] result = new char[charsList.size()];
        for(int i = 0; i < charsList.size(); i++) {
            result[i] = charsList.get(i).charValue();
        }

        return result;
    }

    public String getFontName() {
        return fontName;
    }
}
