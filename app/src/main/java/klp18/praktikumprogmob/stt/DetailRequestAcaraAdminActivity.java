package klp18.praktikumprogmob.stt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import klp18.praktikumprogmob.stt.database.SQLiteHelper;
import klp18.praktikumprogmob.stt.events.InsertEventActivity;
import klp18.praktikumprogmob.stt.network.ApiService;
import klp18.praktikumprogmob.stt.network.ResponsModelRequest;
import klp18.praktikumprogmob.stt.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRequestAcaraAdminActivity extends AppCompatActivity {

    private TextView nama, tanggal, lokasi, alamat, keterangan, nama_user;
    private Button approve, tolak;
    SQLiteHelper helper;
    private String id, nama_acara_request, tanggal_acara, lokasi_acara, alamat_acara, keterangan_acara, nama_pengguna;
    ApiService api;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request_acara_admin);
        nama = (TextView) findViewById(R.id.NamaAcaraRequestDetailAdmin);
        tanggal = (TextView) findViewById(R.id.TanggalAcaraRequestDetailAdmin);
        lokasi = (TextView) findViewById(R.id.LokasiAcaraRequestDetailAdmin2);
        alamat = (TextView) findViewById(R.id.AlamatAcaraRequestDetailAdmin);
        keterangan = (TextView) findViewById(R.id.KeteranganAcaraRequestDetailAdmin);
        approve = (Button) findViewById(R.id.btnApproveDetailAcaraAdmin);
        tolak = (Button) findViewById(R.id.btnTolakDetailAcaraAdmin2);
        context = DetailRequestAcaraAdminActivity.this;
        nama_user = (TextView) findViewById(R.id.NamaPenggunaRequestDetailAdmin);
        helper = new SQLiteHelper(this);
        api = RetrofitBuilder.createService(ApiService.class);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            id = bundle.getString("id");
            nama_acara_request = bundle.getString("nama");
            tanggal_acara = bundle.getString("tanggal");
            lokasi_acara = bundle.getString("lokasi");
            alamat_acara = bundle.getString("alamat");
            keterangan_acara = bundle.getString("keterangan");
            nama_pengguna = bundle.getString("nama_pengguna");
            nama.setText(nama_acara_request);
            tanggal.setText(tanggal_acara);
            lokasi.setText(lokasi_acara);
            alamat.setText(alamat_acara);
            keterangan.setText(keterangan_acara);
            nama_user.setText(nama_pengguna);
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequestAcaraAdminActivity.this);
                builder.setTitle("Konfirmasi approve")
                        .setCancelable(false)
                        .setMessage("Tekan OK untuk menambahkannya ke list kegiatan STT")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goToAddEvent = new Intent(context, InsertEventActivity.class);
                                goToAddEvent.putExtra("nama_acara", nama_acara_request);
                                goToAddEvent.putExtra("tanggal_acara", tanggal_acara);
                                goToAddEvent.putExtra("lokasi_acara", lokasi_acara);
                                goToAddEvent.putExtra("alamat_acara", alamat_acara);
                                goToAddEvent.putExtra("keterangan", keterangan_acara);
                                context.startActivity(goToAddEvent);
                                api.deleteRequest(id).enqueue(new Callback<ResponsModelRequest>() {
                                    @Override
                                    public void onResponse(Call<ResponsModelRequest> call, Response<ResponsModelRequest> response) {
                                        Log.d("Retrofit get", "Berhasil!");
                                        Integer isDelete = helper.deleteDataRequest(nama_acara_request);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsModelRequest> call, Throwable t) {
                                        Log.d("Retrofit get", "gagal");
                                    }
                                });
                            }
                        }).show();
            }
        });

        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequestAcaraAdminActivity.this);
                builder.setTitle("Tolak?")
                        .setCancelable(false)
                        .setMessage("Apakah Anda yakin ingin menolak permintaan acara ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                api.deleteRequest(id).enqueue(new Callback<ResponsModelRequest>() {
                                    @Override
                                    public void onResponse(Call<ResponsModelRequest> call, Response<ResponsModelRequest> response) {
                                        Log.d("Retrofit get", "Berhasil!");
                                        Integer isDelete = helper.deleteDataRequest(nama_acara_request);
                                        startActivity(new Intent(DetailRequestAcaraAdminActivity.this, RequestAcaraAdminActivity.class));
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsModelRequest> call, Throwable t) {
                                        Log.d("Retrofit get", "gagal");
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }
}