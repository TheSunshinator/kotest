package io.kotest.core.spec.style

import io.kotest.core.factory.TestFactory
import io.kotest.core.factory.TestFactoryConfiguration
import io.kotest.core.factory.build
import io.kotest.core.spec.SpecConfiguration

/**
 * Creates a [TestFactory] from the given block.
 *
 * The receiver of the block is a [FunSpecTestFactoryConfiguration] which allows tests
 * to be defined using the 'fun-spec' style.
 */
fun behaviorSpec(block: BehaviorSpecTestFactoryConfiguration.() -> Unit): TestFactory {
   val config = BehaviorSpecTestFactoryConfiguration()
   config.block()
   return config.build()
}

class BehaviorSpecTestFactoryConfiguration : TestFactoryConfiguration(), FunSpecDsl {
   override val addTest = ::addDynamicTest
}

abstract class BehaviorSpec(body: BehaviorSpec.() -> Unit = {}) : SpecConfiguration(), FunSpecDsl {
   override val addTest = ::addRootTestCase

   init {
      body()
   }
}