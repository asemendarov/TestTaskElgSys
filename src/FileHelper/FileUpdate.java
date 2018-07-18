package FileHelper;

import java.io.File;
import java.io.IOException;

public class FileUpdate implements FileCommand {
    private final File file;

    public FileUpdate(File file) throws IOException {
        this.file = file;
        exists();
    }

    public FileUpdate(String fileName) throws IOException {
        this.file = new File(System.getProperty("user.dir")
                + File.separator + fileName);
        exists();
    }

    @Override
    public String execute(String outText) throws IOException {
        String oldFile = new FileReader(file).execute(null);

        StringBuilder sb = new StringBuilder();
        sb.append(oldFile);
        sb.append(outText);

        new FileWriter(file).execute(sb.toString());

        return sb.toString();
    }

    private void exists() throws IOException {
        if(!file.exists())
            file.createNewFile();
    }
}
