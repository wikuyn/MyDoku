package com.moneymanagement.mywalletpro.activities.BottomNav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.moneymanagement.mywalletpro.R
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Alert.AlertFragment
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Home.HomeFragment
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Offers.OffersFragment
import com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Wallet.WalletFragment
import com.moneymanagement.mywalletpro.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {

    private var binding : ActivityBottomNavigationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.bottomNavigation.setOnItemSelectedListener { it ->
            when(it.itemId){
                R.id.menuHome ->{
                    val fragment = HomeFragment()
                    addFragment(fragment, HomeFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }
                R.id.menuWallet ->{
                    val fragment = WalletFragment()
                    addFragment(fragment, WalletFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }
                R.id.menuOffers ->{
                    val fragment = OffersFragment()
                    addFragment(fragment, OffersFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }
                R.id.menuAlert ->{
                    val fragment = AlertFragment()
                    addFragment(fragment, AlertFragment::class.java.simpleName)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        val homeFragment = HomeFragment()
        addFragment(homeFragment, HomeFragment::class.java.simpleName)

    }

    private fun addFragment(fragment: Fragment, tagname : String) {
        val mFragmentManager : FragmentManager = supportFragmentManager
        val mFragmentTransaction : FragmentTransaction = mFragmentManager.beginTransaction()

        val currentFragment : Fragment? = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null){
            mFragmentTransaction.hide(currentFragment)
        }

        var tempFragment : Fragment? = mFragmentManager.findFragmentByTag(tagname)
        if (tempFragment == null){
            tempFragment = fragment
            mFragmentTransaction.add(R.id.containers, tempFragment, tagname)
        }else{
            mFragmentTransaction.show(tempFragment)
        }

        mFragmentTransaction.setPrimaryNavigationFragment(tempFragment)
        mFragmentTransaction.setReorderingAllowed(true)
        mFragmentTransaction.commitAllowingStateLoss()
    }
}