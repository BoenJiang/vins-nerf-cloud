package com.vins_nerf.core.enums.handler;

import com.vins_nerf.core.enums.VinsCameraPosition;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VinsCameraPositionHandler extends BaseTypeHandler<VinsCameraPosition> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, VinsCameraPosition parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getState());
    }

    @Override
    public VinsCameraPosition getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return VinsCameraPosition.getInstance(rs.getInt(columnName));
    }

    @Override
    public VinsCameraPosition getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return VinsCameraPosition.getInstance(rs.getInt(columnIndex));
    }

    @Override
    public VinsCameraPosition getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return VinsCameraPosition.getInstance(cs.getInt(columnIndex));
    }
}
