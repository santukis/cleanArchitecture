package com.santukis.cleanarchitecture.game.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.customview.widget.ViewDragHelper
import androidx.fragment.app.findFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.*
import kotlin.random.Random


class PuzzleLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val position: Point = Point()
    private var selectedPiece: PuzzleView? = null
    private var pieces: MutableList<PuzzleView> = ArrayList()

    private var scaleFactor = 1f

    private val isScaling = AtomicBoolean(false)

    private val original = Rect()
    private val frame = Rect()
    private val frameContour = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 30f
    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = max(0.5f, min(scaleFactor, 2.0f))
            ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            isScaling.getAndSet(true)
            return super.onScaleBegin(detector)
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            isScaling.getAndSet(false)
            ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)
            super.onScaleEnd(detector)
        }
    }

    private val scaleDetector = ScaleGestureDetector(context, scaleListener)

    private val dragCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child is PuzzleView && child.canMove
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            position.x = left
            position.y = top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            capturedChild.animate().translationZ(50f).scaleX(scaleFactor + 0.1f).scaleY(scaleFactor + 0.1f).setDuration(100).start()
            capturedChild.alpha = 0.5f
            selectedPiece = capturedChild as PuzzleView
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            selectedPiece?.apply {
                releasedChild.animate().translationZ(10f).scaleX(scaleFactor).scaleY(scaleFactor ).setDuration(0).start()
                alpha = 1f

                val tolerance: Double = sqrt(width.toDouble().pow(2.0) + height.toDouble().pow(2.0)) / 10
                val weightedX = (coordinates.x * scaleFactor).toInt() + frame.left - ((1 - scaleFactor) * width / 2).toInt()
                val weightedY = (coordinates.y * scaleFactor).toInt() + frame.top - ((1 - scaleFactor) * height / 2).toInt()
                val xDiff: Int = abs(weightedX - left)
                val yDiff: Int = abs(weightedY - top)

                if (xDiff <= tolerance && yDiff <= tolerance) {
                    dragHelper.settleCapturedViewAt(weightedX, weightedY)
                    isClickable = false
                    canMove = false

                } else {
                    dragHelper.settleCapturedViewAt(position.x, position.y)
                }

                ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }
    }

    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, 2f, dragCallback)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHelper.cancel()
            return false
        }
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            save()

            val width = (original.right * scaleFactor).toInt()
            val height = (original.bottom * scaleFactor).toInt()
            val centerX = (this@PuzzleLayout.width / 2)
            val centerY = (this@PuzzleLayout.height / 2)

            canvas.drawRect(
                frame.apply {
                    left = centerX - (width / 2)
                    right = centerX + (width / 2)
                    top = centerY - (height / 2)
                    bottom = centerY + (height / 2)
                }, frameContour
            )

            pieces.forEach { piece ->
                if (isScaling.get()) piece.updateScale(scaleFactor, frame)
            }

            restore()
        }
    }

    fun createPuzzle(url: String, size: Size) {
        doOnLayout {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        createPieces(resource, size)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }

    private fun createPieces(resource: Bitmap, size: Size) {
        pieces.clear()

        val aspectRatio = resource.width.toFloat() / resource.height.toFloat()
        val targetWidth = if (aspectRatio >= 1) width else (resource.width * height.toFloat() / resource.height).toInt()
        val targetHeight = if (aspectRatio < 1) height else (resource.height * width.toFloat() / resource.width).toInt()
        val scaledBitmap = Bitmap.createScaledBitmap(resource, targetWidth, targetHeight, true)

        Log.d("Scale", "original bitmap size width: ${resource.width} height: ${resource.height} aspectRation: $aspectRatio")
        Log.d("Scale", "scaled bitmap size width: ${scaledBitmap.width} height: ${scaledBitmap.height} aspectRation: ${scaledBitmap.width.toFloat() / scaledBitmap.height.toFloat()}")

        val rows = if (aspectRatio < 1) max(size.width, size.height) else min(size.width, size.height)
        val cols = if (aspectRatio > 1) max(size.width, size.height) else min(size.width, size.height)

        val pieceWidth = scaledBitmap.width / cols
        val pieceHeight = scaledBitmap.height / rows

        original.right = scaledBitmap.width
        original.bottom = scaledBitmap.height

        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                val piece = PuzzleView.createPiece(
                    context,
                    scaledBitmap,
                    col,
                    cols,
                    row,
                    rows,
                    xCoord,
                    yCoord,
                    pieceWidth,
                    pieceHeight
                )
                pieces.add(piece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }

        pieces.shuffle()
        removeAllViews()
        pieces.forEach { piece ->
            addView(piece)
            piece.layoutParams = (piece.layoutParams as? LayoutParams)?.apply {
                leftMargin = Random.nextInt(0, this@PuzzleLayout.width - piece.pieceWidth)
                topMargin = Random.nextInt(0, this@PuzzleLayout.height - piece.pieceHeight)
            }
        }
    }
}