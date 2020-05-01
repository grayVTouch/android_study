package com.test.test.bcy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.test.test.R;

public class FocusFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater , ViewGroup vg , Bundle bundle)
    {
        return inflater.inflate(R.layout.bcy_app_fragment_focus, vg , false);
    }
}
