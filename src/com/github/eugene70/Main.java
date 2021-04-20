package com.github.eugene70;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String filePath = "book.txt";
        File file = getFile(filePath); // refactor 1 : 파일 가져오는 코드 method 처리

        if(!file.exists()) { // refactor 1-1 : 파일 유효성 체크
            System.out.println("파일을 찾을 수 없습니다.");
            return;
        }

        Map<String, Integer> wordMap = new HashMap<>();

        try (
            InputStream fileInputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))
        ) {
            wordMap.putAll(getWordCount(reader));
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(wordMap);

        List<Map.Entry<String, Integer>> list = getSortedList(wordMap); //refactor 3 : sort 처리

        System.out.println(list);
    }

    private static List<Map.Entry<String, Integer>> getSortedList(Map<String, Integer> wordMap) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordMap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                int v1 = (int)o1.getValue();
                int v2 = (int)o2.getValue();
                return Integer.compare(v2, v1);
            }
        });
        return list;
    }

    private static Map<String, Integer> getWordCount(BufferedReader reader) throws IOException {
        Map<String, Integer> wordMap = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) { //refactor 2-1 반복 구문 보완

            line = getLineStr(line);
            if (line.length() > 0) {

                String[] words = line.split("\\s+");
                for (String word : words) {
                    int countWord = wordMap.getOrDefault(word, 0);
                    wordMap.put(word, countWord + 1);
                }
            }
        }

        return wordMap;
    }

    private static String getLineStr(String line) {
        line = line.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase(Locale.ROOT).trim();
        return line;
    }

    private static File getFile(String filePath) {
        URL url = ClassLoader.getSystemResource(filePath);
        System.out.println("resource : " + url);
        File file = null;

        if(url != null) {
            file = new File(url.getFile());
        }
        return file;
    }
}