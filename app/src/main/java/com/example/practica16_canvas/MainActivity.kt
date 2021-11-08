package com.example.practica16_canvas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        //Creamos una instancia a nuestro Canvas
        val myCanvasView = MyCanvasView(this)
        //Lo hacemos trabajar en pantalla completa
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        //Agregamos la descripcion del contenido
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        //Asignamos nuestro canvas
        setContentView(myCanvasView)
    }
}