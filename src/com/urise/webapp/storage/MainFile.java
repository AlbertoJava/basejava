package com.urise.webapp.storage;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        /*текущий каталог*/
        File dir = new File(".\\.");
        try {
            findFiles(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
<<<<<<< HEAD
            if (list==null){
                System.out.println("Files not found!");
                return;
            }
            for (int i=list.length; --i>=0;) {
=======
            for (int i = list.length; --i >= 0; ) {
>>>>>>> lesson 8.
                findFiles(list[i]);
            }
        } else {
            System.out.println(file.getName());
        }
    }

}
