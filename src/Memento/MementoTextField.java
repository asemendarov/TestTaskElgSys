package Memento;

import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class MementoTextField {
    private final Map<String, TextField> map = new HashMap<>();

    public TextField put(String s, TextField textField){
        return map.put(s, textField);
    }

    public Map<String, TextField> getMap(){
        return map;
    }

    public boolean containsKey(String s){
        return map.containsKey(s);
    }

    public void clear(){
        map.clear();
    }
}
