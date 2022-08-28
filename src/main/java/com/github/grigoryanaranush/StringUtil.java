package com.github.grigoryanaranush;

import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = PRIVATE)
public class StringUtil {
    public static final Pattern SYMBOLS_TO_BE_CLEANED_FROM_DATE_PATTERN = compile("\\s*[/\\s-.]\\s*");
    private static final Pattern EMPTY_STRING_PATTERN = compile("^(\\\\n+|\\\\r+|\\\\t+|\\s+|\\.+|-+|/\\s*/|\\**)$");

    public static Predicate<String> nonEmptyStringPredicate() {
        return s -> StringUtils.isNotBlank(s)
            && !EMPTY_STRING_PATTERN.matcher(s).find() && !"NC".equals(s) && !"NCD".equals(s)
            && !"NO CONSTA".equals(s) && !"NO COSTA".equals(s) && !"NO CONTA".equals(s) && !"NO CONTIENE".equals(s)
            && !"NO SEÑALA".equals(s) && !"NO TIENE".equals(s) && !".NULL.".equals(s) && !"NO LO TIENE".equals(s);
    }

    public static Optional<Matcher> getPositiveMatcher(Pattern pattern, String s) {
        return Optional.ofNullable(s)
            .map(pattern::matcher)
            .filter(Matcher::find);
    }

    static String replace(Pattern pattern, String target, String replacement) {
        return getPositiveMatcher(pattern, target)
            .map(matcher -> matcher.replaceAll(replacement))
            .orElse(target);
    }
}
