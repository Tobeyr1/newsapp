package com.newsapp.android.ViewPageTitle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.newsapp.android.NewsAdapter;
import com.newsapp.android.R;
import com.newsapp.android.gson.Data;
import com.newsapp.android.gson.News;

import java.util.ArrayList;
import java.util.List;

import static com.newsapp.android.ViewPageTitle.TabAdapter.TITLES;

@SuppressLint("ValidFragment")
public  class MainFragment extends Fragment {
    private int newsType= 0 ;
    private int currentPage = 1;
   private ListView lvNews;
    private NewsAdapter adapter;
    private List<Data> dataList;

    public MainFragment() {
      /* this.category = category;*/
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* lvNews = (ListView) getView().findViewById(R.id.lvNews);
        adapter = new NewsAdapter(getActivity(), dataList);
        lvNews.setAdapter(adapter);*/
       /*mAdapter = new NewsAdapter(getActivity(),mDatas);
        mListView = (ListView) getView().findViewById(R.id.lvNews);
        mListView.setAdapter(mAdapter);*/


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.news_item,null );
       /* ListView lvNews = (ListView) view.findViewById(R.id.lvNews);
        lvNews = (ListView) view.findViewById(R.id.lvNews);
        Bundle mBundle = getArguments();
        String title = mBundle.getString("arg");*/
        return view;
    }
}

