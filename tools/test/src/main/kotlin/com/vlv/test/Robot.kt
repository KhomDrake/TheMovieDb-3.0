package com.vlv.test

interface Setup<L : Launch<C>, C : Check> {

    fun createCheck() : C

    fun createLaunch() : L

    fun setupLaunch()

    infix fun launch(func: L.() -> Unit) : L {
        setupLaunch()
        return createLaunch().apply(func)
    }

    infix fun check(func: C.() -> Unit) : C {
        setupLaunch()
        return createCheck().apply(func)
    }

}

interface Launch<C: Check> {

    fun createCheck() : C

    infix fun check(func: C.() -> Unit) : C {
        return createCheck().apply(func)
    }

}

interface Check
