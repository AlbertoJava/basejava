package com.urise.webapp.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class minValue {
    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        for (int i=0;i<10;i++){
            values.add (i);
        }
        System.out.println("Sum of elements =" + values.stream().reduce((x1,x2)->x1+x2).get());
        printStream(oddOrEven(values).stream(),"List without streams");
        printStream(oddOrEvenOptional(values).stream(), "List created using stream line!" );

    }

    private static void printStream(Stream stream, String title) {
        System.out.println("------" + title + "------");
        stream.forEach(System.out::println);
        System.out.println("------------");
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).min().getAsInt();
    }


    static List<Integer> oddOrEven(List<Integer> integers){
        List<Integer> evenList = new ArrayList<>();
        List<Integer> oddList = new ArrayList<>();
        int sum=0;
        for (int i=0;i<integers.size();i++){
            sum=+integers.get(i);
            if (i%2==0){
                oddList.add(integers.get(i));
            }
            else {
                evenList.add(integers.get(i));
            }
        }
        return sum%2==0?evenList:oddList;
    }

    static List<Integer> oddOrEvenOptional(List<Integer> integers){
        //Map<Boolean, List<Integer>> result
        List<Integer> result=integers.stream()
                  .collect(Collectors.partitioningBy((x)-> integers.indexOf(x)%2==0))
                  .get(integers.stream().reduce((x1,x2)->x1+x2).get()%2!=0)
                  ;

          return result;
    }

}
