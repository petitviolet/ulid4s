# ulid for Scala

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

## LICENSE

[LICENSE](https://github.com/petitviolet/ulid4s/blob/master/LICENSE)
