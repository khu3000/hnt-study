package com.github.eugene70;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static java.util.AbstractMap.SimpleEntry;

public class LogAnalysis {

    private static Stream<String> readFile(String fileName) {
        try {
            final URI uri = ClassLoader.getSystemResource(fileName).toURI();
            return Files.lines(Paths.get(uri));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private static String[] splitBySpace(String line) {
        return line.split("\\s+");
    }

    public static void main(String[] args) {

        final String filename = "ptwpta04-portal-access.log.tmp.txt";
        final String TARGET_URL = "/air/ols/maskhandler/doReleaseMask/v1.00";

        Double rtn = readFile(filename)
                .filter(s -> s.indexOf(TARGET_URL) > -1)
                .map(line -> splitBySpace(line))
                .mapToInt(s -> Integer.parseInt(valueOf(s[4])))
                .average()
                .getAsDouble();
        System.out.println(rtn);

    }

    public static <T> T log(T param) {
        System.out.println(Thread.currentThread().getName() + ": " + param.toString());
        return param;
    }
}