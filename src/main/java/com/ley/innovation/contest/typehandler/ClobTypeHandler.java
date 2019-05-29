package com.ley.innovation.contest.typehandler;

//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//import org.springframework.util.FileCopyUtils;
//
//import java.io.*;
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * clob type handler
// **/
//@MappedTypes({String.class})
//@MappedJdbcTypes({JdbcType.CLOB})
//public class ClobTypeHandler extends BaseTypeHandler<String> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
//            throws SQLException {
//        final StringReader sr = new StringReader(parameter);
//        final int length = parameter.getBytes().length;
//        ps.setCharacterStream(i, sr, length);
//        sr.close();
//
//    }
//
//    @Override
//    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        final OutputStream outPutStream = new ByteArrayOutputStream();
//        final Writer writer = new OutputStreamWriter(outPutStream);
//        try {
//            Reader reader = rs.getCharacterStream(columnName);
//            if (null == reader) {
//                return null;
//            } else {
//                FileCopyUtils.copy(reader, writer);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return outPutStream.toString();
//    }
//
//    @Override
//    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        final OutputStream outPutStream = new ByteArrayOutputStream();
//        final Writer writer = new OutputStreamWriter(outPutStream);
//        try {
//            Reader reader = rs.getCharacterStream(columnIndex);
//            if (null == reader) {
//                return null;
//            } else {
//                FileCopyUtils.copy(reader, writer);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return outPutStream.toString();
//    }
//
//    @Override
//    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        final OutputStream outPutStream = new ByteArrayOutputStream();
//        final Writer writer = new OutputStreamWriter(outPutStream);
//        try {
//            Reader reader = cs.getCharacterStream(columnIndex);
//            if (null == reader) {
//                return null;
//            } else {
//                FileCopyUtils.copy(reader, writer);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return outPutStream.toString();
//    }
//
//}

