package es.boostreator.domain.model.enums;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Type {

    GOTEO(new String[]{"GOTEO"}),
    AUTOMATICA(new String[]{"AUTOMATICA"}),
    MANUAL(new String[]{"MANUAL"}),
    ESPRESSO(new String[]{"ESPRESSO", "EXPRESO"}),
    ITALIANA(new String[]{"ITALIANA"}),
    CAPSULAS(new String[]{"CAPSULAS"}),
    DESCALCIFICADOR(new String[]{"DESCALCIFICADOR"}),
    MONODOSIS(new String[]{"MONODOSIS"}),
    MOLINILLO(new String[]{"MOLINILLO"});


    private List<String> patterns;

    Type() {
        this(new String[]{});
    }

    Type(String[] patterns) {
        this.patterns = Arrays.asList(patterns);
        //this.patterns.add(this.name().toString());
    }

    public static List<Type> getTypeListByProductTitle(String text) {
        List<Type> typeList = new ArrayList<>();
        Type[] types = Type.values();
        text = Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .trim()
                .toUpperCase()
                .replace("'", "")
                .replace(" ", "")
                .replaceAll("[^\\p{ASCII}]", "");

        try {
            for (Type type : types)
                for (String pattern : type.patterns) {
                    if (text.contains(pattern.toUpperCase())) {
                        typeList.add(type);
                        break;
                    }
                }
        } catch (IllegalArgumentException e) {
            System.out.println("Chacho que ha fallaoo");
        }

        return typeList;
    }

}
