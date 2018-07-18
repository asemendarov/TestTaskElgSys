package Tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static final Properties properties = new Properties();

    static {
        init();
    }

    private static void init(){
        try {
            properties.load(new FileInputStream(new File("mail.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
