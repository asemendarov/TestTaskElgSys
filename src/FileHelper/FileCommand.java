package FileHelper;

import java.io.IOException;

public interface FileCommand {
    String execute(String outText) throws IOException;
}
