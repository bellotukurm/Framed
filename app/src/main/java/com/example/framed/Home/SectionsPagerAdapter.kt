package com.example.framed.Home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


//Class for storing fragments for tabs
class SectionsPagerAdapter: FragmentPagerAdapter{

    private val TAG = "SectionsPagerAdapter"
    private val mFragmentList: ArrayList<Fragment> = ArrayList<Fragment>()

    constructor(fm: FragmentManager?): super(fm)

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
    }

}
