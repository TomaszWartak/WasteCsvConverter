package pl.dev4lazy.waste.validators;

import pl.dev4lazy.waste.interfaces.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WasteCodeValidator implements Validator<String> {

    boolean valid = false;
    private final String WASTE_CODE_REGEX_PATTERN = "\\d{2} \\d{2} \\d{2}\\*?";
    private String value = "";

    @Override
    public void doValidation(String valueToValidate) {
        // Kompiluj wzorzec Regex
        Pattern pattern = Pattern.compile(WASTE_CODE_REGEX_PATTERN);
        // Tworzy matcher do dopasowania wzorca do łańcucha znaków
        Matcher matcher = pattern.matcher(valueToValidate);
        valid = matcher.matches();
        if (!valid) {
            value = valueToValidate;
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String getNegativeValidationDescription() {
        if (value.isEmpty()) {
            return "";
        }
        return "Kod "+value+" nie jest poprawny";
    }
}
