// Generated code from Butter Knife. Do not modify!
package com.example.chatapp.ui.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.chatapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhoneActivity_ViewBinding implements Unbinder {
  private PhoneActivity target;

  @UiThread
  public PhoneActivity_ViewBinding(PhoneActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PhoneActivity_ViewBinding(PhoneActivity target, View source) {
    this.target = target;

    target.phoneNumberEditText = Utils.findRequiredViewAsType(source, R.id.phoneNumberEditText, "field 'phoneNumberEditText'", EditText.class);
    target.phoneNumberCard = Utils.findRequiredViewAsType(source, R.id.phoneNumberCard, "field 'phoneNumberCard'", CardView.class);
    target.verificationCodeEditText = Utils.findRequiredViewAsType(source, R.id.verificationCodeEditText, "field 'verificationCodeEditText'", EditText.class);
    target.verificationCodeTextCard = Utils.findRequiredViewAsType(source, R.id.verificationCodeTextCard, "field 'verificationCodeTextCard'", CardView.class);
    target.sendCodeButton = Utils.findRequiredViewAsType(source, R.id.sendCodeButton, "field 'sendCodeButton'", Button.class);
    target.sendCodeButtonCard = Utils.findRequiredViewAsType(source, R.id.sendCodeButtonCard, "field 'sendCodeButtonCard'", CardView.class);
    target.verifyButton = Utils.findRequiredViewAsType(source, R.id.verifyButton, "field 'verifyButton'", Button.class);
    target.verifyButtonCard = Utils.findRequiredViewAsType(source, R.id.verifyButtonCard, "field 'verifyButtonCard'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhoneActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.phoneNumberEditText = null;
    target.phoneNumberCard = null;
    target.verificationCodeEditText = null;
    target.verificationCodeTextCard = null;
    target.sendCodeButton = null;
    target.sendCodeButtonCard = null;
    target.verifyButton = null;
    target.verifyButtonCard = null;
  }
}
