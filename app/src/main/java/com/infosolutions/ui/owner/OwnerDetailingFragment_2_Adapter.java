package com.infosolutions.ui.owner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OwnerDetailingFragment_2_Adapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    OwnerDetailingFragment_2_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("response", OwnerDetailingActivity_2.response);
        Fragment frag = OwnerDetailingFragment_2.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
