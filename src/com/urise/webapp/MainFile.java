package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    static StringBuilder sb=new StringBuilder();
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
            System.out.println(sb.toString() +  "Directory: " + file.getName());
            File[] list = file.listFiles();
            if (list == null) {
                System.out.println("Directory is empty!");
                return;
            }
            sb.append(" ");
            for (int i = list.length; --i >= 0; ) {
                findFiles(list[i]);
            }
            sb.delete(sb.length()-1,sb.length());

        } else {
            System.out.println(sb.toString() + file.getName());
        }
    }

}
