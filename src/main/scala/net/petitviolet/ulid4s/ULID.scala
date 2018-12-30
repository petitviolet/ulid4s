package net.petitviolet.ulid4s

import java.security.SecureRandom

object ULID {
  private val self = {
    val timeSource = () => System.currentTimeMillis()
    val randGen = () => {
      val random = SecureRandom.getInstance("NativePRNGNonBlocking")
      random.setSeed(java.util.UUID.randomUUID().toString.getBytes)
      random.nextDouble()
    }
    new ULID(timeSource, randGen)
  }

  def generate: String = self.generate

  /**
    * check a given string is valid as ULID
    * @param ulid
    * @return
    */
  def isValid(ulid: String): Boolean = {
    if (ulid.length != constants.ULID_LENGTH) false
    else ulid.forall { constants.DECODING_CHARS(_) != -1 }
  }

  /**
    * extract timestamp from given ULID string
    * @param ulid ULID string
    * @return Some(timestamp) when given string is valid ULID, otherwise None
    */
  def timestamp(ulid: String): Option[Long] = {
    if (isValid(ulid)) {
      val result = ulid.take(10).reverse.zipWithIndex.foldLeft(0L) {
        case (acc, (c, index)) =>
          val idx = constants.ENCODING_CHARS.indexOf(c)
          acc + (idx * Math.pow(constants.ENCODING_LENGTH, index)).toLong
      }
      Option(result)
    } else None
  }

  private[ulid4s] object constants {
    val ENCODING_CHARS: Array[Char] = Array(
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
      'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
      'Y', 'Z'
    )

    val DECODING_CHARS: Array[Byte] = Array[Byte](
      -1, -1, -1, -1, -1, -1, -1, -1, // 0
      -1, -1, -1, -1, -1, -1, -1, -1, // 8
      -1, -1, -1, -1, -1, -1, -1, -1, // 16
      -1, -1, -1, -1, -1, -1, -1, -1, // 24
      -1, -1, -1, -1, -1, -1, -1, -1, // 32
      -1, -1, -1, -1, -1, -1, -1, -1, // 40
      0, 1, 2, 3, 4, 5, 6, 7, // 48
      8, 9, -1, -1, -1, -1, -1, -1, // 56
      -1, 10, 11, 12, 13, 14, 15, 16, // 64
      17, 1, 18, 19, 1, 20, 21, 0, // 72
      22, 23, 24, 25, 26, -1, 27, 28, // 80
      29, 30, 31, -1, -1, -1, -1, -1, // 88
      -1, 10, 11, 12, 13, 14, 15, 16, // 96
      17, 1, 18, 19, 1, 20, 21, 0, // 104
      22, 23, 24, 25, 26, -1, 27, 28, // 112
      29, 30, 31 // 120
    )

    val ENCODING_LENGTH = 32

    val TIMESTAMP_LENGTH = 10
    val RANDOM_LENGTH = 16
    val ULID_LENGTH: Int = TIMESTAMP_LENGTH + RANDOM_LENGTH

    val MIN_TIME = 0x0L
    val MAX_TIME = 0x0000ffffffffffffL

  }
}

/**
  * ULID genrator
  * @param timeSource a function returns current time milliseconds(e.g. java.lang.System.currentTimeMillis())
  * @param random a function returns a random value (e.g. scala.util.Random.nextDouble())
  */
class ULID(timeSource: () => Long, random: () => Double) {
  import ULID.constants._

  /**
    * generate ULID string
    * @return
    */
  def generate: String = encodeTime() + encodeRandom()

  private def encodeTime(): String = {
    @annotation.tailrec
    def run(time: Long, out: String = "", count: Int = 0): String = {
      count match {
        case TIMESTAMP_LENGTH => out
        case _ =>
          val mod = (time % ENCODING_LENGTH).toInt
          run((time - mod) / ENCODING_LENGTH,
              ENCODING_CHARS(mod) + out,
              count + 1)
      }
    }

    timeSource() match {
      case time if (time < MIN_TIME) || (MAX_TIME < time) =>
        throw new IllegalArgumentException(
          s"cannot generate ULID string. Time($time) is invalid");
      case time =>
        run(time)
    }
  }

  private def encodeRandom(): String = {
    @annotation.tailrec
    def run(out: String = "", count: Int = 0): String = {
      count match {
        case RANDOM_LENGTH => out
        case _ =>
          val rand = random()
          if (rand < 0.0d || 1.0d < rand) {
            throw new IllegalArgumentException(
              s"random must not under 0.0 or over 1.0. random value = $rand")
          }
          val index = Math.floor((ENCODING_LENGTH - 1) * rand).toInt
          run(ENCODING_CHARS(index) + out, count + 1)
      }
    }
    run()
  }
}
