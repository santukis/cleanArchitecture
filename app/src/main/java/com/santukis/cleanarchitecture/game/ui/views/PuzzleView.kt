package com.santukis.cleanarchitecture.game.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Size
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import kotlin.math.*
import kotlin.random.Random

class PuzzleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    val position: Point = Point()
    val coordinates: Point = Point()
    var size = Size(0, 0)
    var canMove: Boolean = true


    companion object {
        fun createPiece(context: Context, croppedBitmap: Bitmap, cell: Point, axisSize: Size, coordinates: Point, pieceSize: Size, containerSize: Size): PuzzleView {
            val offset = calculatePieceOffset(cell, pieceSize)

            val pieceBitmap = Bitmap.createBitmap(
                croppedBitmap,
                coordinates.x - offset.x,
                coordinates.y - offset.y,
                pieceSize.width + offset.x,
                pieceSize.height + offset.y
            )
            val piece = createPiece(context, pieceBitmap, coordinates, offset, pieceSize, containerSize)

            val puzzlePiece = Bitmap.createBitmap(pieceSize.width + offset.x, pieceSize.height + offset.y, Bitmap.Config.ARGB_8888)

            drawPiece(puzzlePiece, axisSize, cell, offset, pieceSize, pieceBitmap)

            piece.setImageBitmap(puzzlePiece)
            return piece
        }

        private fun calculatePieceOffset(cell: Point, pieceSize: Size): Point {
            val offset = Point(0, 0)

            if (cell.x > 0) {
                offset.x = pieceSize.width / 3
            }
            if (cell.y > 0) {
                offset.y = pieceSize.height / 3
            }
            return offset
        }

        private fun createPiece(context: Context, pieceBitmap: Bitmap?, coordinates: Point, offset: Point, pieceSize: Size, containerSize: Size): PuzzleView {
            val piece = PuzzleView(context)
            piece.setImageBitmap(pieceBitmap)
            piece.coordinates.x = coordinates.x - offset.x
            piece.coordinates.y = coordinates.y - offset.y
            piece.size = Size(pieceSize.width + offset.x, pieceSize.height + offset.y)
            piece.position.x = Random.nextInt(0, containerSize.width - piece.size.width / 2)
            piece.position.y =  Random.nextInt(0, containerSize.height - piece.size.height / 2)
            return piece
        }

        private fun drawPiece(puzzlePiece: Bitmap, axisSize: Size, cell: Point, offset: Point, pieceSize: Size, pieceBitmap: Bitmap) {
            val canvas = Canvas(puzzlePiece)
            val path = drawPath(axisSize, cell, offset, pieceSize, pieceBitmap)
            createMask(canvas, path, pieceBitmap)
            drawBorder(canvas, path)
        }

        private fun drawPath(axisSize: Size, cell: Point, offset: Point, pieceSize: Size, pieceBitmap: Bitmap): Path {
            val bumpSize = pieceSize.height / 4

            return Path().also { path ->
                path.moveTo(offset.x.toFloat(), offset.y.toFloat())
                path.drawUpperBound(cell.y, pieceBitmap, offset, bumpSize)
                path.drawRightBound(cell.x, axisSize.width, pieceBitmap, offset.y, bumpSize)
                path.drawBottomBound(cell.y, axisSize.height, offset.x, pieceBitmap, bumpSize)
                path.drawLeftBound(cell.x, offset.x, offset.y, pieceBitmap, bumpSize)
            }
        }

        private fun Path.drawLeftBound(
            col: Int,
            offsetX: Int,
            offsetY: Int,
            pieceBitmap: Bitmap,
            bumpSize: Int
        ) {
            if (col == 0) {
                close()
            } else {
                lineTo(offsetX.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2)
                cubicTo(
                    offsetX.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                    offsetX.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                    offsetX.toFloat(),
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3
                )
                close()
            }
        }

        private fun Path.drawBottomBound(
            row: Int,
            rows: Int,
            offsetX: Int,
            pieceBitmap: Bitmap,
            bumpSize: Int
        ) {
            if (row == rows - 1) {
                lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())

            } else {
                lineTo(offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3 * 2, pieceBitmap.height.toFloat())
                cubicTo(
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6 * 5,
                    pieceBitmap.height.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6,
                    pieceBitmap.height.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3,
                    pieceBitmap.height.toFloat()
                )
                lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
            }
        }

        private fun Path.drawRightBound(
            col: Int,
            cols: Int,
            pieceBitmap: Bitmap,
            offsetY: Int,
            bumpSize: Int
        ) {
            if (col == cols - 1) {
                lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())

            } else {
                lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3)
                cubicTo(
                    pieceBitmap.width.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                    pieceBitmap.width.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                    pieceBitmap.width.toFloat(),
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2
                )
                lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
            }
        }

        private fun Path.drawUpperBound(
            row: Int,
            pieceBitmap: Bitmap,
            offset: Point,
            bumpSize: Int
        ) {
            if (row == 0) {
                lineTo(pieceBitmap.width.toFloat(), offset.y.toFloat())

            } else {
                lineTo(offset.x.toFloat() + (pieceBitmap.width - offset.x) / 3, offset.y.toFloat())
                cubicTo(
                    offset.x.toFloat() + (pieceBitmap.width - offset.x) / 6,
                    offset.y.toFloat() - bumpSize,
                    offset.x.toFloat() + (pieceBitmap.width - offset.x) / 6 * 5,
                    offset.y.toFloat() - bumpSize,
                    offset.x.toFloat() + (pieceBitmap.width - offset.x) / 3 * 2,
                    offset.y.toFloat()
                )
                lineTo(pieceBitmap.width.toFloat(), offset.y.toFloat())
            }
        }

        private fun createMask(canvas: Canvas, path: Path, pieceBitmap: Bitmap) {
            val paint = Paint()
            paint.color = -0x1000000
            paint.style = Paint.Style.FILL
            canvas.drawPath(path, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)
        }

        private fun drawBorder(canvas: Canvas, path: Path) {
            var border = Paint()
            border.color = -0x7f000001
            border.style = Paint.Style.STROKE
            border.strokeWidth = 8.0f
            border.isDither = true
            border.isAntiAlias = true
            canvas.drawPath(path, border)

            border = Paint()
            border.color = -0x80000000
            border.style = Paint.Style.STROKE
            border.strokeWidth = 3.0f
            border.isDither = true
            border.isAntiAlias = true
            canvas.drawPath(path, border)
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        updateZIndex()

    }

    private fun updateZIndex() {
        z = when (canMove) {
            true -> 10f
            else -> 0f
        }
    }

    fun updatePosition(scaleFactor: Float, frame: Rect, scrollDistance: PointF) {
        when(canMove) {
            true -> {
                ViewCompat.offsetLeftAndRight(this, scrollDistance.x.toInt())
                ViewCompat.offsetTopAndBottom(this, scrollDistance.y.toInt())
            }
            false -> {
                ViewCompat.offsetLeftAndRight(this, ((coordinates.x * scaleFactor) + frame.left - left).toInt())
                ViewCompat.offsetTopAndBottom(this, ((coordinates.y * scaleFactor) + frame.top - top).toInt())
            }
        }

        ViewCompat.postInvalidateOnAnimation(this)
    }

    fun updateScale(scaleFactor: Float) {
        val pivot = when(canMove) {
            true -> 0.5f
            false -> 0f
        }
        pivotX = pivot
        pivotY = pivot
        scaleX = scaleFactor
        scaleY = scaleFactor
    }

    fun calculatePiecePosition(scaleFactor: Float, frame: Rect): Pair<Int, Int> {
        val tolerance: Double = sqrt(width.toDouble().pow(2.0) + height.toDouble().pow(2.0)) / 10
        val weightedX = (coordinates.x * scaleFactor).toInt() + frame.left
        val weightedY = (coordinates.y * scaleFactor).toInt() + frame.top
        val xDiff: Int = abs(weightedX - position.x)
        val yDiff: Int = abs(weightedY - position.y)

        return when(xDiff <= tolerance && yDiff <= tolerance) {
            true -> {
                canMove = false
                weightedX to weightedY
            }
            else -> position.x to position.y
        }
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(position.x, position.y, position.x + size.width, position.y + size.height)
    }
}