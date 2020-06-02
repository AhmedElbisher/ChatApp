// Generated code from Butter Knife. Do not modify!
package com.example.chatapp.ui.sitings;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.chatapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SittingsActivity_ViewBinding implements Unbinder {
  private SittingsActivity target;

  @UiThread
  public SittingsActivity_ViewBinding(SittingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SittingsActivity_ViewBinding(SittingsActivity target, View source) {
    this.target = target;

    target.profileImage = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profileImage'", CircleImageView.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.userName, "field 'userName'", EditText.class);
    target.userNameCard = Utils.findRequiredViewAsType(source, R.id.userNameCard, "field 'userNameCard'", CardView.class);
    target.userStatus = Utils.findRequiredViewAsType(source, R.id.userStatus, "field 'userStatus'", EditText.class);
    target.statusCard = Utils.findRequiredViewAsType(source, R.id.statusCard, "field 'statusCard'", CardView.class);
    target.updateButton = Utils.findRequiredViewAsType(source, R.id.update, "field 'updateButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SittingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profileImage = null;
    target.userName = null;
    target.userNameCard = null;
    target.userStatus = null;
    target.statusCard = null;
    target.updateButton = null;
  }
}
