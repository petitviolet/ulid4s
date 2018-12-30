package net.petitviolet.ulid4s

import org.openjdk.jmh.annotations._

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.Throughput))
class ULIDBench {
  val ulidRandom =
    new ULID(() => System.currentTimeMillis(), () => util.Random.nextDouble())

  @Benchmark
  def generate_ULID_SecureRandom() = {
    ULID.generate
  }

  @Benchmark
  def generate_ULID_Random() = {
    ulidRandom.generate
  }

  @Benchmark
  def generate_UUID() = {
    // for compare
    java.util.UUID.randomUUID().toString
  }
}
