package com.soechka1.designsystem.component.shared

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.ColorUtils
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MyFirstAwesomeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    val ratioWidthLine = 0.4f

    var sectorsCount: Int = 0
        set(value) {
            field = value
            sectorsCountText = value.toString()
            invalidate()
        }

    var sectorColors: List<Int> = emptyList()
        set(value) {
            field = value
            updatePaints()
            invalidate()
        }

    var selectedIndex: Int? = null
        set(value) {
            field = value
            invalidate()
        }

    // cached objects (zero alloc)
    private var sectorsCountText: String = "0"
    
    private val arcRect = RectF()
    private var centerX = 0f
    private var centerY = 0f
    private var outerRadius = 0f
    private var innerRadius = 0f
    private var thickness = 0f
    private var arcRadius = 0f
    private var textOffset = 0f

    private val normalPaints = mutableListOf<Paint>()
    private val lightPaints = mutableListOf<Paint>()

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
    }

    private fun updatePaints() {
        normalPaints.clear()
        lightPaints.clear()
        for (color in sectorColors) {
            normalPaints.add(Paint(Paint.ANTI_ALIAS_FLAG).apply { 
                this.color = color 
                this.strokeWidth = thickness
            })
            lightPaints.add(Paint(Paint.ANTI_ALIAS_FLAG).apply {
                this.color = ColorUtils.blendARGB(color, Color.WHITE, 0.6f)
                this.strokeWidth = thickness
            })
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // calc dimensions once
        centerX = w / 2f
        centerY = h / 2f
        outerRadius = minOf(w, h) / 2f
        innerRadius = outerRadius * (1 - ratioWidthLine)
        thickness = outerRadius * ratioWidthLine
        arcRadius = outerRadius - thickness / 2f

        arcRect.set(
            centerX - arcRadius,
            centerY - arcRadius,
            centerX + arcRadius,
            centerY + arcRadius
        )

        textPaint.textSize = outerRadius * ratioWidthLine
        val fontMetrics = textPaint.fontMetrics
        textOffset = (fontMetrics.descent + fontMetrics.ascent) / 2f

        // update stroke width
        normalPaints.forEach { it.strokeWidth = thickness }
        lightPaints.forEach { it.strokeWidth = thickness }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (sectorsCount == 0 || sectorColors.isEmpty()) return

        // center text
        canvas.drawText(sectorsCountText, centerX, centerY - textOffset, textPaint)

        val sweep = 360f / sectorsCount
        val startOffset = 180f // 9 o clock

        // flat arcs
        for (i in 0 until sectorsCount) {
            val colorIndex = i % sectorColors.size
            val paint = if (selectedIndex == i) lightPaints[colorIndex] else normalPaints[colorIndex]
            
            paint.style = Paint.Style.STROKE
            paint.strokeCap = Paint.Cap.BUTT // flat end

            val startAngle = startOffset + i * sweep
            canvas.drawArc(arcRect, startAngle, sweep, false, paint)
        }

        // rounded heads
        for (i in 0 until sectorsCount) {
            val colorIndex = i % sectorColors.size
            val paint = if (selectedIndex == i) lightPaints[colorIndex] else normalPaints[colorIndex]
            
            paint.style = Paint.Style.FILL

            val headAngle = startOffset + (i + 1) * sweep
            val angleRad = Math.toRadians(headAngle.toDouble())
            
            // coords at arc end
            val cx = centerX + arcRadius * cos(angleRad).toFloat()
            val cy = centerY + arcRadius * sin(angleRad).toFloat()

            // overlap circle
            canvas.drawCircle(cx, cy, thickness / 2f, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (sectorsCount == 0 || sectorColors.isEmpty()) return true

            val dx = event.x - centerX
            val dy = event.y - centerY
            val distance = sqrt((dx * dx + dy * dy).toDouble()).toFloat()

            if (distance in innerRadius..outerRadius) {
                selectedIndex = getClickedSectorIndex(dx, dy, sectorsCount)
            } else {
                selectedIndex = null
            }

            return true
        }
        return super.onTouchEvent(event)
    }

    private fun getClickedSectorIndex(dx: Float, dy: Float, sectorsCount: Int): Int {
        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
        
        if (angle < 0) {
            angle += 360f
        }
        
        angle -= 180f
        
        if (angle < 0) {
            angle += 360f
        }
        
        val sweep = 360f / sectorsCount
        return (angle / sweep).toInt().coerceIn(0, sectorsCount - 1)
    }
}