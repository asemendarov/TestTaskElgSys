package FileHelper;

import java.io.File;
import java.io.IOException;

public class FileHelper {

    private FileCommand command;
    private final File file;

    public FileHelper(File file) {
        this.file = file;
    }

    public FileHelper(String fileName) {
        this.file = new File(System.getProperty("user.dir")
                + File.separator + fileName);
    }

    public void write(String text) throws IOException {
        command = new FileWriter(file);
        command.execute(text);
    }

    public String read() throws IOException {
        command = new FileReader(file);
        return command.execute(null);
    }

    public String update(String text) throws IOException {
        command = new FileUpdate(file);
        return command.execute(text);
    }
}
