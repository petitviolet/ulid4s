# ulid for Scala

Strongly recommended to use [airframe-ulid](https://wvlet.org/airframe/docs/airframe-ulid) instead.

[![MavenCentral](https://maven-badges.herokuapp.com/maven-central/net.petitviolet/ulid4s_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.petitviolet/ulid4s_2.13)
 [![Actions Status](https://github.com/petitviolet/ulid4s/workflows/CI/badge.svg)](https://github.com/petitviolet/ulid4s/actions)
 [![codecov](https://codecov.io/gh/petitviolet/ulid4s/branch/master/graph/badge.svg)](https://codecov.io/gh/petitviolet/ulid4s)


ULID (Universally Unique Lexicographically Sortable Identifier) generator and parser for Scala.

Refer [ulid/spec](https://github.com/ulid/spec).

## Getting Started

```scala
libraryDependencies += "net.petitviolet" %% "ulid4s" % "<version>"
```

## Example

```scala
scala> import net.petitviolet.ulid4s.ULID
import net.petitviolet.ulid4s.ULID

scala> ULID.generate
res0: String = 01CZX6XV0FQHFK33XA7X4QG4NK

scala> ULID.timeStamp(ULID.generate)
res1: Option[Long] = Some(1546094841642)

scala> System.currentTimeMillis
res2: Long = 1546094852651
```

## Performance

JMH results are below.

```
$ git checkout v0.3.0 && sbt 'benchmark / jmh:run -i 3 -wi 3 -f 1 -t 1'
HEAD is now at 1770d2c fixed build.sbt

...

[info] Running (fork) org.openjdk.jmh.Main -i 3 -wi 3 -f 1 -t 1
[info] # JMH version: 1.21
[info] # VM version: JDK 1.8.0_192, Java HotSpot(TM) 64-Bit Server VM, 25.192-b12
[info] # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/jre/bin/java
[info] # VM options: <none>

...

[info] Benchmark                              Mode  Cnt       Score         Error  Units
[info] ULIDBench.generate_ULID_Random        thrpt    3  529616.492 ± 1496958.221  ops/s
[info] ULIDBench.generate_ULID_SecureRandom  thrpt    3   63538.492 ±    2606.774  ops/s
[info] ULIDBench.generate_UUID               thrpt    3  540198.296 ±  226327.267  ops/s
```

`ULID` provides a default ULID generator using `SecureRandom`, that is slower than UUID v4.  
If you do not need strong randomeness on generating ULID, you can use other random generator e.g. `Random` instead of `SecureRandom`.

## LICENSE

[LICENSE](https://github.com/petitviolet/ulid4s/blob/master/LICENSE)
