// Generated code from Butter Knife. Do not modify!
package com.example.chatapp.ui.groupchats;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.chatapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupChatActivity_ViewBinding implements Unbinder {
  private GroupChatActivity target;

  @UiThread
  public GroupChatActivity_ViewBinding(GroupChatActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupChatActivity_ViewBinding(GroupChatActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.sendButton = Utils.findRequiredViewAsType(source, R.id.imageButton, "field 'sendButton'", ImageButton.class);
    target.massageField = Utils.findRequiredViewAsType(source, R.id.editText2, "field 'massageField'", EditText.class);
    target.groubChatRecyclerView = Utils.findRequiredViewAsType(source, R.id.groubChatRecyclerView, "field 'groubChatRecyclerView'", RecyclerView.class);
    target.addPerson = Utils.findRequiredViewAsType(source, R.id.addPerson, "field 'addPerson'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupChatActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.sendButton = null;
    target.massageField = null;
    target.groubChatRecyclerView = null;
    target.addPerson = null;
  }
}
