package com.example.gessy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gessy.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listView);
        EditText inputBusca = view.findViewById(R.id.inputBusca);
        Button btnBuscar = view.findViewById(R.id.btnBuscar);
        Button btnLimpar = view.findViewById(R.id.btnLimpar);

        ArrayList<String> lista = new ArrayList<>();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lista);

        listView.setAdapter(adapter);

        inputBusca.setOnEditorActionListener((v, actionId, event) -> {
            String novoItem = inputBusca.getText().toString().trim();

            if (novoItem.isEmpty()) {
                Toast.makeText(requireContext(), "Digite algo!", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (lista.contains(novoItem)) {
                Toast.makeText(requireContext(), "Item já existe!", Toast.LENGTH_SHORT).show();
                return true;
            }

            lista.add(novoItem);
            adapter.notifyDataSetChanged();
            inputBusca.setText("");
            return true;
        });

        listView.setOnItemClickListener((parent, itemView, position, id) -> {
            lista.remove(position);
            adapter.notifyDataSetChanged();
        });

        btnBuscar.setOnClickListener(v1 -> {
            String texto = inputBusca.getText().toString().toLowerCase();
            ArrayList<String> filtrada = new ArrayList<>();

            for (String item : lista) {
                if (item.toLowerCase().contains(texto)) {
                    filtrada.add(item);
                }
            }

            ArrayAdapter<String> novoAdapter =
                    new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, filtrada);

            listView.setAdapter(novoAdapter);
        });

        btnLimpar.setOnClickListener(v12 -> {
            lista.clear();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}