package com.robolectric.runner


import org.junit.runners.model.InitializationError
import org.robolectric.DefaultTestLifecycle
import org.robolectric.RobolectricTestRunner
import org.robolectric.TestLifecycle
import org.robolectric.annotation.Config
import org.robolectric.manifest.AndroidManifest

import java.io.File
import java.util.Properties

class RobolectricGradleTestRunnerOld @Throws(InitializationError::class)
constructor(testClass: Class<*>) : RobolectricTestRunner(testClass) {

    override fun getTestLifecycleClass(): Class<out TestLifecycle<Any>> {
        return MyTestLifecycle::class.java
    }

    class MyTestLifecycle : DefaultTestLifecycle()

    override fun getAppManifest(config: Config): AndroidManifest {
        var path = "src/main/AndroidManifest.xml"

        if (!File(path).exists()) {
            path = "app/" + path
        }

        return super.getAppManifest(overwriteConfig(config, "manifest", path))
    }

    protected fun overwriteConfig(
            config: Config, key: String, value: String): Config.Implementation {
        val properties = Properties()
        properties.setProperty(key, value)
        return Config.Implementation(config,
                Config.Implementation.fromProperties(properties))
    }
}