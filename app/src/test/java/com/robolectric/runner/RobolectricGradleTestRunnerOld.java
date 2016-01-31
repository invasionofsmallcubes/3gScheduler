package com.robolectric.runner;


import org.junit.runners.model.InitializationError;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

import java.io.File;
import java.util.Properties;

public class RobolectricGradleTestRunnerOld extends RobolectricTestRunner {

  public RobolectricGradleTestRunnerOld(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override
  protected Class<? extends TestLifecycle> getTestLifecycleClass() {
    return MyTestLifecycle.class;
  }

  public static class MyTestLifecycle extends DefaultTestLifecycle {
  }

  @Override
  protected AndroidManifest getAppManifest(Config config) {
    String path = "src/main/AndroidManifest.xml";

    if (!new File(path).exists()) {
      path = "app/" + path;
    }

    return super.getAppManifest(overwriteConfig(config, "manifest", path));
  }

  protected Config.Implementation overwriteConfig(
          Config config, String key, String value) {
    Properties properties = new Properties();
    properties.setProperty(key, value);
    return new Config.Implementation(config,
            Config.Implementation.fromProperties(properties));
  }
}