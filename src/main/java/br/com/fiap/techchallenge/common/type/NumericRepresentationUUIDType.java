package br.com.fiap.techchallenge.common.type;

import static com.google.common.base.Strings.padStart;

import io.hypersistence.utils.hibernate.type.ImmutableType;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.spi.TypeConfiguration;
import org.hibernate.usertype.EnhancedUserType;

@SuppressWarnings("checkstyle:abbreviationaswordinname") // UUID is a known name
public class NumericRepresentationUUIDType extends ImmutableType<UUID> implements EnhancedUserType<UUID> {
    private static final long serialVersionUID = 1L;

    private static final int LEAST_SIGNIFICANT_DIGITS_BEGIN = 16;
    private static final int LEAST_SIGNIFICANT_DIGITS_END = 32;
    private static final int UUID_REPESENTATION_SIZE = 32;
    private static final int HEX_RADIX = 16;
    private static final int DECIMAL_RADIX = 10;
    private static final int NUMERIC_REPRESENTATION_SIZE = 40;

    public NumericRepresentationUUIDType() {
        super(UUID.class);
    }

    @Override
    protected UUID get(ResultSet rs, int index, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        String value = rs.getString(index);
        if (value == null) {
            return null;
        } else {
            UUID result = fromDecimal(value);
            return result;
        }
    }

    @Override
    protected void set(PreparedStatement ps, UUID uuid, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        String value = toDecimal(uuid);
        if (value == null) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, value);
        }
    }

    @Override
    public int getSqlType() { return Types.VARCHAR; }

    public static String toDecimal(UUID id) {
        if (id == null) {
            return null;
        } else {
            String value = id.toString().replaceAll("-", "");
            BigInteger integer = new BigInteger(value, HEX_RADIX);
            return padStart(integer.toString(DECIMAL_RADIX), NUMERIC_REPRESENTATION_SIZE, '0');
        }
    }

    public static UUID fromDecimal(String id) {
        if (id == null) {
            return null;
        } else {
            BigInteger value = new BigInteger(id, DECIMAL_RADIX);
            String hex = padStart(value.toString(HEX_RADIX), UUID_REPESENTATION_SIZE, '0');
            long mostSigBits = new BigInteger(hex.substring(0, LEAST_SIGNIFICANT_DIGITS_BEGIN), HEX_RADIX).longValue();
            long leastSigBits = new BigInteger(hex.substring(LEAST_SIGNIFICANT_DIGITS_BEGIN, LEAST_SIGNIFICANT_DIGITS_END), HEX_RADIX).longValue();
            UUID uuid = new UUID(mostSigBits, leastSigBits);
            return uuid;
        }
    }

    @Override
    public JdbcType getJdbcType(TypeConfiguration typeConfiguration) {
        return super.getJdbcType(typeConfiguration);
    }

    @Override
    public String toSqlLiteral(UUID value) {
        return toDecimal(value);
    }

    @Override
    public String toString(UUID value) throws HibernateException {
        return toDecimal(value);
    }

    @Override
    public UUID fromStringValue(CharSequence sequence) throws HibernateException {
        return fromDecimal(String.valueOf(sequence));
    }
}
