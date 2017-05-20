// Generated code from Butter Knife. Do not modify!
package com.hiteshsahu.tensorflow_android.view.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.Object;
import java.lang.Override;

public class ClassifierActivityBase$$ViewBinder<T extends ClassifierActivityBase> extends BaseCameraActivity$$ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = (InnerUnbinder) super.bind(finder, target, source);
    View view;
    view = finder.findRequiredView(source, 2131492986, "field 'resultsView'");
    target.resultsView = finder.castView(view, 2131492986, "field 'resultsView'");
    return unbinder;
  }

  @Override
  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends ClassifierActivityBase> extends BaseCameraActivity$$ViewBinder.InnerUnbinder<T> {
    protected InnerUnbinder(T target) {
      super(target);
    }

    @Override
    protected void unbind(T target) {
      super.unbind(target);
      target.resultsView = null;
    }
  }
}
