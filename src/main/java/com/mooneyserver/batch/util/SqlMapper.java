package com.mooneyserver.batch.util;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class SqlMapper implements ItemSqlParameterSourceProvider<String> {
  
  @Override
  public SqlParameterSource createSqlParameterSource(String item) {
    return new SqlParameterSource() {
      @Override
      public int getSqlType(String paramName) {
        return TYPE_UNKNOWN;
      }
      
      @Override
      public String getTypeName(String paramName) {
        return null;
      }
      
      @Override
      public Object getValue(String paramName) throws IllegalArgumentException {
        return item;
      }
      
      @Override
      public boolean hasValue(String paramName) {
        return true;
      }
    };

  }
}
