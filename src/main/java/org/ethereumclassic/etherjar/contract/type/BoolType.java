package org.ethereumclassic.etherjar.contract.type;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

public class BoolType extends NumericType {

    public final static BigInteger FALSE = BigInteger.ZERO;

    public final static BigInteger TRUE = BigInteger.ONE;

    public final static BoolType DEFAULT = new BoolType();

    /**
     * Try to parse a {@link BoolType} string representation (either canonical form or not).
     *
     * @param str a string
     * @return a {@link BoolType} instance is packed as {@link Optional} value,
     * or {@link Optional#empty()} instead
     * @throws NullPointerException if a {@code str} is <code>null</code>
     *
     * @see #getCanonicalName()
     */
    public static Optional<BoolType> from(String str) {
        Objects.requireNonNull(str);

        if (!Objects.equals(str, "bool"))
            return Optional.empty();

        return Optional.of(DEFAULT);
    }

    public BoolType() {
        super(8, false);
    }

    @Override
    public BigInteger getMinValue() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger getMaxValue() {
        return BigInteger.valueOf(2);
    }

    @Override
    public String getCanonicalName() {
        return "bool";
    }
}
