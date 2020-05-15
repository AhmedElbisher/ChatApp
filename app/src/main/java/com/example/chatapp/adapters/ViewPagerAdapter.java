package com.example.chatapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp.fragments.ChatsFragment;
import com.example.chatapp.fragments.ContactsFragment;
import com.example.chatapp.fragments.GroupFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return  new ChatsFragment();
            case 1 : return  new GroupFragment();
            case 2 : return  new ContactsFragment();
            default: return null;
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return  "chats";
            case 1 : return  "group";
            case 2 : return  "contacts";
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
