package com.amrubio27.mypokemoncard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amrubio27.mypokemoncard.pokemonCardNew.ExamplePokemonCardNew
import com.amrubio27.mypokemoncard.ui.theme.MyPokemonCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPokemonCardTheme {
                ExamplePokemonCardNew()
            }
        }
    }
}