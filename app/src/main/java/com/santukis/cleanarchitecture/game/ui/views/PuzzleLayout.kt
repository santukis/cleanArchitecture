package com.santukis.cleanarchitecture.game.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Piece
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.*

class PuzzleLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    interface OnPuzzleStateChanged {
        fun onPiecesCreated(puzzleId: String, pieces: List<Piece>) {}
        fun onPieceChanged(puzzleId: String, piece: Piece)
        fun onPuzzleChanged(puzzle: Puzzle) {}
    }

    private var puzzle: Puzzle = Puzzle()
    private var selectedPiece: PieceView? = null
    private var pieceViews: MutableList<PieceView> = ArrayList()

    private var onPuzzleStateChanged: OnPuzzleStateChanged? = null

    private val isScaling = AtomicBoolean(false)
    private val isDragging = AtomicBoolean(false)
    private val isFirstLoad = AtomicBoolean(false)

    private var imageSize = Size(0, 0)

    private val scrollDistance = PointF()

    private var screenSize: Size = Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)

    private val frame = Rect()
    private val frameContour = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 15f
    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            puzzle.apply {
                scaleFactor *= detector.scaleFactor
                scaleFactor = max(0.5f, min(scaleFactor, 2.0f))
                onPuzzleStateChanged?.onPuzzleChanged(this)
            }
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
            return child is PieceView && child.piece.canMove
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            selectedPiece?.apply {
                piece.position.x = left
                piece.position.y = top
            }
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            isDragging.getAndSet(true)
            capturedChild.animate().translationZ(50f).alpha(0.5f).setDuration(100).start()
            selectedPiece = capturedChild as PieceView
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            releasedChild.animate().translationZ(10f).alpha(1f).setDuration(100).start()
            selectedPiece?.apply {
                val (x, y) = calculatePiecePosition(puzzle.scaleFactor, frame)
                dragHelper.settleCapturedViewAt(x, y)

                ViewCompat.postInvalidateOnAnimation(this@PuzzleLayout)

                onPuzzleStateChanged?.onPieceChanged(puzzle.id, piece)
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
            if (pieceViews.getOrNull(index)?.piece?.canMove == false) 0 else index
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
            toggleScalingIfFirstLoad(false)
            drawFrame()
            drawPieces()
            toggleScalingIfFirstLoad(true)
            restore()
        }
    }

    private fun toggleScalingIfFirstLoad(finished: Boolean) {
        if (isFirstLoad.get()) {
            isFirstLoad.set(!finished)
            isScaling.set(!isScaling.get())
        }
    }

    private fun Canvas.drawFrame() {
        when (isScaling.get()) {
            true -> scaleFrame(frame.centerX(), frame.centerY())
            false -> frame.offset(scrollDistance.x.toInt(), scrollDistance.y.toInt())
        }

        drawRect(frame, frameContour)
    }

    private fun scaleFrame(centerX: Int, centerY: Int) {
        val halfWidth = (imageSize.width * puzzle.scaleFactor).toInt() / 2
        val halfHeight = (imageSize.height * puzzle.scaleFactor).toInt() / 2

        frame.set(
            centerX - halfWidth,
            centerY - halfHeight,
            centerX + halfWidth,
            centerY + halfHeight
        )
    }

    private fun drawPieces() {
        pieceViews.forEach { piece ->
            if (isScaling.get()) piece.updateScale(puzzle.scaleFactor)

            if (!dragHelper.continueSettling(true))
                piece.updatePosition(puzzle.scaleFactor, frame, scrollDistance)
        }
    }

    fun createPuzzle(puzzle: Puzzle) {
        doOnLayout {
            this.puzzle = puzzle

            Glide.with(this)
                .asBitmap()
                .load(puzzle.image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        isFirstLoad.getAndSet(true)
                        createPieceViews(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }

    private fun createPieceViews(resource: Bitmap) {
        try {
            pieceViews.clear()

            val aspectRatio = resource.width.toFloat() / resource.height.toFloat()
            val scaledBitmap = scaleImage(resource, aspectRatio)

            imageSize = Size(scaledBitmap.width, scaledBitmap.height)

            val axisSize = buildAxisSize(aspectRatio, puzzle.difficulty)

            scaleFrame(screenSize.width / 2, screenSize.height / 2)

            val pieceSize = Size(scaledBitmap.width / axisSize.width, scaledBitmap.height / axisSize.height)

            when {
                puzzle.pieces.isEmpty() -> createPieces(scaledBitmap, axisSize, pieceSize)
                puzzle.pieces.isNotEmpty() -> createPieces(scaledBitmap, axisSize, pieceSize, puzzle.pieces)
            }

            addPiecesToLayout()

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun scaleImage(resource: Bitmap, aspectRatio: Float): Bitmap {
        val targetWidth = if (aspectRatio >= 1) screenSize.width else (resource.width * screenSize.height.toFloat() / resource.height).toInt()
        val targetHeight = if (aspectRatio < 1) screenSize.height else (resource.height * screenSize.width.toFloat() / resource.width).toInt()
        return Bitmap.createScaledBitmap(resource, targetWidth, targetHeight, true)
    }

    private fun buildAxisSize(aspectRatio: Float, difficulty: Difficulty): Size {
        val width = sqrt(difficulty.maxSize * aspectRatio)
        val height = sqrt(difficulty.maxSize / aspectRatio)

        return Size(width.toInt(), height.toInt())
    }

    private fun createPieces(scaledBitmap: Bitmap, axisSize: Size, pieceSize: Size) {
        val coordinates = Point(0, 0)

        for (row in 0 until axisSize.height) {
            coordinates.x = 0
            for (col in 0 until axisSize.width) {
                val pieceView = PieceView.createPiece(
                    context,
                    scaledBitmap,
                    Point(col, row),
                    axisSize,
                    coordinates,
                    pieceSize,
                    screenSize
                )
                pieceViews.add(pieceView)
                coordinates.x += pieceSize.width
            }
            coordinates.y += pieceSize.height
        }

        puzzle.pieces = pieceViews.map { it.piece }
        onPuzzleStateChanged?.onPiecesCreated(puzzle.id, puzzle.pieces)
    }

    private fun createPieces(scaledBitmap: Bitmap, axisSize: Size, pieceSize: Size, pieces: List<Piece>) {
        pieces.forEach { piece ->
            val pieceView = PieceView.createPiece(
                context,
                scaledBitmap,
                axisSize,
                pieceSize,
                piece
            )
            pieceViews.add(pieceView)
        }
    }

    private fun addPiecesToLayout() {
        pieceViews.shuffle()
        removeAllViews()

        pieceViews.forEach { piece ->
            addView(piece)
        }
    }

    fun addOnPuzzleStateChanged(listener: OnPuzzleStateChanged) {
        onPuzzleStateChanged = listener
    }
}