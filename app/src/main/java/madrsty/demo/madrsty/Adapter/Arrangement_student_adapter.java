package madrsty.demo.madrsty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import madrsty.demo.madrsty.Callback.IRecyclerClickListener;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.EventBus.ArrangementStudentItemClick;
import madrsty.demo.madrsty.Model.StudentModel;
import madrsty.demo.madrsty.R;

public class Arrangement_student_adapter extends RecyclerView.Adapter<Arrangement_student_adapter.MyViewHolder> {

    private Context context;
    private List<StudentModel> studentModels;
    int x;

    public Arrangement_student_adapter(Context context, List<StudentModel> studentModels) {
        this.context = context;
        this.studentModels = studentModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.arrangement_item2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.student_name_arrangement.setText(new StringBuilder(studentModels.get(getItemCount() - position - 1).getF_name()));


        //final StudentModel superPost = studentModels.get(getItemCount() - position - 1);


        holder.student_number.setText(new StringBuilder((String.valueOf(position + 1))));

        if (position == 0) {
            holder.gold_student.setVisibility(View.VISIBLE);

        } else if (position == 1) {
            holder.silver_student.setVisibility(View.VISIBLE);

        } else if (position == 2) {
            holder.bronz_student.setVisibility(View.VISIBLE);

        }

        if (studentModels.get((getItemCount() - position - 1)).getS_id().equals(common.getuID()))
            holder.current_student_selection.setVisibility(View.VISIBLE);


        holder.setListener((view, pos) -> {
            common.currentStudent = studentModels.get((getItemCount() - pos - 1));

            EventBus.getDefault().postSticky(new ArrangementStudentItemClick(true, studentModels.get((getItemCount() - pos - 1))));

        });

    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Unbinder unbinder;


        @BindView(R.id.student_name_arrangement)
        TextView student_name_arrangement;

        @BindView(R.id.student_number)
        TextView student_number;


        @BindView(R.id.gold_student)
        ImageView gold_student;

        @BindView(R.id.silver_student)
        ImageView silver_student;

        @BindView(R.id.bronz_student)
        ImageView bronz_student;

        @BindView(R.id.current_student_selection)
        ImageView current_student_selection;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClickListener(v, getAdapterPosition());

        }
    }

}
