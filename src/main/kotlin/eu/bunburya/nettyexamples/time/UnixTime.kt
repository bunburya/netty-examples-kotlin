package eu.bunburya.nettyexamples.time

import java.util.Date

class UnixTime(value: Long? = null) {

    val value: Long = value ?: System.currentTimeMillis() / 1000L + 2208988800L

    override fun toString(): String = Date((value - 2_208_988_800L) * 1000L).toString()

}