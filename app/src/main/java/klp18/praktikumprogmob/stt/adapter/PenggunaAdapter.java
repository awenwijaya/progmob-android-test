package klp18.praktikumprogmob.stt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import klp18.praktikumprogmob.stt.R;
import klp18.praktikumprogmob.stt.database.Event;

public class PenggunaAdapter extends RecyclerView.Adapter<PenggunaAdapter.HolderData> {

    private List<Event> list;
    private Context ctx;

    public PenggunaAdapter(List<Event> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengguna, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        Event event = list.get(position);
        holder.id.setText(event.getId());
        holder.nama.setText(event.getNama_acara());
        holder.tanggal.setText(event.getTanggal_acara());
        holder.tempat.setText(event.getTempat_acara());
        holder.alamat.setText(event.getAlamat());
        holder.keterangan.setText(event.getKeterangan());
        holder.event = event;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView id, nama, tanggal, tempat, alamat, keterangan;
        Event event;
        public HolderData(@NonNull View v) {
            super(v);
            id = v.findViewById(R.id.idAcaraPengguna);
            nama = v.findViewById(R.id.tvNamaAcaraRequestPengguna);
            tanggal = v.findViewById(R.id.tvTanggalAcaraPengguna);
            tempat = v.findViewById(R.id.tvLokasiAcaraRequestPengguna);
            alamat = v.findViewById(R.id.tvAlamatRequestPengguna);
            keterangan = v.findViewById(R.id.tvKeteranganRequestPengguna);
        }
    }

}
