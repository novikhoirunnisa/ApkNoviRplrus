package com.example.sepedarental;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sepedarental.R;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class EditSepeda extends AppCompatActivity {
    ImageView imageView;
    private Bitmap mSelectedImage;
    private String mSelectedImagePath;
    File mSelectedFileBanner;
    EditText mKodesepeda, mMerk, mWarna, mHargasewa,mJenis;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sepeda);
        mKodesepeda = findViewById(R.id.updatekodesepeda);
        mMerk = findViewById(R.id.updatemerk);
        mJenis = findViewById(R.id.updatejenis);
        mWarna = findViewById(R.id.updatewarna);
        mHargasewa = findViewById(R.id.updatehargasewa);
        imageView = findViewById(R.id.updateimg);
        save = findViewById(R.id.simpan1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(EditSepeda.this);
                new PickSetup().setCameraButtonText("Gallery");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postitem();
            }
        });

    }

    public void postitem() {
        final String kodesepeda = mKodesepeda.getText().toString();
        final String merk = mMerk.getText().toString();
        final String warna = mHargasewa.getText().toString();
        final String hargasewa = mHargasewa.getText().toString();
        final String jenis = mJenis.getText().toString();
        HashMap<String, String> body = new HashMap<>();
        body.put("kodesepeda", kodesepeda);
        body.put("merk", merk);
        body.put("jenis", jenis);
        body.put("warna", warna);
        body.put("hargasewa", hargasewa);

        AndroidNetworking.upload("http://localhost/pts/Gambar.php")
                .addMultipartFile("gambar", mSelectedFileBanner)
                .addMultipartParameter(body)
                .setPriority(Priority.HIGH)
//                .setOkHttpClient(((RS) getApplication()).getOkHttpClient())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EditSepeda.this, "Y", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(EditSepeda.this, "G", Toast.LENGTH_SHORT).show();
                    }

//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(AddItemActivity.this, Config.TOAST_AN_EROR, Toast.LENGTH_SHORT).show();
//                        Log.d("HBB", "onError: " + anError.getErrorBody());
//                        Log.d("HBB", "onError: " + anError.getLocalizedMessage());
//                        Log.d("HBB", "onError: " + anError.getErrorDetail());
//                        Log.d("HBB", "onError: " + anError.getResponse());
//                        Log.d("HBB", "onError: " + anError.getErrorCode());
//                    }
                });
    }
}