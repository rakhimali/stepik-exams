package com.company;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*public class Main {

    public static void main(String[] args) {
        *//*Scanner s = new Scanner(System.in);
        int k = Integer.parseInt(s.nextLine());
        String[] loo = s.nextLine().split(" ");
        int[] log = Arrays.stream(loo)
                          .mapToInt(Integer::parseInt)
                          .toArray();
        String find = String.valueOf(Arrays.stream(log)
                                           .max()
                                           .getAsInt());
        System.out.println( java.util.Arrays.asList(loo).indexOf(find));*//*

       *//* System.out.println(Arrays.stream(new Scanner(System.in)
                  .nextLine()
                  .split(" "))
                  .map(Level::parse)
                  .mapToInt(Level::intValue)
                  .max()
                  .toString());*//*

    }

}*/
class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Long size = Long.parseLong(in.nextLine());
            List<Ball> list = new ArrayList<>();
            for (int k = 0; k < size; k++) {
                String[] dots = in.nextLine().split(" ");
                Ball s = new Ball(dots[0],
                                  dots[1],
                                  Long.parseLong(dots[2]),
                                  Long.parseLong(dots[3]),
                                  Long.parseLong(dots[4]),
                                  Long.parseLong(dots[5]));
                list.add(s);
            }

            List<Ball> copy = list.stream().collect(Collectors.toList());
            List<School> schools = new ArrayList<>();
            list.stream()
                    .filter(Do.distinctByKey(l -> l.getSchoolNumber()))
                    .forEach(l -> {
                        School s = new School();
                        List<Ball> ave = copy.stream()
                                .filter(c -> l.getSchoolNumber().equals(c.getSchoolNumber()))
                                .collect(Collectors.toList());
                        s.setSchoolNumber(l.getSchoolNumber());
                        s.setMathAve(ave.stream()
                                .collect(Collectors.averagingLong(Ball::getMath)));
                        s.setRusAve(ave.stream()
                                .collect(Collectors.averagingLong(Ball::getRus)));
                        s.setInfoAve(ave.stream()
                                .collect(Collectors.averagingLong(Ball::getInfo)));
                        s.setAllAve((s.getMathAve() + s.getRusAve() + s.getInfoAve()) / 3);
                        schools.add(s);
                    });

            String output = String.format(Locale.ENGLISH,
                                 "Отчет по городу: математика - %.1f, " +
                                                          "русский язык - %.1f, " +
                                                          "инфрматика - %.1f, " +
                                                          "общий средний балл - %.1f",
                    list.stream()
                            .collect(Collectors.averagingLong(Ball::getMath)),
                    list.stream()
                            .collect(Collectors.averagingLong(Ball::getRus)),
                    list.stream()
                            .collect(Collectors.averagingLong(Ball::getInfo)),
                    Do.getAll(list));
            System.out.println(output + "\n" + "Отчет по школам:");
            schools.stream()
                   .sorted(Comparator.comparing(School::getAllAve).reversed()
                                     .thenComparing(School::getMathAve)
                                     .thenComparing(School::getRusAve)
                                     .thenComparing(School::getInfoAve)
                                     .thenComparing(School::getSchoolNumber, Comparator.reverseOrder()))
                   .forEach(s -> {
                       String sch = String.format(Locale.ENGLISH,
                               "Школа № " + s.getSchoolNumber() + ": математика - %.1f, " +
                                        "русский язык - %.1f, " +
                                        "инфрматика - %.1f, " +
                                        "общий средний балл - %.1f" ,
                                s.getMathAve(),
                                s.getRusAve(),
                                s.getInfoAve(),
                                s.getAllAve());
                       System.out.println(sch);
                   });

            List<String> subject = Arrays.asList("math", "rus", "info");
            subject.forEach(k -> {
                Do.getMax(list, k);
            });
    }
}

class Do {

    public static void getMax(List<Ball> m,
                              String param) {
        switch (param) {
            case "math" :
                final Long mathMax = m.stream()
                       .mapToLong(v -> v.getMath())
                       .max()
                       .getAsLong();
                m.stream()
                        .filter(l -> l.getMath().equals(mathMax))
                        .sorted(Comparator.comparing(Ball::getFamilia)
                                .thenComparing(Ball::getName))
                        .forEach(l -> {
                            String f = String.format("Лучший результат по математике - %s %s - %s",
                                    l.getFamilia(),
                                    l.getName(),
                                    l.getMath());
                            System.out.println(f);
                        });
                break;
            case "rus" :
                final Long rusMax = m.stream()
                       .mapToLong(v -> v.getRus())
                       .max()
                       .getAsLong();
                m.stream()
                        .filter(l -> l.getRus().equals(rusMax))
                        .sorted(Comparator.comparing(Ball::getFamilia)
                                .thenComparing(Ball::getName))
                        .forEach(l -> {
                            String f = String.format("Лучший результат по русскому языку - %s %s - %s",
                                    l.getFamilia(),
                                    l.getName(),
                                    l.getRus());
                            System.out.println(f);
                        });
                break;
            case "info" :
                final Long infoMax = m.stream()
                       .mapToLong(v -> v.getInfo())
                       .max()
                       .getAsLong();
                m.stream()
                        .filter(l -> l.getInfo().equals(infoMax))
                        .sorted(Comparator.comparing(Ball::getFamilia)
                                .thenComparing(Ball::getName))
                        .forEach(l -> {
                            String f = String.format("Лучший результат по информатике - %s %s - %s",
                                    l.getFamilia(),
                                    l.getName(),
                                    l.getInfo());
                            System.out.println(f);
                        });
                break;
            default:
                break;
        }
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T,
                                                 Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static Double getAll(List<Ball> list) {
        Double all = list.stream().collect(Collectors.averagingLong(Ball::getMath)) +
                list.stream().collect(Collectors.averagingLong(Ball::getRus)) +
                list.stream().collect(Collectors.averagingLong(Ball::getInfo));
        return (double) all / 3;
    }

}


class Ball {
    String familia, name;
    Long schoolNumber, math, rus, info;

    public Ball(String familia,
                String name,
                Long schoolNumber,
                Long math,
                Long rus,
                Long info) {
        this.familia = familia;
        this.name = name;
        this.schoolNumber = schoolNumber;
        this.math = math;
        this.rus = rus;
        this.info = info;
    }

    public String getFamilia() {
        return familia;
    }

    public String getName() {
        return name;
    }

    public Long getSchoolNumber() {
        return schoolNumber;
    }

    public Long getMath() {
        return math;
    }

    public Long getRus() {
        return rus;
    }

    public Long getInfo() {
        return info;
    }
}

class School {
    Long schoolNumber;
    Double mathAve, rusAve, infoAve, allAve;

    public School() {
    }

    public Long getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(Long schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Double getMathAve() {
        return mathAve;
    }

    public void setMathAve(Double mathAve) {
        this.mathAve = mathAve;
    }

    public Double getRusAve() {
        return rusAve;
    }

    public void setRusAve(Double rusAve) {
        this.rusAve = rusAve;
    }

    public Double getInfoAve() {
        return infoAve;
    }

    public void setInfoAve(Double infoAve) {
        this.infoAve = infoAve;
    }

    public Double getAllAve() {
        return allAve;
    }

    public void setAllAve(Double allAve) {
        this.allAve = allAve;
    }
}


/*class ChristmasTree {

    private String color;

    public ChristmasTree(String color) {
        this.color = color;
    }

    public void putTreeTopper(String color) {
        ChristmasTree christmasTree = new ChristmasTree(this.color);
        ChristmasTree.TreeTopper treeTopper = christmasTree.new TreeTopper(color);
        treeTopper.sparkle();
    }

    class TreeTopper {

        private String color;

        public TreeTopper(String color) {
            this.color = color;
        }

        public void sparkle() {
            *//*System.out.println("Sparkling " + this.color + " tree topper looks stunning with " + ChristmasTree.this.color + " Christmas tree!");*//*
            *//*String date = "Н-1159. 23.11.2006";
            date = date.replace("ж", "").trim();
            if(date.length() > 10) {
                date = date.substring(date.length() - 10);
            }
            System.out.println(date);*//*

            StringBuilder idx = new StringBuilder("KZ21PRE");
            String appId = "555";
            for (int i = 0; i < (8 - appId.length()); i++) {
                idx.append("0");
            }
            String finished = idx + appId;
            System.out.println(finished);
            appId = null;
            System.out.println(appId);
            System.out.println(Long.parseLong("00000555"));
        }
    }
}*/

// this code should work
/*class CreateHoliday {

    public static void main(String[] args) {

        ChristmasTree christmasTree = new ChristmasTree("green");
        christmasTree.putTreeTopper("silver");
    }
}*/

