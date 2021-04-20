package com.github.eugene70;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String filePath = "book.txt";
        Map<String, Integer> wordMap = null;
        try {
            File file = getFileSystemResource(filePath); // refactor 1 : 파일 가져오는 코드 method 처리

            wordMap = new HashMap<>();

            wordMap.putAll(getMapDistinctWordCount(file)); //refactor 2 : text file의 단어 갯수를 map으로 가져오는 method 추출
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println(wordMap);

        List<Map.Entry<String, Integer>> sortedlist = getSortedListFromMap(wordMap); //refactor 3 : sort 처리

        System.out.println(sortedlist);
    }

    private static List<Map.Entry<String, Integer>> getSortedListFromMap(Map<String, Integer> wordMap) {
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

    private static Map<String, Integer> getMapDistinctWordCount(File file) throws IOException {
        return getMapDistinctWordCount(new FileInputStream(file));
    }

    private static Map<String, Integer> getMapDistinctWordCount(InputStream inputStream) throws IOException {
        return getMapDistinctWordCount(new InputStreamReader(inputStream));
    }

    private static Map<String, Integer> getMapDistinctWordCount(InputStreamReader reader) throws IOException {
        return getMapDistinctWordCount(new BufferedReader(reader));
    }

    private static Map<String, Integer> getMapDistinctWordCount(BufferedReader reader) throws IOException {
        Map<String, Integer> wordMap = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) { //refactor 2-1 반복 구문 보완

            line = line.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase(Locale.ROOT).trim();
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

    private static File getFileSystemResource(String filePath) throws FileNotFoundException {
        URL url = ClassLoader.getSystemResource(filePath);
        System.out.println("url : " + url);
        File file = null;

        if(url != null) {
            file = new File(url.getFile());
        } else {
            System.out.println("파일을 찾을 수 없습니다 : " + filePath);
            throw new FileNotFoundException();
        }

        return file;
    }
}