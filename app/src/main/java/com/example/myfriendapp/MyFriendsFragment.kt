package com.example.myfriendapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsFragment : Fragment(){
    private var fabAddFriend: FloatingActionButton? = null
    private var listMyFriends: RecyclerView? = null
    private var list = mutableListOf<MyFriends>()
    lateinit var adapter: MyFriendAdapter

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsFragment{
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }

    private fun initLocalDB(){
        db = AppDatabase.getAppDatabase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }

    private fun initView(){
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriends = activity?.findViewById(R.id.listMyFriends)

        fabAddFriend?.setOnClickListener{ (activity as MainActivity).tampilMyFriendsAddFragment()}
        listMyFriends?.layoutManager = LinearLayoutManager(activity)
        adapter = MyFriendAdapter(requireActivity(), list)
        listMyFriends?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        ambilTeman()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun ambilTeman(){
        list.clear()
        list.addAll(myFriendDao!!.ambilSemuaTeman())
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriends = null
    }
}