package task_1;

import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    private static final int QUEUE_SIZE = 100;
    private static final int TEXTS_COUNT = 10_000;
    private static final int TEXT_LENGTH = 100_000;
    private static final String LETTERS = "abc";
    private static final int LINE_LENGTH = 50;
    private static final int LINES = (TEXTS_COUNT * TEXT_LENGTH) / LINE_LENGTH;

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> forA = new ArrayBlockingQueue<>(QUEUE_SIZE);
        ArrayBlockingQueue<String> forB = new ArrayBlockingQueue<>(QUEUE_SIZE);
        ArrayBlockingQueue<String> forC = new ArrayBlockingQueue<>(QUEUE_SIZE);
        TextGenerator generator = new TextGenerator(TEXTS_COUNT, TEXT_LENGTH, LINE_LENGTH, LETTERS, forA, forB, forC);
        LinesChecker checkerA = new LinesChecker(forA, 'a', LINES);
        LinesChecker checkerB = new LinesChecker(forB, 'b', LINES);
        LinesChecker checkerC = new LinesChecker(forC, 'c', LINES);

        generator.start();
        System.out.println(forA);
        checkerA.start();
        checkerB.start();
        checkerC.start();
        checkerA.join();
        checkerB.join();
        checkerC.join();

    }
}