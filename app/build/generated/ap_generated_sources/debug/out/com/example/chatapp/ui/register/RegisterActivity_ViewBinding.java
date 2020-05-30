// Generated code from Butter Knife. Do not modify!
package com.example.chatapp.ui.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.chatapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target, View source) {
    this.target = target;

    target.registerEmailText = Utils.findRequiredViewAsType(source, R.id.registerEmailText, "field 'registerEmailText'", EditText.class);
    target.registerPasswordText = Utils.findRequiredViewAsType(source, R.id.registerPasswordText, "field 'registerPasswordText'", EditText.class);
    target.createAccountButton = Utils.findRequiredViewAsType(source, R.id.createAccountButton, "field 'createAccountButton'", Button.class);
    target.alreadyhaveAccountText = Utils.findRequiredViewAsType(source, R.id.alreadyhaveAccountText, "field 'alreadyhaveAccountText'", TextView.class);
    target.progressbarLayout = Utils.findRequiredViewAsType(source, R.id.progressbarLayout, "field 'progressbarLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.registerEmailText = null;
    target.registerPasswordText = null;
    target.createAccountButton = null;
    target.alreadyhaveAccountText = null;
    target.progressbarLayout = null;
  }
}
