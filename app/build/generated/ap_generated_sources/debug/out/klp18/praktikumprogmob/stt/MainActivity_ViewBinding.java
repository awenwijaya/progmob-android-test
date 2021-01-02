// Generated code from Butter Knife. Do not modify!
package klp18.praktikumprogmob.stt;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view7f090078;

  private View view7f090070;

  private View view7f090071;

  private View view7f09007b;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnLogout, "field 'logout' and method 'logout'");
    target.logout = Utils.castView(view, R.id.btnLogout, "field 'logout'", ImageButton.class);
    view7f090078 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.logout();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEditProfile, "field 'editProfile' and method 'goToEditProfile'");
    target.editProfile = Utils.castView(view, R.id.btnEditProfile, "field 'editProfile'", ImageButton.class);
    view7f090070 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToEditProfile();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnEvent, "field 'event' and method 'goToEventList'");
    target.event = Utils.castView(view, R.id.btnEvent, "field 'event'", ImageButton.class);
    view7f090071 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToEventList();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnRequestAdmin, "field 'request' and method 'goToRequest'");
    target.request = Utils.castView(view, R.id.btnRequestAdmin, "field 'request'", ImageButton.class);
    view7f09007b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.goToRequest();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.logout = null;
    target.editProfile = null;
    target.event = null;
    target.request = null;

    view7f090078.setOnClickListener(null);
    view7f090078 = null;
    view7f090070.setOnClickListener(null);
    view7f090070 = null;
    view7f090071.setOnClickListener(null);
    view7f090071 = null;
    view7f09007b.setOnClickListener(null);
    view7f09007b = null;
  }
}
