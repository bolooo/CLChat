package org.caiqizhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolo.chat.R;

public class Me extends Fragment {


    private View view;
    public static Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Chats.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me, container, false);
        Log.v("fragment", "personalinfo");
        return view;
    }
}
