package com.spring_guru.spring_DI.spring_DI.i18n

import com.spring_guru.spring_DI.spring_DI.controllers.i18n.MyI18NController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("EN")
@SpringBootTest
class MyI18NControllerTestEN {
    @Autowired
    lateinit var myI18NController: MyI18NController

    @Test
    fun sayHello() {
        println(myI18NController.sayHello())
    }
}