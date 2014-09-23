package com.robolectric.runner;

import android.app.Application;

import com.bitquartet.tgscheduler.app.TestNSApplication;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

import java.lang.reflect.Method;

public class RobolectricGradleTestRunner extends RobolectricTestRunner {

  public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
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
    String myAppPath = RobolectricGradleTestRunner.class.getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .getPath();
    String manifestPath = myAppPath + "../../../src/main/AndroidManifest.xml";
    String resPath = myAppPath + "../../../src/main/res";
    String assetPath = myAppPath + "../../../src/main/assets";
    return createAppManifest(Fs.fileFromPath(manifestPath), Fs.fileFromPath(resPath),
                             Fs.fileFromPath(assetPath));
  }
}