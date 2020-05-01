package com.test.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.test.R;

import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater , ViewGroup viewGroup , Bundle bundle)
    {
        // LayoutInflater 布局扩充器
        return inflater.inflate(R.layout.fragment_one , viewGroup , false);
    }


}
