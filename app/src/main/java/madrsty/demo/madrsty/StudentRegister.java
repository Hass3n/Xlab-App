package madrsty.demo.madrsty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import madrsty.demo.madrsty.Common.common;
import madrsty.demo.madrsty.Model.RequestModel;

public class StudentRegister extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference request;
    private StorageReference StorageRef;
    private Uri ImageUri;

    private String ImageRandomKey;
    private String imageLink;
    private String saveCurrentDate;
    private String saveCurrentTime;

    private SweetAlertDialog pDialog;

    private RequestModel studentModel;

    private String F_Name, L_Name, S_year, S_mail, S_phone, S_password;
    @BindView(R.id.regUserPhoto)
    ImageView regUserPhoto;

    @BindView(R.id.f_name)
    EditText f_name;

    @BindView(R.id.l_name)
    EditText l_name;

    @BindView(R.id.s_year)
    EditText s_year;

    @BindView(R.id.mail)
    EditText mail;

    @BindView(R.id.pp_passwod)
    EditText pp_passwod;

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.register)
    Button register;

    @OnClick(R.id.register)
    void onPressRegister() {
        uploadData();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        ButterKnife.bind(this);

        regUserPhoto.setOnClickListener(v ->
                CropImage.activity(ImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                        .setAspectRatio(1, 1)
                        .start(StudentRegister.this));

        initFirebase();
        getSupportActionBar().hide();

    }

    private void initFirebase() {

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        request = database.getReference();
        StorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri = result.getUri();

            Picasso.get()
                    .load(ImageUri)
                    .into(regUserPhoto);
        } else {
            Toast.makeText(this, "لم يتم اختيار الصوره", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadData() {

        F_Name = f_name.getText().toString();
        L_Name = l_name.getText().toString();
        S_year = s_year.getText().toString();
        S_password = pp_passwod.getText().toString();
        S_mail = mail.getText().toString();
        S_phone = phone.getText().toString();
        if (ImageUri == null) {
            Toast.makeText(this, "لم يتم اختيار الصوره...", Toast.LENGTH_SHORT).show();

        } else if ((TextUtils.isEmpty(F_Name))) {
            f_name.setError("ادخل اسمك الاول");
        } else if ((TextUtils.isEmpty(L_Name))) {
            f_name.setError("ادخل اسمك الاخير");
        } else if ((TextUtils.isEmpty(S_year))) {
            f_name.setError("ادخل سنتك الدراسيه");
        } else if ((TextUtils.isEmpty(S_mail))) {
            f_name.setError("ادخل البريد الالكتروني");
        } else if ((TextUtils.isEmpty(S_password))) {
            f_name.setError("ادخل كلمه السر");
        } else if ((TextUtils.isEmpty(S_phone))) {
            f_name.setError("ادخل رقم الموبيل");
        } else {


            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("جار ارسال الطلب");
            pDialog.setCancelable(false);
            pDialog.show();

            saveData(S_mail, S_password, F_Name, L_Name, S_year, S_phone);

        }
    }
/*
    private void createUser(final String Email, final String Password, String Fname, String Lname, String Year, String Phone) {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                       // saveData(Email, Password, Fname, Lname, Year, Phone, id);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }*/


    private void saveData(final String Email, final String Password, String Fname, String Lname, String Year, String Phone) {


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ImageRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference imagePath = StorageRef.child("StudentImage/").child(f_name.getText().toString() + "/" + ImageRandomKey + ".jpg");

        final UploadTask uploadTask = imagePath.putFile(ImageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            pDialog.dismiss();

            // Toast.makeText(StudentRegister.this, "Image Uploaded Successfully ........", Toast.LENGTH_SHORT).show();
            imagePath.getDownloadUrl().addOnSuccessListener(uri -> {

                studentModel = new RequestModel(
                        Fname,
                        Lname,
                        Year,
                        Email,
                        Password,
                        Phone,
                        uri.toString(),
                        request.push().getKey()
                );

                request.child(common.STUDENT_REQUESTS_KEY).child(studentModel.getR_id()).setValue(studentModel);


                Toast.makeText(StudentRegister.this, "تم ارسال طلب   " + studentModel.getF_name() + "بنجاح", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StudentRegister.this, LoginActivity.class);
                startActivity(intent);


            });

        }).addOnFailureListener(e -> {
            pDialog.dismiss();
            Toast.makeText(StudentRegister.this, "Erorr:" + e.getMessage(), Toast.LENGTH_SHORT).show();

        });


    }

}
