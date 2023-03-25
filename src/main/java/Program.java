import java.io.IOException;
import java.security.spec.ECField;
import java.util.*;
import java.util.concurrent.Executor;

public class Program {

    public static final String dataUrl = "D:\\Job\\Тестовые задания\\Renue\\airports.dat";

    private boolean isValidArgs(String[] args) {
        if (args.length != 1) {
            return false;
        }

        try {
            int columnNum = Integer.parseInt(args[0]);
            if (columnNum < 1) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void run(String[] args) {
        if (!isValidArgs(args)) {
            System.out.println("Ошибка: Принимается только один положительный числовой аргумент");
            return;
        }

        try {
            int columnNum = Integer.parseInt(args[0]) - 1;
            CSVReader reader = new CSVReader(dataUrl);
            Scanner scanner = new Scanner(System.in);

            PrefixTree<Long> prefixTree = reader.createPrefixTreeOnColumn(columnNum);

            while (true) {
                System.out.println("Введите строку:");
                String request = "";
                try {
                    request = scanner.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println("Завершение программы");
                    return;
                }

                if (!isNumber(request)) {
                    request = "\"" + request;
                }

                Date before = new Date();
                SortedMap<String, Long> results = prefixTree.getInfoByPrefix(request);
                Date after = new Date();

                if (results.isEmpty()) {
                    System.out.println("Совпадений не найдено");
                    continue;
                }

                for (Map.Entry<String, Long> entry : results.entrySet()) {
                    String string = entry.getKey();
                    Long pointer = entry.getValue();
                    System.out.println(string + "[" + reader.readline(pointer) + "]");
                }
                System.out.println("Количество найденных строк: " + results.size() +
                        ", затраченное на поиск время: " + (after.getTime() - before.getTime()) + "мс");
            }
        } catch (IOException e) {
            System.out.println("Ошибка: проблема с файлом данных");
        }
    }
}
