JFR JDBC
========

A JDBC driver that wraps an other JDBC driver and generates JFR events.

Usage
-----

Simply wrap your `DataSource` with a `JfrDataSource`

```java
new JfrDataSource(dataSource)
```

Implementation/Overhead
-----------------------

The implementation is based around wrapper objects.

* no reflection
* no string concatenation

Caveats
-------
- does not work with Oracle explicit statement caching
- does not work with unwrapped objects
- does not work with implicitly closed objects