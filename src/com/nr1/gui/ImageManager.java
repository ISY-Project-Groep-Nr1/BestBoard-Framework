package com.nr1.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class ImageManager {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    public static BufferedImage getImage(String imageName) {
        return images.get(imageName);
    }

    private static void load(String name, File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            images.put(name, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadAll(String location) {
        File dir = new File(location);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                load(child.getName().split("\\.")[0], child);
            }
        } else {
            throw new IllegalStateException(location + " is not a directory!");
        }
    }

    static {
        loadAll("recourses/images");
    }
}
