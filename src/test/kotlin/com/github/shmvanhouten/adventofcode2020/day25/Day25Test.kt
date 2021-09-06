package com.github.shmvanhouten.adventofcode2020.day25

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day25Test {

    @Nested
    inner class Part1 {

        private val `subject number` = 7

        @Test
        internal fun `given the subject number of 7 and a public key of 5764801, the loop size must be 8`() {
            val loopSize = bruteForceLoopNumber(`subject number`, 5764801)
            assertThat(loopSize, equalTo(8))
        }

        @Test
        internal fun `given the subject number of 7 and a public key of 17807724, the loop size must be 11`() {
            val loopSize = bruteForceLoopNumber(`subject number`, 17807724)
            assertThat(loopSize, equalTo(11))
        }

        @Test
        internal fun `given we found the loop number, we can calculate the private key`() {
            val privateKey = bruteForcePrivateKey(`subject number`, 5764801, 17807724)
            assertThat(privateKey, equalTo(14897079))
        }

        @Test
        internal fun `part 1`() {
            val privateKey = bruteForcePrivateKey(`subject number`, 1965712, 19072108)
            assertThat(privateKey, equalTo(16881444))
        }
    }

}
