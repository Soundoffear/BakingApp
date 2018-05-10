package com.example.soundoffear.bakingapp.recipeFragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.soundoffear.bakingapp.FullScreenExoActivity;
import com.example.soundoffear.bakingapp.ItemListActivity;
import com.example.soundoffear.bakingapp.R;
import com.example.soundoffear.bakingapp.StepDetailsActivity;
import com.example.soundoffear.bakingapp.Utilities.ExoplayerHandlerUtility;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class DetailsFragmentStep extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;

    public DetailsFragmentStep() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentDetailView = inflater.inflate(R.layout.detail_fragment_step, container,false);

        Bundle bundle = getArguments();

        assert bundle != null;
        String title = bundle.getString(StepDetailsActivity.BUNDLE_TITLE);
        String description = bundle.getString(StepDetailsActivity.BUNDLE_DESC);
        String videoURI = bundle.getString(StepDetailsActivity.BUNDLE_VIDEO);
        String thumbURI = bundle.getString(StepDetailsActivity.BUNDLE_THUMB);


        TextView tv_title = fragmentDetailView.findViewById(R.id.detailFragmentStepTitle);
        TextView tv_description = fragmentDetailView.findViewById(R.id.detailFragmentStepDetails);
        simpleExoPlayerView = fragmentDetailView.findViewById(R.id.detailFragmentExoPlayer);

        tv_title.setText(title);
        tv_description.setText(description);

        if(!TextUtils.isEmpty(videoURI)) {
            ExoplayerHandlerUtility.getInstance().uriExoplayer(getContext(), Uri.parse(videoURI), simpleExoPlayerView);
        } else {
            ExoplayerHandlerUtility.getInstance().uriExoplayer(getContext(), Uri.parse(thumbURI), simpleExoPlayerView);
        }

        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(getActivity(), FullScreenExoActivity.class);
            intent.putExtra(FullScreenExoActivity.FULL_SCREEN_URI, videoURI);
            startActivity(intent);
        }

        return fragmentDetailView;
    }

}
