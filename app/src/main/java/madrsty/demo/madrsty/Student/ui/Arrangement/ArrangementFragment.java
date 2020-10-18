package madrsty.demo.madrsty.Student.ui.Arrangement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import madrsty.demo.madrsty.Adapter.Arrangement_student_adapter;
import madrsty.demo.madrsty.R;

public class ArrangementFragment extends Fragment {

    private ArrangementViewModel arrangementViewModel;

    private Arrangement_student_adapter adapter;
    private View root;

    Unbinder unbinder;

    LayoutAnimationController layoutAnimationController;

    @BindView(R.id.arrangement_recycler)
    RecyclerView arrangement_recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arrangementViewModel =
                ViewModelProviders.of(this).get(ArrangementViewModel.class);
        root = inflater.inflate(R.layout.fragment_arrangement, container, false);

        initView();
        arrangementViewModel.getMassageErorr().observe(getViewLifecycleOwner(), s -> {

            Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();

        });

        arrangementViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), studentModels -> {
            adapter = new Arrangement_student_adapter(getContext(), studentModels);
            arrangement_recycler.setAdapter(adapter);
            arrangement_recycler.setLayoutAnimation(layoutAnimationController);

        });
        return root;
    }

    private void initView() {

        unbinder = ButterKnife.bind(this, root);

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);

        arrangement_recycler.setHasFixedSize(true);
        arrangement_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}