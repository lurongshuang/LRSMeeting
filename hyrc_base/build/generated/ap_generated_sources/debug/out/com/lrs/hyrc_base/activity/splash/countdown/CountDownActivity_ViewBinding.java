// Generated code from Butter Knife. Do not modify!
package com.lrs.hyrc_base.activity.splash.countdown;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lrs.hyrc_base.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CountDownActivity_ViewBinding implements Unbinder {
  private CountDownActivity target;

  @UiThread
  public CountDownActivity_ViewBinding(CountDownActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CountDownActivity_ViewBinding(CountDownActivity target, View source) {
    this.target = target;

    target.tvClose = Utils.findRequiredViewAsType(source, R.id.tvClose, "field 'tvClose'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CountDownActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvClose = null;
  }
}
