// Generated code from Butter Knife. Do not modify!
package com.hiteshsahu.tensorflow_android.view.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SplashActivity$$ViewBinder<T extends SplashActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492984, "field 'appSlogan'");
    target.appSlogan = finder.castView(view, 2131492984, "field 'appSlogan'");
    view = finder.findRequiredView(source, 2131492983, "field 'appTitle'");
    target.appTitle = finder.castView(view, 2131492983, "field 'appTitle'");
    view = finder.findRequiredView(source, 2131492982, "field 'logo'");
    target.logo = finder.castView(view, 2131492982, "field 'logo'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends SplashActivity> implements Unbinder {
    private T target;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      target.appSlogan = null;
      target.appTitle = null;
      target.logo = null;
    }
  }
}
