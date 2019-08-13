package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MainReflection {

    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";
    private static final Resume R_1 = new Resume(UUID_1,"FIO_1");
    private static final Resume R_2 = new Resume(UUID_2,"FIO_2");
    private static final Resume R_3 = new Resume(UUID_3,"FIO_3");
    private static final Resume R_4 = new Resume(UUID_4,"FIO_4");

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume test = new Resume("test");
        Method method = test.getClass().getMethod("toString");
        System.out.println(method.invoke(test));
        SortedArrayStorage storage = new SortedArrayStorage();
        storage.getStorage()[0]=R_1;
        storage.getStorage()[1]=R_2;
        storage.getStorage()[2]=R_3;

        storage.save(R_4);

        System.out.println(storage.getStorage().length);
        System.out.println(Arrays.binarySearch(storage.getStorage(),0,9, R_2));
    }



}