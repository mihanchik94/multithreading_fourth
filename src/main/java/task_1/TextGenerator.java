package task_1;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TextGenerator extends Thread {
    private final int texts;
    private final int length;
    private final int lineLength;
    private final String letters;
    private final ArrayBlockingQueue<String> first;
    private final ArrayBlockingQueue<String> second;
    private final ArrayBlockingQueue<String> third;

    public TextGenerator(int texts, int length, int lineLength, String letters, ArrayBlockingQueue<String> first, ArrayBlockingQueue<String> second, ArrayBlockingQueue<String> third) {
        this.texts = texts;
        this.length = length;
        this.lineLength = lineLength;
        this.letters = letters;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private void splitText(String text, ArrayBlockingQueue<String> result) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        Map<Integer, String> map = text.chars().mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.groupingBy(c -> atomicInteger.getAndIncrement() / lineLength, Collectors.joining()));
        for (String value : map.values()) {
            try {
                result.put(value);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < texts; i++) {
            builder.append(generateText(letters, length));
        }
        System.out.println(builder);
        try {
            splitText(builder.toString(), first);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            splitText(builder.toString(), second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            splitText(builder.toString(), third);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}