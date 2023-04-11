package recognition

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import recognition.dataset.standardNumbers
import java.util.concurrent.CountDownLatch

class DrawingBoard : Application() {

    private val canvasWidth = 800.0
    private val canvasHeight = 800.0
    private val pixelSize = 100.0

    private lateinit var canvas: Canvas
    private lateinit var gc: GraphicsContext
    private lateinit var pixelData: Array<BooleanArray>
    private var paintLatch = CountDownLatch(1)

    override fun start(primaryStage: Stage) {
        canvas = Canvas(canvasWidth, canvasHeight)
        gc = canvas.graphicsContext2D

        pixelData = Array((canvasWidth / pixelSize).toInt()) { BooleanArray((canvasHeight / pixelSize).toInt()) }

        canvas.setOnMouseDragged { event ->
            val x = (event.x / pixelSize).toInt()
            val y = (event.y / pixelSize).toInt()
            if (x >= 0 && x < pixelData.size && y >= 0 && y < pixelData[0].size) {
                gc.fill = Color.BLACK
                gc.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize)
                pixelData[y][x] = true
            }
        }

        val root = BorderPane(canvas)
        val scene = Scene(root)

        primaryStage.scene = scene
        primaryStage.title = "Drawing Board"
        primaryStage.show()
    }

    fun getPixelData(): List<Double> {
        println()
        return pixelData.map { it.map { v -> if (v) 1.0 else 0.0 } }.flatten()
    }

    fun getPixelDataInt(): List<Int> {
        println()
        return pixelData.map { it.map { v -> if (v) 1 else 0 } }.flatten()
    }

    override fun stop() {
        setPaintLatch()
        //ShowData(getPixelData())
        runCheckWithImg(getPixelDataInt(), 1,standardNumbers)
    }

    fun setPaintLatch() {
        paintLatch.countDown()
    }
}