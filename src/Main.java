import com.dropbox.core.DbxException;
import java.awt.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws DbxException, IOException, AWTException {
        MyThread thread = new MyThread();
        thread.start();
    }
}