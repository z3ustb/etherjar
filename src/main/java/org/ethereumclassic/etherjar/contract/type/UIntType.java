package org.ethereumclassic.etherjar.contract.type;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UIntType extends NumericType {

    public final static UIntType DEFAULT = new UIntType();

    final static Map<Integer, UIntType> CACHED_INSTANCES =
            Stream.of(8, 16, 32, 64, 128, 256).collect(Collectors.collectingAndThen(
                    Collectors.toMap(Function.identity(), UIntType::new), Collections::unmodifiableMap));

    final static String NAME_PREFIX = "uint";

    final static Pattern NAME_PATTERN = Pattern.compile("uint(\\d*)");

    /**
     * Try to parse a {@link UIntType} string representation (either canonical form or not).
     *
     * @param str a string
     * @return a {@link UIntType} instance is packed as {@link Optional} value,
     * or {@link Optional#empty()} instead
     * @throws NullPointerException if a {@code str} is <code>null</code>
     * @throws IllegalArgumentException if a {@link UIntType} has invalid input
     *
     * @see #getCanonicalName()
     */
    public static Optional<UIntType> from(String str) {
        if (!str.startsWith(NAME_PREFIX))
            return Optional.empty();

        Matcher matcher = NAME_PATTERN.matcher(str);

        if (!matcher.matches())
            throw new IllegalArgumentException("Wrong 'uint' type format: " + str);

        String digits = matcher.group(1);

        if (digits.isEmpty())
            return Optional.of(DEFAULT);

        int bits = Integer.parseInt(digits);

        return Optional.of(CACHED_INSTANCES.containsKey(bits) ?
                CACHED_INSTANCES.get(bits) : new UIntType(bits));
    }

    private final BigInteger maxValue;

    public UIntType() {
        this(256);
    }

    public UIntType(int bits) {
        super(bits, false);

        maxValue = powerOfTwo(bits);
    }

    @Override
    public BigInteger getMinValue() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger getMaxValue() {
        return maxValue;
    }

    @Override
    public String getCanonicalName() { return "uint" + getBits(); }
}
