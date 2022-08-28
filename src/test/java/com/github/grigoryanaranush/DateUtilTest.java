package com.github.grigoryanaranush;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateUtilTest {
    private static Stream<Arguments> datesToLocalDate() {
        return Stream.of(
            Arguments.of("15/11/2001/12:00:00/T.CENTRO", LocalDate.of(2001, 11, 15)),
            Arguments.of("15/11/2001 12:00:00 T.CENTRO", LocalDate.of(2001, 11, 15)),
            Arguments.of("15/OCTUBRE/2001/12:00:00/T.CENTRO", LocalDate.of(2001, 10, 15)),
            Arguments.of("15/OCT/2001/12:00:00/T.CENTRO", LocalDate.of(2001, 10, 15)),
            Arguments.of("04 OCTUBRE 2001", LocalDate.of(2001, 10, 4)),
            Arguments.of("04 DE AGOSTO DE 2005", LocalDate.of(2005, 8, 4)),
            Arguments.of("04 OCT 2001", LocalDate.of(2001, 10, 4)),
            Arguments.of("04 OCT. 2001", LocalDate.of(2001, 10, 4)),
            Arguments.of("04 MARZO 2001", LocalDate.of(2001, 3, 4)),
            Arguments.of("08/03/1909", LocalDate.of(1909, 3, 8)),
            Arguments.of("12/04/2005|", LocalDate.of(2005, 4, 12)),
            Arguments.of("22/2/2002", LocalDate.of(2002, 2, 22)),
            Arguments.of("6/10/1999", LocalDate.of(1999, 10, 6)),
            Arguments.of("03-09-1949", LocalDate.of(1949, 9, 3)),
            Arguments.of("05/04/1964,", LocalDate.of(1964, 4, 5)),
            Arguments.of("05/11/1947.", LocalDate.of(1947, 11, 5)),
            Arguments.of("08 /08/ 1974.", LocalDate.of(1974, 8, 8)),
            Arguments.of("08 /08/ 1974", LocalDate.of(1974, 8, 8)),
            Arguments.of("08-08 1989", LocalDate.of(1989, 8, 8)),
            Arguments.of("10/12//1969", LocalDate.of(1969, 12, 10)),
            Arguments.of("08/03/1909", LocalDate.of(1909, 3, 8))
        );
    }

    @ParameterizedTest
    @MethodSource("datesToLocalDate")
    void dateParsingToLocalDateShouldBeDoneProperly(String input, LocalDate expected) {
        // when
        var actual = DateUtil.getLocalDate(input);

        // then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected, actual.get());
    }

}