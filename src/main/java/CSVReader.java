import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class CSVReader {
    private String url;

    public CSVReader(String url) {
        this.url = url;
    }

    public PrefixTree<Long> createPrefixTreeOnColumn(int n) throws IOException {
        RandomAccessFile file = new RandomAccessFile(url, "r");
        PrefixTree<Long> prefixTree = new PrefixTree<>();
        int i = 0;

        while (file.length() > file.getFilePointer()) {
            long position = file.getFilePointer();
            String s = file.readLine();
            String[] arr = s.split(",");

            if (arr.length >= n) {
                prefixTree.addString(arr[n], position);
            } else {
                throw new IOException("Column " + n + " can not be read from line " + i);
            }
            i++;
        }
        file.close();
        return prefixTree;
    }

    public String readline(long filePointer) throws IOException {
        RandomAccessFile file = new RandomAccessFile(url, "r");
        file.seek(filePointer);
        String result = file.readLine();
        file.close();
        return result;
    }
}
