package com.mianlabs.pokeluv.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mianlabs.pokeluv.R;
import com.mianlabs.pokeluv.adapters.PokeListAdapter;
import com.mianlabs.pokeluv.utilities.PokePicker;
import com.mianlabs.pokeluv.utilities.TypefaceUtils;

/**
 * Must be launched from an AppCompatActivity class.
 */
public class PokeList extends Fragment {
    private static final String TAG = PokeList.class.getSimpleName();

    private AppCompatActivity mContext;
    private RecyclerView mPokemonList;
    private PokePicker.Generations mPokemonGeneration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_poke_list, container, false);
        mContext = (AppCompatActivity) getActivity();

        Bundle bundle = getArguments();
        String title; // Title for the Action Bar.
        if (bundle != null) {
            mPokemonGeneration = (PokePicker.Generations) bundle.get(GenActivity.GEN_KEY);
            title = mPokemonGeneration.getName();
            TypefaceUtils.setActionBarTitle(mContext, title);

            // Get the gen. numbers array for the adapter.
            int[] gen;
            switch (mPokemonGeneration) {
                case GEN_I:
                    gen = PokePicker.GenNumbers.getGenOne();
                    break;
                case GEN_II:
                    gen = PokePicker.GenNumbers.getGenTwo();
                    break;
                case GEN_III:
                    gen = PokePicker.GenNumbers.getGenThree();
                    break;
                case GEN_IV:
                    gen = PokePicker.GenNumbers.getGenFour();
                    break;
                case GEN_V:
                    gen = PokePicker.GenNumbers.getGenFive();
                    break;
                case GEN_VI:
                    gen = PokePicker.GenNumbers.getGenSix();
                    break;
                default:
                    gen = null;
                    Log.e(TAG, "Error while retrieving the generations number array");
                    break;
            }

            if (gen != null) {
                mPokemonList = (RecyclerView) rootView.findViewById(R.id.pokemon_list);
                PokeListAdapter pokeListAdapter = new PokeListAdapter(mContext, gen);
                mPokemonList.setAdapter(pokeListAdapter);
                mPokemonList.setLayoutManager(new GridLayoutManager(mContext, 3));
                mPokemonList.setHasFixedSize(true);
            }
        }
        Log.d(TAG, "Poke List view set");
        return rootView;
    }

    @Override
    public void onStop() {
        TypefaceUtils.setActionBarTitle(mContext, getString(R.string.app_name));
        super.onStop();
    }

}
