package com.vins_nerf.core.enums.handler;

import com.vins_nerf.core.enums.VinsFilterType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VinsFilterTypeHandler extends BaseTypeHandler<VinsFilterType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, VinsFilterType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getState());
    }

    @Override
    public VinsFilterType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return VinsFilterType.getInstance(rs.getInt(columnName));
    }

    @Override
    public VinsFilterType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return VinsFilterType.getInstance(rs.getInt(columnIndex));
    }

    @Override
    public VinsFilterType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return VinsFilterType.getInstance(cs.getInt(columnIndex));
    }
}
