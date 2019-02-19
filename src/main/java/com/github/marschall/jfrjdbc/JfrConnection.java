package com.github.marschall.jfrjdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.ShardingKey;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

final class JfrConnection implements Connection {
  
  private final Connection delegate;

  JfrConnection(Connection delegate) {
    Objects.requireNonNull(delegate, "delegate");
    this.delegate = delegate;
  }

  private JdbcObjectEvent createStatementEvent() {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "createStatement";
    return event;
  }

  private JdbcObjectEvent prepareStatementEvent(String sql) {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "prepareStatement";
    event.query = sql;
    return event;
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
    return delegate.unwrap(iface);
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return delegate.isWrapperFor(iface);
  }

  public Statement createStatement() throws SQLException {
    var event = createStatementEvent();
    try {
      var statement = delegate.createStatement();
      return new JfrStatement(statement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    var event = createStatementEvent();
    try {
      var statement = delegate.createStatement(resultSetType, resultSetConcurrency);
      return new JfrStatement(statement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    var event = createStatementEvent();
    try {
      var statement = delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
      return new JfrStatement(statement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql) throws SQLException {
    var event = prepareStatementEvent(sql);
    event.begin();
    try {
      var preparedStatement = delegate.prepareStatement(sql);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    var event = prepareStatementEvent(sql);
    event.begin();
    try {
      var preparedStatement = delegate.prepareStatement(sql, resultSetType, resultSetConcurrency);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    var event = prepareStatementEvent(sql);
    event.begin();
    try {
      var preparedStatement = delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    var event = prepareStatementEvent(sql);
    try {
      var preparedStatement = delegate.prepareStatement(sql, autoGeneratedKeys);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    var event = prepareStatementEvent(sql);
    try {
      var preparedStatement = delegate.prepareStatement(sql, columnIndexes);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    var event = prepareStatementEvent(sql);
    try {
      var preparedStatement = delegate.prepareStatement(sql, columnNames);
      return new JfrPreparedStatement(preparedStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "prepareCall";
    event.query = sql;
    try {
      var callableStatement = delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      return new JfrCallableStatement(callableStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public CallableStatement prepareCall(String sql) throws SQLException {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "prepareCall";
    event.query = sql;
    try {
      var callableStatement = delegate.prepareCall(sql);
      return new JfrCallableStatement(callableStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "prepareCall";
    event.query = sql;
    try {
      var callableStatement = delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
      return new JfrCallableStatement(callableStatement);
    } finally {
      event.end();
      event.commit();
    }
  }

  public String nativeSQL(String sql) throws SQLException {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "nativeSQL";
    event.query = sql;
    try {
      return delegate.nativeSQL(sql);
    } finally {
      event.end();
      event.commit();
    }
  }

  public void setAutoCommit(boolean autoCommit) throws SQLException {
    delegate.setAutoCommit(autoCommit);
  }

  public boolean getAutoCommit() throws SQLException {
    return delegate.getAutoCommit();
  }

  public void commit() throws SQLException {
    delegate.commit();
  }

  public void rollback() throws SQLException {
    delegate.rollback();
  }

  public void close() throws SQLException {
    delegate.close();
  }

  public boolean isClosed() throws SQLException {
    return delegate.isClosed();
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    var event = new JdbcObjectEvent();
    event.operationObject = "Connection";
    event.operationName = "getMetaData";
    event.begin();
    try {
      return delegate.getMetaData();
    } finally {
      event.end();
      event.commit();
    }
  }

  public void setReadOnly(boolean readOnly) throws SQLException {
    delegate.setReadOnly(readOnly);
  }

  public boolean isReadOnly() throws SQLException {
    return delegate.isReadOnly();
  }

  public void setCatalog(String catalog) throws SQLException {
    delegate.setCatalog(catalog);
  }

  public String getCatalog() throws SQLException {
    return delegate.getCatalog();
  }

  public void setTransactionIsolation(int level) throws SQLException {
    delegate.setTransactionIsolation(level);
  }

  public int getTransactionIsolation() throws SQLException {
    return delegate.getTransactionIsolation();
  }

  public SQLWarning getWarnings() throws SQLException {
    return delegate.getWarnings();
  }

  public void clearWarnings() throws SQLException {
    delegate.clearWarnings();
  }

  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return delegate.getTypeMap();
  }

  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    delegate.setTypeMap(map);
  }

  public void setHoldability(int holdability) throws SQLException {
    delegate.setHoldability(holdability);
  }

  public int getHoldability() throws SQLException {
    return delegate.getHoldability();
  }

  public Savepoint setSavepoint() throws SQLException {
    return delegate.setSavepoint();
  }

  public Savepoint setSavepoint(String name) throws SQLException {
    return delegate.setSavepoint(name);
  }

  public void rollback(Savepoint savepoint) throws SQLException {
    delegate.rollback(savepoint);
  }

  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    delegate.releaseSavepoint(savepoint);
  }

  public Clob createClob() throws SQLException {
    return delegate.createClob();
  }

  public Blob createBlob() throws SQLException {
    return delegate.createBlob();
  }

  public NClob createNClob() throws SQLException {
    return delegate.createNClob();
  }

  public SQLXML createSQLXML() throws SQLException {
    return delegate.createSQLXML();
  }

  public boolean isValid(int timeout) throws SQLException {
    return delegate.isValid(timeout);
  }

  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    delegate.setClientInfo(name, value);
  }

  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    delegate.setClientInfo(properties);
  }

  public String getClientInfo(String name) throws SQLException {
    return delegate.getClientInfo(name);
  }

  public Properties getClientInfo() throws SQLException {
    return delegate.getClientInfo();
  }

  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return delegate.createArrayOf(typeName, elements);
  }

  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return delegate.createStruct(typeName, attributes);
  }

  public void setSchema(String schema) throws SQLException {
    delegate.setSchema(schema);
  }

  public String getSchema() throws SQLException {
    return delegate.getSchema();
  }

  public void abort(Executor executor) throws SQLException {
    delegate.abort(executor);
  }

  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    delegate.setNetworkTimeout(executor, milliseconds);
  }

  public int getNetworkTimeout() throws SQLException {
    return delegate.getNetworkTimeout();
  }

  public void beginRequest() throws SQLException {
    delegate.beginRequest();
  }

  public void endRequest() throws SQLException {
    delegate.endRequest();
  }

  public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey, int timeout) throws SQLException {
    return delegate.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
  }

  public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
    return delegate.setShardingKeyIfValid(shardingKey, timeout);
  }

  public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey) throws SQLException {
    delegate.setShardingKey(shardingKey, superShardingKey);
  }

  public void setShardingKey(ShardingKey shardingKey) throws SQLException {
    delegate.setShardingKey(shardingKey);
  }
  
  

}
