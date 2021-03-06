package com.santukis.cleanarchitecture.game.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.scaleMatrix
import androidx.core.graphics.translationMatrix
import androidx.core.view.ViewCompat
import com.santukis.cleanarchitecture.R
import kotlin.math.sqrt


class PuzzleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatImageView(context, attrs, defStyleAttr) {

    val coordinates = Point()
    var canMove: Boolean = true

    var pieceWidth: Int = 0
    var pieceHeight: Int = 0

    init {
//        pivotY = 0f
//        pivotX = 0f
    }

    companion object {
        fun createPiece(context: Context, croppedBitmap: Bitmap, col: Int, cols: Int, row: Int, rows: Int, xCoord: Int, yCoord: Int, pieceWidth: Int, pieceHeight: Int): PuzzleView {
            val offset = Point(0, 0)
            if (col > 0) {
                offset.x = pieceWidth / 3
            }
            if (row > 0) {
                offset.y = pieceHeight / 3
            }
            // apply the offset to each piece
            val pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offset.x, yCoord - offset.y, pieceWidth + offset.x, pieceHeight + offset.y)
            val piece = PuzzleView(context)
            piece.setImageBitmap(pieceBitmap)
            piece.coordinates.x = xCoord - offset.x
            piece.coordinates.y = yCoord - offset.y
            piece.pieceWidth = pieceWidth + offset.x
            piece.pieceHeight = pieceHeight + offset.y
            // this bitmap will hold our final puzzle piece image
            val puzzlePiece = Bitmap.createBitmap(pieceWidth + offset.x, pieceHeight + offset.y, Bitmap.Config.ARGB_8888)
            // draw path
            val bumpSize = pieceHeight / 4
            val canvas = Canvas(puzzlePiece)
            val path = Path()
            path.moveTo(offset.x.toFloat(), offset.y.toFloat())
            drawUpperBound(row, path, pieceBitmap, offset.y, offset.x, bumpSize)
            drawRightBound(col, cols, path, pieceBitmap, offset.y, bumpSize)
            drawBottomBound(row, rows, path, offset.x, pieceBitmap, bumpSize)
            drawLeftBound(col, path, offset.x, offset.y, pieceBitmap, bumpSize)
            // mask the piece
            val paint = Paint()
            paint.setColor(-0x1000000)
            paint.setStyle(Paint.Style.FILL)
            canvas.drawPath(path, paint)
            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
            canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)
            // draw a white border
            var border = Paint()
            border.setColor(-0x7f000001 )
            border.setStyle(Paint.Style.STROKE)
            border.setStrokeWidth(8.0f)
            border.isDither = true
            canvas.drawPath(path, border)
            // draw a black border
            border = Paint()
            border.setColor(-0x80000000)
            border.setStyle(Paint.Style.STROKE)
            border.setStrokeWidth(3.0f)
            border.isDither = true
            canvas.drawPath(path, border)
            // set the resulting bitmap to the piece
            piece.setImageBitmap(puzzlePiece)
            return piece
        }

        private fun drawLeftBound(
            col: Int,
            path: Path,
            offsetX: Int,
            offsetY: Int,
            pieceBitmap: Bitmap,
            bumpSize: Int
        ) {
            if (col == 0) {
                // left side piece
                path.close()
            } else {
                // left bump
                path.lineTo(offsetX.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2)
                path.cubicTo(
                    offsetX.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                    offsetX.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                    offsetX.toFloat(),
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3
                )
                path.close()
            }
        }

        private fun drawBottomBound(
            row: Int,
            rows: Int,
            path: Path,
            offsetX: Int,
            pieceBitmap: Bitmap,
            bumpSize: Int
        ) {
            if (row == rows - 1) {
                // bottom side piece
                path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
            } else {
                // bottom bump
                path.lineTo(offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3 * 2, pieceBitmap.height.toFloat())
                path.cubicTo(
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6 * 5,
                    pieceBitmap.height.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6,
                    pieceBitmap.height.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3,
                    pieceBitmap.height.toFloat()
                )
                path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
            }
        }

        private fun drawRightBound(
            col: Int,
            cols: Int,
            path: Path,
            pieceBitmap: Bitmap,
            offsetY: Int,
            bumpSize: Int
        ) {
            if (col == cols - 1) {
                // right side piece
                path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
            } else {
                // right bump
                path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3)
                path.cubicTo(
                    pieceBitmap.width.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                    pieceBitmap.width.toFloat() - bumpSize,
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                    pieceBitmap.width.toFloat(),
                    offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2
                )
                path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
            }
        }

        private fun drawUpperBound(
            row: Int,
            path: Path,
            pieceBitmap: Bitmap,
            offsetY: Int,
            offsetX: Int,
            bumpSize: Int
        ) {
            if (row == 0) {
                // top side piece
                path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
            } else {
                // top bump
                path.lineTo(offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3, offsetY.toFloat())
                path.cubicTo(
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6,
                    offsetY.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6 * 5,
                    offsetY.toFloat() - bumpSize,
                    offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3 * 2,
                    offsetY.toFloat()
                )
                path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.d("Scale", "left: $left top: $top right: $right bottom: $bottom width: $width height: $height z:$z")
    }

    fun updateScale(scaleFactor: Float, frame: Rect) {
        if (!canMove) {
            pivotX = 0f
            pivotY = 0f
            translationX = (coordinates.x * scaleFactor) + frame.left - left
            translationY = (coordinates.y * scaleFactor) + frame.top  - top
        }

        scaleX = scaleFactor
        scaleY = scaleFactor

        ViewCompat.postInvalidateOnAnimation(this)
    }
}