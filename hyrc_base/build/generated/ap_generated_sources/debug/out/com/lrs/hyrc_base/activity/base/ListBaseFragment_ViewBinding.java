// Generated code from Butter Knife. Do not modify!
package com.lrs.hyrc_base.activity.base;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lrs.hyrc_base.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListBaseFragment_ViewBinding implements Unbinder {
  private ListBaseFragment target;

  @UiThread
  public ListBaseFragment_ViewBinding(ListBaseFragment target, View source) {
    this.target = target;

    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refreshLayout, "field 'refreshLayout'", SmartRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.statefulLayout = Utils.findRequiredViewAsType(source, R.id.ll_stateful, "field 'statefulLayout'", StatefulLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListBaseFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.refreshLayout = null;
    target.recyclerView = null;
    target.statefulLayout = null;
  }
}
