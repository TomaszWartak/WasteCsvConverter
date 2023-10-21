package pl.dev4lazy.waste.interfaces;

public interface Validator<V> {

    // W klasach mplementujących musi być pole boolean valid ustawiane doValidation()
    // W pierwszej kolejności trzeba wywołać doValidation, a później inne metody
    void doValidation( V value );

    // Musi zwracać wartość valid
    // W pierwszej kolejności trzeba wywołać doValidation, a później inne metody
    boolean isValid();

    // W pierwszej kolejności trzeba wywołać doValidation, a później inne metody
    default boolean isNotValid() {
        return !isValid();
    }

    // Zwraca tekst komunikatu informującego o braku walidacji
    // W pierwszej kolejności trzeba wywołać doValidation, a później inne metody
    String getNegativeValidationDescription();

}
