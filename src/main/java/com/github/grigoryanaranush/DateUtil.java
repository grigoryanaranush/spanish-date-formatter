package com.github.grigoryanaranush;

import static com.github.grigoryanaranush.StringUtil.SYMBOLS_TO_BE_CLEANED_FROM_DATE_PATTERN;
import static com.github.grigoryanaranush.StringUtil.nonEmptyStringPredicate;
import static com.github.grigoryanaranush.StringUtil.replace;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = PRIVATE)
@Log
public class DateUtil {
    private static final Pattern LAST_NON_DIGIT_SYMBOL = compile("\\W$");
    private static final Pattern MORE_THAN_TWO_SLASH_PATTERN = compile("/{2,}");

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
        .append(ofPattern("[dd][d]/"))
        .optionalStart().appendLiteral("de/").optionalEnd()
        .append(ofPattern("[MM][M][MMMM][MMM]/"))
        .optionalStart().appendLiteral("de/").optionalEnd()
        .append(ofPattern("yyyy"))
        .appendOptional(ofPattern("/HH:mm:ss"))
        .optionalStart().appendLiteral("/t/centro").optionalEnd()
        .appendOptional(ofPattern("yyyy/[MM][M][MMMM][MMM]/[dd][d]"))
        .toFormatter(Locale.forLanguageTag("es-ES"));

    public static Optional<LocalDate> getLocalDate(String date) {
        return Optional.ofNullable(date)
            .map(StringUtils::normalizeSpace)
            .filter(nonEmptyStringPredicate())
            .map(s -> replace(LAST_NON_DIGIT_SYMBOL, s, ""))
            .map(s -> replace(SYMBOLS_TO_BE_CLEANED_FROM_DATE_PATTERN, s, "/"))
            .map(s -> replace(MORE_THAN_TWO_SLASH_PATTERN, s, "/"))
            .map(String::toLowerCase)
            .flatMap(s -> Try.of(() -> LocalDate.parse(s, FORMATTER))
                .onFailure(throwable -> log.info(throwable.getMessage()))
                .toJavaOptional())
            .filter(localDate -> localDate.isBefore(LocalDate.now()));
    }
}
