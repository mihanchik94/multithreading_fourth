package task_1;

import java.util.concurrent.ArrayBlockingQueue;

public class LinesChecker extends Thread {
    private final ArrayBlockingQueue<String> queue;
    private final char checkingLetter;
    private final int lines;

    public LinesChecker(ArrayBlockingQueue<String> queue, char checkingLetter, int lines) {
        this.queue = queue;
        this.checkingLetter = checkingLetter;
        this.lines = lines;
    }

    public void checkLines(char checkingLetter) {
        String resultLine = "";
        int linesCounter = 0;
        int resultCounter = 0;
        int result = 0;
        while (linesCounter != lines) {
            String line = queue.poll();
            if (line == null) {
                continue;
            }
            for (char letter : line.toCharArray()) {
                if (letter == checkingLetter) {
                    resultCounter++;
                }
                if (resultCounter > result) {
                    result = resultCounter;
                    resultLine = line;
                }
            }
            resultCounter = 0;
            linesCounter++;
        }
        if (result == 0) {
            System.out.printf("Символ %c отсутствует%n", checkingLetter);
        }
        System.out.printf("Самое большое количество символов %c в строке %s и составляет %d шт.%n", checkingLetter, resultLine, result);
    }

    @Override
    public void run() {
        checkLines(checkingLetter);
    }
}