package io.kotest.engine.listener

import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.mpp.Logger
import io.kotest.mpp.bestName
import kotlin.reflect.KClass

object LoggingTestEngineListener : AbstractTestEngineListener() {

   private val logger = Logger(this::class)

   override suspend fun engineFinished(t: List<Throwable>) {
      logger.log { Pair(null, "Engine finished $t") }
   }

   override suspend fun specStarted(kclass: KClass<*>) {
      logger.log { Pair(kclass.bestName(), "specStarted") }
   }

   override suspend fun specFinished(kclass: KClass<*>, t: Throwable?) {
      logger.log { Pair(kclass.bestName(), "specFinished") }
   }

   override suspend fun testStarted(testCase: TestCase) {
      logger.log { Pair(testCase.name.testName, "testStarted") }
   }

   override suspend fun testFinished(testCase: TestCase, result: TestResult) {
      logger.log { Pair(testCase.name.testName, "testFinished") }
   }
}
