package com.example.geoxplore;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geoxplore.api.ApiUtils;
import com.example.geoxplore.api.model.UserStatsRanking;
import com.example.geoxplore.api.service.UserService;
import com.example.geoxplore.dummy.DummyContent;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


///**
// * A fragment representing a list of Items.
// * <p/>
// * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
// * interface.
// */
public class RankingFragment extends Fragment {
    public static final String TAG = "ranking_fragment";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    // TODO zrobic ranking
    public RankingFragment() {
    }

    @SuppressWarnings("unused")
    public static RankingFragment newInstance(int columnCount) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking_list, container, false);
        List<UserStatsRanking> userStatsRankingList = new LinkedList<UserStatsRanking>();
        userStatsRankingList.add(new UserStatsRanking("error", 0, 0));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //TODO token musimy gdzieś zapisać
            ApiUtils
                    .getService(UserService.class)
                    .getRanking("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdXplZiIsImV4cCI6MTUyNDg5NzQxOX0.1XZC5IHfk0WD_Z5eEQ7RvjDSBQmEkWm5Z2B15Om4gqhvAaYvqrrqdxvhC9TZz4SETaKEKpfN5Sr3y9lP-PKh2w")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(x -> userStatsRankingList )
                    .subscribe(x -> recyclerView.setAdapter(new MyRankingRecyclerViewAdapter(x)));
            //recyclerView.setAdapter(new MyRankingRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnListFragmentInteractionListener {
//        void onListFragmentInteraction(DummyItem item);
//    }
}
