package com.example.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment(){

    companion object{
        fun newInstance(): MyFriendsAddFragment{
            return MyFriendsAddFragment()
        }
    }

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    private var edtName: EditText? = null
    private var edtEmail: EditText? = null
    private var edtTelp: EditText? = null
    private var edtAddress: EditText? = null
    private var btnSave: Button? = null
    private var spinnerGender: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friends_add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }

    private fun initLocalDB(){
        db = AppDatabase.getAppDatabase(requireActivity())
        myFriendDao = db?.myFriendDao()
        setDataSpinnerGender()
    }
    private fun initView() {
        spinnerGender = activity?.findViewById(R.id.spinnerGender)

        edtName = activity?.findViewById(R.id.edtName)
        edtEmail = activity?.findViewById(R.id.edtEmail)
        edtTelp = activity?.findViewById(R.id.edtTelp)
        edtAddress = activity?.findViewById(R.id.edtAdress)

        btnSave = activity?.findViewById(R.id.btnSave)
        btnSave?.setOnClickListener {
            validasiInput()
        }
    }

    private fun setDataSpinnerGender(){
        val adapater = ArrayAdapter.createFromResource(requireActivity(), R.array.gender_list, android.R.layout.simple_spinner_item)
        adapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender?.adapter = adapater
    }

    private fun validasiInput(){
        namaInput = edtName?.text.toString()
        emailInput = edtEmail?.text.toString()
        telpInput = edtTelp?.text.toString()
        alamatInput = edtAddress?.text.toString()
        genderInput = spinnerGender?.selectedItem.toString()

        when{
            namaInput.isEmpty() -> edtName?.error = "Nama tidak boleh kosong"
            emailInput.isEmpty() -> edtEmail?.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp?.error = "Telpon tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress?.error = "Alamat tidak boleh kosong"
            genderInput.equals("Pilih Jenis Kelamin") -> Toast.makeText(activity, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show()
            else -> {
                val teman = MyFriends(nama = namaInput, email = emailInput, telp = telpInput, alamat = alamatInput, kelamin = genderInput)
                tambahDataTeman(teman)
            }
        }
    }

    private fun tambahDataTeman(teman: MyFriends): Job{
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }
}