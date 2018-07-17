package Parser;

import javax.swing.text.html.HTML;
import java.util.*;

public class HTMLParser implements Parsing {
    private final Set<Character> alphabet = new HashSet<>();

    public HTMLParser() {
        alphabet.addAll(Arrays.asList(

                'а', 'б', 'в', 'г', 'д', 'е', 'ё',
                'ж', 'з', 'и', 'й', 'к', 'л', 'м',
                'н', 'о', 'п', 'р', 'с', 'т', 'у',
                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ',
                'ы', 'ь', 'э', 'ю', 'я',

                'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z'

        ));
    }

    @Override
    public Set<String> parsing(String string) {

        Set<String> set = new HashSet<>();

        StringTokenizer tokenizer = new StringTokenizer(string, " <>"); // [' ', '<', '>']

        while (tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            if ((s = pullOut(s)) != null)
                set.add(s);
        }

        return set;
    }

    private String pullOut(String s) {
        int index = s.indexOf('#');

        if (index == -1)
            return null;

        StringBuilder result = new StringBuilder("#");

        for (index++; index < s.length(); index++) {
            if (!alphabet.contains(s.charAt(index)))
                break;

            result.append(s.charAt(index));
        }

        if (result.length() < 2)
            return null;

        return result.toString();
    }
}
