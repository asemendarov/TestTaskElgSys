package HTML;

import Memento.MementoTextField;
import javafx.scene.web.HTMLEditor;

public class Substitution {
    public static void replaceAll(HTMLEditor htmlEditor, MementoTextField memento){
        final String[] html = {htmlEditor.getHtmlText()};
        memento.getMap().forEach(
                (k, v) -> html[0] = html[0].replaceAll(k, v.getText())
        );
        htmlEditor.setHtmlText(html[0]);
    }
}