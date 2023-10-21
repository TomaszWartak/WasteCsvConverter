package pl.dev4lazy.waste.validators;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class WasteCodeValidatorTest {

    private WasteCodeValidator wasteCodeValidator;

    @BeforeEach
    void prepare() {
        wasteCodeValidator = new WasteCodeValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"03 01 05", "17 09 04", "17 01 07", "17 01 07*"})
    void isValid__Should_get_true__For_proper_waste_codes(String wasteCode) {
        wasteCodeValidator.doValidation( wasteCode );
        Assertions.assertThat( wasteCodeValidator.isValid( ) ).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "17", "17 01", "17 01 ", "17 01 0", "17 01 07 ", "*17 01 07"})
    void isValid__Should_get_false__For_improper_waste_codes(String wasteCode) {
        wasteCodeValidator.doValidation( wasteCode );
        Assertions.assertThat( wasteCodeValidator.isValid( ) ).isFalse();
    }
}