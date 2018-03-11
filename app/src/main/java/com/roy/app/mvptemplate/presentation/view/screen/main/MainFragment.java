package com.roy.app.mvptemplate.presentation.view.screen.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.roy.app.mvptemplate.R;
import com.roy.app.mvptemplate.databinding.FragmentMainBinding;
import com.roy.app.mvptemplate.presentation.di.scope.ActivityScoped;
import com.roy.app.mvptemplate.presentation.view.base.BaseFragment;

import javax.inject.Inject;
import javax.inject.Named;

@ActivityScoped
public class MainFragment extends BaseFragment<MainContract.Presenter,FragmentMainBinding> implements MainContract.View{
    private static final String ARG_TEXT = "param_text";

    @Inject
    @Named("title")
    String mParamText;

    @Inject
    MainContract.Presenter presenter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Inject
    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParamText = getArguments().getString(ARG_TEXT);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("shijc","MainFragment,presenter:"+presenter);
        presenter.startTask();
    }

    @Override
    public void setText(String text) {
        mBinding.text.setText(text);
    }
}
