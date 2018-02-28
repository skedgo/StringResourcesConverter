package com.skedgo.tools.platform.android

import com.skedgo.tools.model.TransUnit
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class TransUnitAndroidUtilsTest {

    @Test
    fun `should clean the id with one ampersand`() {
        // Arrange.
        val transUnit = TransUnit("", "xx&yy", "", "")

        // Act.
        transUnit.cleanSource()

        // Assert.
        transUnit.source `should be equal to` "xx_ampersandyy"
    }

    @Test
    fun `should clean the id with any ampersand`() {
        // Arrange.
        val transUnit = TransUnit("", "&xx&yy", "", "")

        // Act.
        transUnit.cleanSource()

        // Assert.
        transUnit.source `should be equal to` "_ampersandxx_ampersandyy"
    }

    @Test
    fun `should transform the id with %ld into pattern`() {
        // Arrange.
        val transUnit = TransUnit("", "xx%ldyy", "", "")

        // Act.
        transUnit.cleanSource()

        // Assert.
        transUnit.source `should be equal to` "xx_patternyy"
    }
    
    @Test
    fun `should transform the id with %@ into pattern`() {
        // Arrange.
        val transUnit = TransUnit("", "xx%@yy", "", "")

        // Act.
        transUnit.cleanSource()

        // Assert.
        transUnit.source `should be equal to` "xx_patternyy"
    }    
    
    @Test
    fun `should transform the id with %d@ into pattern`() {
        // Arrange.
        val transUnit = TransUnit("", "xx%1@yy%2@", "", "")

        // Act.
        transUnit.cleanSource()

        // Assert.
        transUnit.source `should be equal to` "xx_patternyy_pattern"
    }

    @Test
    fun `should clean the target with one ampersand`() {
        // Arrange.
        val transUnit = TransUnit("", "", "xx&yy", "")

        // Act.
        transUnit.cleanTarget()

        // Assert.
        transUnit.target `should be equal to` "xx&amp;yy"
    }

    @Test
    fun `should clean the target with apostrophe`() {
        // Arrange.
        val transUnit = TransUnit("", "", "'xxyy'", "")

        // Act.
        transUnit.cleanTarget()

        // Assert.
        transUnit.target `should be equal to` "\\'xxyy\\'"
    }

    @Test
    fun `should update the target with android patterns`() {
        // Arrange.
        val transUnit = TransUnit("", "", "xxPERCATyyPERCATzzPERCAT", "")

        // Act.
        transUnit.cleanTarget()

        // Assert.
        transUnit.target `should be equal to` "xx%1\$syy%2\$szz%3\$s"
    }
}