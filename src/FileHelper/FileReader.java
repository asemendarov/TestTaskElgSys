package FileHelper;

import java.io.*;

public class FileReader implements FileCommand {

    private final File file;
    private String text;

    public FileReader(File file) throws IOException {
        this.file = file;
        exists();
    }

    public FileReader(String fileName) throws IOException {
        this.file = new File(System.getProperty("user.dir")
                + File.separator + fileName);
        exists();
    }

    @Override
    public String execute(String text) throws IOException { // ignore text
        try (BufferedReader in = new BufferedReader(new java.io.FileReader(file))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }

            this.text = stringBuilder.toString();
        } catch(IOException e) {
            // pass
            throw new IOException(e);
        }

        return this.text;
    }

    private void exists() throws FileNotFoundException {
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }

    public String getText() {
        return text;
    }
}
