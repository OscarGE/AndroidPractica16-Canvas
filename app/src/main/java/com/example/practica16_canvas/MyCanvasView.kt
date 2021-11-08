package com.example.practica16_canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat

//Definimos como constante el ancho de nuestro pincel
private const val STROKE_WIDTH = 12f

class MyCanvasView(context: Context): View(context) {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private var motionTouchEventX= 0f
    private var motionTouchEventY= 0f

    private var currentX=0f
    private var currentY=0f

    private val touchTolerance: Int = ViewConfiguration.get(context).scaledTouchSlop

    private val backgroundColor:Int = ResourcesCompat.getColor(resources, R.color.colorBackgraund, null)

    private val drawColor: Int = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

    //Creamos un objeto Path que guardará lo que se dibuje
    private var path = Path()

    //Definimos un objeto Paint
    private val paint: Paint =Paint().apply {
        color=drawColor
        isAntiAlias = true
        isDither=true
        style=Paint.Style.STROKE //default: Fill
        strokeJoin=Paint.Join.ROUND //default: MITER
        strokeCap=Paint.Cap.ROUND //default: BUTT
        strokeWidth= STROKE_WIDTH //default: Hairline-width (really thin)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //Permite reciclar el Bitmap y evitar perdida de memoria
        if(::extraBitmap.isInitialized)
            extraBitmap.recycle()

        //Creamos un nuevo bitmap y canvas, ademas asignamos el color de fondo
        extraBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        extraCanvas=Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f,0f,null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX=event.x
        motionTouchEventY=event.y

        when(event.action){
            MotionEvent.ACTION_DOWN->touchStart()
            MotionEvent.ACTION_MOVE->touchMove()
            MotionEvent.ACTION_UP->touchUp()
        }
        return true
    }

    private fun touchUp(){
        path.reset()
    }

    private fun touchMove(){
        val dx: Float= Math.abs(motionTouchEventX-currentX)
        val dy: Float= Math.abs(motionTouchEventY-currentY)
        if(dx>=touchTolerance || dy >= touchTolerance){
            path.quadTo(currentX,currentY,(motionTouchEventX+currentX)/2, (motionTouchEventY+currentY)/2)
            currentX=motionTouchEventX
            currentY=motionTouchEventY

            extraCanvas.drawPath(path,paint)
        }
        invalidate()
    }


    private fun touchStart(){
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX=motionTouchEventX
        currentY=motionTouchEventY
    }
}