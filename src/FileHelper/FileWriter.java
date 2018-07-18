package FileHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter implements FileCommand {

    private final File file;

    public FileWriter(File file) throws IOException{
        this.file = file;
        exists();
    }

    public FileWriter(String fileName) throws IOException{
        this.file = new File(System.getProperty("user.dir")
                + File.separator + fileName);
        exists();
    }

    @Override
    public String execute(String outText) throws IOException { // ignore result (null)
        try (BufferedWriter out = new BufferedWriter(new java.io.FileWriter(file))){
            out.write(outText);
        } catch(IOException e) {
            // pass
            throw new IOException(e);
        }

        return null;
    }

    private void exists() throws IOException {
        if(!file.exists())
            file.createNewFile();
    }
}
