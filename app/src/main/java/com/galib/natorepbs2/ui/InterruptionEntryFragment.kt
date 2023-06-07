package com.galib.natorepbs2.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.galib.natorepbs2.R
import com.galib.natorepbs2.gsheet.SignInManager
import com.galib.natorepbs2.gsheet.SpreadsheetViewModel
import com.galib.natorepbs2.gsheet.SpreadsheetViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import javax.annotation.Nullable


class InterruptionEntryFragment : Fragment(), SignInManager.SignInListener {
    private var signInManager: SignInManager? = null
    private val spreadsheetViewModel: SpreadsheetViewModel by viewModels {
        SpreadsheetViewModelFactory(requireContext())
    }
    private lateinit var textViewData: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_interruption_entry, container, false)
        textViewData = rootView.findViewById(R.id.textView)
        return rootView
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInManager = SignInManager(requireActivity(), this)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        if(account != null){
            onSignInSuccess(account)
        } else {
            resultLauncher.launch(signInManager?.getSignInIntent())
        }

        spreadsheetViewModel.getSheetData().observe(viewLifecycleOwner) { sheetData ->
            textViewData.text = "$sheetData"
        }
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("InterruptionEntry", "after sign in ${result.resultCode}")
        val data: Intent? = result.data
        signInManager!!.handleSignInResult(data)
    }

    override fun onSignInSuccess(account: GoogleSignInAccount) {
        Log.d("SpreadsheetManager", "onSignInSuccess: ")
        spreadsheetViewModel.setAccount(account)
        spreadsheetViewModel.fetchSheetData(null, null)
    }

    override fun onSignInFailure() {
        Log.d("SpreadsheetManager", "onSignInFailure: ")
        spreadsheetViewModel.setAccount(null)
        //Hide Entry Related everything
        //Show Error Message
    }
}