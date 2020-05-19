package com.example.chatapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp.fragments.ChatsFragment;
import com.example.chatapp.fragments.ContactsFragment;
import com.example.chatapp.fragments.GroupFragment;
import com.example.chatapp.fragments.RequestFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return  new ContactsFragment();
            case 1 : return  new GroupFragment();
            case 2: return  new RequestFragment();
            default: return null;
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return  "chats";
            case 1 : return  "group";
            case 2 : return  "requests";
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
