package com.spring_guru.spring_DI.spring_DI.services

import com.spring_guru.spring_DI.spring_DI.controllers.MyController
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class LifeCycleDemoBean : InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware,
    BeanPostProcessor {

    private var javaVer: String? = null

    init {
        println("## I'm in the LifeCycleBean Constructor ##")
    }

    @Value("\${java.specification.version}")
    fun setJavaVer(javaVer: String) {
        this.javaVer = javaVer
        println("## 1 Properties Set. Java Ver: $javaVer")
    }


    override fun afterPropertiesSet() {
        println("## 6 afterPropertiesSet Populate Properties The LifeCycleBean has its properties set!");
    }

    @PreDestroy
    fun preDestroy() {
        println("## 7 The @PreDestroy annotated method has been called");
    }

    override fun destroy() {
        println("## 8 DisposableBean.destroy The Lifecycle bean has been terminated")
    }

    override fun setBeanName(name: String) {
        println("## 2 BeanNameAware My Bean Name is: $name");
    }

    override fun setBeanFactory(beanFactory: BeanFactory) {
        println("## 3 BeanFactoryAware - Bean Factory has been set")
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        println("## 4 ApplicationContextAware - Application context has been set")
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        println("## postProcessBeforeInitialization: $beanName")

        if (bean is MyController) {
            println("Calling before init")
            bean.beforeInit()
        }

        return super.postProcessBeforeInitialization(bean, beanName)
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        println("## postProcessAfterInitialization: $beanName")

        if (bean is MyController) {
            println("Calling after init")
            bean.afterInit()
        }

        return super.postProcessAfterInitialization(bean, beanName)
    }
}