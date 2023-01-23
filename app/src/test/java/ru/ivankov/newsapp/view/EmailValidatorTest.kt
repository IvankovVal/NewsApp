package ru.ivankov.newsapp.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


//@RunWith(AndroidJUnit4::class)
class EmailValidatorTest() {

    @Test
    fun CheckEmailTest() {
        assert(!emailValidator("@flwef.ru"))
        assert(!emailValidator("sdff@@3.ru"))
        assert(!emailValidator("@.ru"))
        assert(!emailValidator("r5g12eu"))
        assert(!emailValidator("e.ee@eu"))
        assert(!emailValidator("ggd@vsvv.uu"))
        assert(!emailValidator("g@3.ru"))

        assert(emailValidator("quasanastasi@yandex.ru"))
    }

    @Test
    fun RemoveSpaceTest() {
        Assert.assertEquals(removeSpace("   space"), "space")
        Assert.assertEquals(removeSpace("spa  ce"), "space")
        Assert.assertEquals(removeSpace("space    "), "space")
        Assert.assertEquals(removeSpace(" s p a c e "), "space")
        Assert.assertEquals(removeSpace("  space  "), "space")
    }
}