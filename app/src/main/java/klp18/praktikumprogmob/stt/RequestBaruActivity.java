package klp18.praktikumprogmob.stt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import klp18.praktikumprogmob.stt.database.SQLiteHelper;
import klp18.praktikumprogmob.stt.network.ApiService;
import klp18.praktikumprogmob.stt.network.ResponsModelRequest;
import klp18.praktikumprogmob.stt.network.RetrofitBuilder;
import klp18.praktikumprogmob.stt.network.UserModelResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestBaruActivity extends AppCompatActivity {

    EditText nama, lokasi, alamat, keterangan, tanggal;
    Button simpan, batal;
    SharedPreferences requestPref;
    ApiService service;
    private SQLiteHelper helper;
    private String namaRequest, lokasiRequest, alamatRequest, keteranganRequest, tanggalRequest, namaPengguna, emailPengguna;
    TextView namaPenggunaReq, idPenggunaReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_baru);
        nama = (EditText) findViewById(R.id.NamaAcaraRequestEditPengguna);
        lokasi = (EditText) findViewById(R.id.lokasiAcaraRequestEditPengguna);
        alamat = (EditText) findViewById(R.id.alamatAcaraRequestEditPengguna);
        keterangan = (EditText) findViewById(R.id.keteranganAcaraRequestEditPengguna);
        tanggal = (EditText) findViewById(R.id.tanggalAcaraRequestEditPengguna);
        simpan = (Button) findViewById(R.id.btnSimpanRequestEditPengguna);
        batal = (Button) findViewById(R.id.btnBatalRequestEditPengguna);
        namaPenggunaReq = (TextView) findViewById(R.id.namaPenggunaRequestEdit);
        idPenggunaReq = (TextView) findViewById(R.id.idPenggunaRequestBaruNew);
        helper = new SQLiteHelper(this);
        requestPref = getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        emailPengguna = requestPref.getString("email", " ");
        service = RetrofitBuilder.createService(ApiService.class);
        service.user(emailPengguna).enqueue(new Callback<UserModelResponse>() {
            @Override
            public void onResponse(Call<UserModelResponse> call, Response<UserModelResponse> response) {
                namaPenggunaReq.setText(response.body().getData().get(0).getName());
            }

            @Override
            public void onFailure(Call<UserModelResponse> call, Throwable t) {

            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaPengguna = namaPenggunaReq.getText().toString();
                namaRequest = nama.getText().toString();
                tanggalRequest = tanggal.getText().toString();
                lokasiRequest = lokasi.getText().toString();
                keteranganRequest = keterangan.getText().toString();
                alamatRequest = alamat.getText().toString();
                service.postRequest(namaRequest, tanggalRequest, lokasiRequest, keteranganRequest, namaPengguna, alamatRequest).enqueue(new Callback<ResponsModelRequest>() {
                    @Override
                    public void onResponse(Call<ResponsModelRequest> call, Response<ResponsModelRequest> response) {
                        if(response.isSuccessful()) {
                            boolean isInsert = helper.tambahDataRequest(namaRequest, tanggalRequest, lokasiRequest, keteranganRequest, namaPengguna, alamatRequest);
                            if(isInsert) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RequestBaruActivity.this);
                                builder.setTitle("Berhasil ditambahkan!")
                                        .setMessage("Apakah Anda ingin menambahkan data lagi?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                nama.setText("");
                                                lokasi.setText("");
                                                alamat.setText("");
                                                keterangan.setText("");
                                                tanggal.setText("");
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(RequestBaruActivity.this, RequestAcaraUserActivity.class));
                                            }
                                        }).show();
                            } else {
                                Toast.makeText(RequestBaruActivity.this, "data gagal disimpan ke dalam database", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RequestBaruActivity.this, "Terdapat kesalahan dalam server kami! " + response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModelRequest> call, Throwable t) {
                        Toast.makeText(RequestBaruActivity.this, "Gagal menghubungi ke server " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestBaruActivity.this);
                builder.setTitle("Batal?")
                        .setMessage("Apakah anda yakin ingin batal mengisi data ini?")
                        .setIcon(R.drawable.warning)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(RequestBaruActivity.this, RequestAcaraUserActivity.class));
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }
}