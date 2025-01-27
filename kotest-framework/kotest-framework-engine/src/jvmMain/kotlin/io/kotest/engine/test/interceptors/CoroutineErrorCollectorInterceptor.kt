package io.kotest.engine.test.interceptors

import io.kotest.assertions.ThreadLocalErrorCollector
import io.kotest.assertions.errorCollectorContextElement
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestScope
import io.kotest.mpp.Logger
import kotlinx.coroutines.withContext

internal actual fun coroutineErrorCollectorInterceptor(): TestExecutionInterceptor =
   CoroutineErrorCollectorInterceptor

/**
 * A [TestExecutionInterceptor] for keeping the error collector synchronized with thread-switching coroutines.
 * Note: This is a JVM only option.
 */
internal object CoroutineErrorCollectorInterceptor : TestExecutionInterceptor {

   private val logger = Logger(CoroutineErrorCollectorInterceptor::class)

   override suspend fun intercept(
      testCase: TestCase,
      scope: TestScope,
      test: suspend (TestCase, TestScope) -> TestResult
   ): TestResult {
      logger.log { Pair(testCase.name.testName, "Adding ${ThreadLocalErrorCollector.instance} to coroutine context") }
      return withContext(errorCollectorContextElement) {
         test(testCase, scope)
      }
   }
}
