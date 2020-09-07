package net.petitviolet.ulid4s

import java.util.Calendar

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{ FlatSpec, Matchers }

import scala.util.Random

class ULIDTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  behavior of "ULID"

  private def ulid(timestamp: => Long, random: => Double) =
    new ULIDGenerator(() => timestamp, () => random)

  "isValid" should "returns true if args are valid" in {
    forAll(Gen.chooseNum(ULID.constants.MIN_TIME, ULID.constants.MAX_TIME)) { timestamp: Long =>
      val random = Random.nextDouble();
      ULID.isValid(ulid(timestamp, random).generate.value) shouldBe true
    }
  }

  it should "generate" in {
    ulid(ULID.constants.MIN_TIME, 0.0d).generate.value shouldBe "00000000000000000000000000"
    ulid(1L, 0.0d).generate.value shouldBe "00000000010000000000000000"
    ulid(ULID.constants.MAX_TIME, 0.0d).generate.value shouldBe "7ZZZZZZZZZ0000000000000000"

    ulid(0L, 0.5d).generate.value shouldBe "0000000000FFFFFFFFFFFFFFFF"
    ulid(0L, 1.0d).generate.value shouldBe "0000000000ZZZZZZZZZZZZZZZZ"
  }

  it should "fail to generate" in {
    an[IllegalArgumentException] should be thrownBy {
      ulid(ULID.constants.MIN_TIME - 1L, 0.0d).generate
    }
    an[IllegalArgumentException] should be thrownBy {
      ulid(ULID.constants.MAX_TIME + 1L, 0.0d).generate
    }

    an[IllegalArgumentException] should be thrownBy ulid(0L, -0.1d).generate
    an[IllegalArgumentException] should be thrownBy ulid(0L, 1.1d).generate
  }

  it should "timestamp valid" in {
    forAll(Gen.calendar) { cal: Calendar =>
      whenever(
        cal.getTimeInMillis > ULID.constants.MIN_TIME
          && cal.getTimeInMillis < ULID.constants.MAX_TIME) {
        val result = ulid(cal.getTimeInMillis, 0.0d).generate.timestamp
        result shouldBe cal.getTimeInMillis
      }
    }
  }
  it should "timestamp invalid" in {
    forAll { str: String =>
      whenever(str.length != ULID.constants.ULID_LENGTH) {
        val result = ULID.timestamp(str)
        result shouldBe empty
      }
    }
  }

}
