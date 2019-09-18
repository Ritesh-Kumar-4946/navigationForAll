package com.nivesh.production.fragment.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nivesh.production.R;
import com.nivesh.production.utils.niveshProperty.textview_drawable.TextViewRichDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoard_DLFragment extends Fragment {

//    Context mContext = DashBoard_DLFragment.this;

    @BindView(R.id.tvrd_dashboard)
    TextView tvrd_dashboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dl_dashboard, container, false);
        ButterKnife.bind(this, view);


        return view;

//        return inflater.inflate(R.layout.fragment_dl_dashboard, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        https://stackoverflow.com/questions/35625099/how-to-change-color-of-vector-drawable-path-on-button-click
//        DrawableCompat.setTint(iv_demo.getDrawable(),
//                ContextCompat.getColor(getActivity(), R.color.colorRed_nivesh));
    }

}
