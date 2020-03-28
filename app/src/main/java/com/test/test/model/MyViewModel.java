package com.test.test.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel
{
    private int number = 0;

    // 使用 LiveData
    private MutableLiveData<Integer> Count;

    public int getNumber()
    {
        return this.number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public void incCount(int amount)
    {
        int count = this.Count.getValue();
        count += amount;
        this.Count.setValue(count);
    }

    public void decCount(int amount)
    {
        int count = this.Count.getValue();
        count -= amount;
        this.Count.setValue(count);
    }

    public MutableLiveData<Integer> getCount()
    {
        if (this.Count == null) {
            // 没有实例化 MutableLiveData
            this.Count = new MutableLiveData<>();
            this.Count.setValue(0);
        }
        return this.Count;
    }


}
