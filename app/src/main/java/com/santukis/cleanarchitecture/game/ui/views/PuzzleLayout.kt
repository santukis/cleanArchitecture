package com.santukis.cleanarchitecture.game.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.customview.widget.ViewDragHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.*

class PuzzleLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var selectedPiece: PuzzleView? = null
    private var pieces: MutableList<PuzzleView> = ArrayList()

    private var scaleFactor = 1f

    private val isScaling = AtomicBoolean(false)
    private val isDragging = AtomicBoolean(false)

    private var imageSize = Size(0, 0)

    private val screenRect = RectF()

    private val scrollDistance = PointF()

    private val frame = Rect()
    private val frameContour = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 15f
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
            selectedPiece?.position?.x = left
            selectedPiece?.position?.y = top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            isDragging.getAndSet(true)
            capturedChild.animate().translationZ(50f).scaleX(scaleFactor + 0.1f).scaleY(scaleFactor + 0.1f).setDuration(100).start()
            capturedChild.alpha = 0.5f
            selectedPiece = capturedChild as PuzzleView
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            releasedChild.animate().translationZ(10f).scaleX(scaleFactor).scaleY(scaleFactor).setDuration(0).start()
            releasedChild.alpha = 1f
            selectedPiece?.apply {
                val (x, y) = calculatePiecePosition(scaleFactor, frame)
                dragHelper.smoothSlideViewTo(this, x, y)

                ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)
            }

            isDragging.getAndSet(false)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun getOrderedChildIndex(index: Int): Int =
            if (pieces.getOrNull(index)?.canMove == false) 0 else index
    }

    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, 2f, dragCallback)

    private val onGestureListener: GestureDetector.OnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (isScaling.get() || isDragging.get()) {
                scrollDistance.set(0f, 0f)
                return false
            }

            scrollDistance.set(-distanceX, -distanceY)

            ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)
            return true
        }
    }

    private val gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, onGestureListener)

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

        if (event.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_CANCEL) {
            scrollDistance.set(0f, 0f)
        }
        return gestureDetector.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            save()
            updatePosition()
            drawBackground()
            drawFrame()
            drawPieces()
            restore()
        }
    }

    private fun updatePosition() {
        screenRect.set(
            min(max(screenRect.left + scrollDistance.x, -width.toFloat()), 0f),
            min(max(screenRect.top + scrollDistance.y, -height.toFloat()), 0f),
            max(min(screenRect.right + scrollDistance.x, width * 2f), width.toFloat()),
            max(min(screenRect.bottom + scrollDistance.y, height * 2f), height.toFloat())
        )
    }

    private fun Canvas.drawBackground() {
        drawColor(Color.WHITE)
    }

    private fun Canvas.drawFrame() {
        val halfWidth = (imageSize.width * scaleFactor).toInt() / 2
        val halfHeight = (imageSize.height * scaleFactor).toInt() / 2
        val centerX = screenRect.centerX().toInt()
        val centerY = screenRect.centerY().toInt()

        drawRect(
                frame.apply {
                    left = centerX - halfWidth
                    right = centerX + halfWidth
                    top = centerY - halfHeight
                    bottom = centerY + halfHeight
                }, frameContour
        )
    }

    private fun drawPieces() {
        pieces.forEach { piece ->
            if (isScaling.get()) piece.updateScale(scaleFactor)

            if (!dragHelper.continueSettling(true)) piece.updatePosition(scaleFactor, frame)
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
        val scaledBitmap = scaleImage(resource, aspectRatio)

        imageSize = Size(scaledBitmap.width, scaledBitmap.height)

        val axisSize = Size(if (aspectRatio >= 1) max(size.width, size.height) else min(size.width, size.height),
                if (aspectRatio < 1) max(size.width, size.height) else min(size.width, size.height))

        val pieceSize = Size(scaledBitmap.width / axisSize.width, scaledBitmap.height / axisSize.height)
        
        val coordinates = Point(0, 0)
        for (row in 0 until axisSize.height) {
            coordinates.x = 0
            for (col in 0 until axisSize.width) {
                val piece = PuzzleView.createPiece(
                        context,
                        scaledBitmap,
                        Point(col, row),
                        axisSize,
                        coordinates,
                        pieceSize,
                        Size(width, height)
                )
                pieces.add(piece)
                coordinates.x += pieceSize.width
            }
            coordinates.y += pieceSize.height
        }

        addPiecesToLayout()
    }

    private fun addPiecesToLayout() {
        pieces.shuffle()
        removeAllViews()

        pieces.forEach { piece ->
            addView(piece)
        }
    }

    private fun scaleImage(resource: Bitmap, aspectRatio: Float): Bitmap {
        val targetWidth = if (aspectRatio >= 1) width else (resource.width * height.toFloat() / resource.height).toInt()
        val targetHeight = if (aspectRatio < 1) height else (resource.height * width.toFloat() / resource.width).toInt()
        return Bitmap.createScaledBitmap(resource, targetWidth, targetHeight, true)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenRect.set(
            -w / 2f,
            -h / 2f,
            w + w / 2f,
            h + h / 2f
        )
    }
}