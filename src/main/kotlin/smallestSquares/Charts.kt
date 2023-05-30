package smallestSquares

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.stage.Stage


class Charts : Application() {

    override fun start(primaryStage: Stage) {
        // Создание осей X и Y
        val xAxis = NumberAxis()
        val yAxis = NumberAxis()

        // Создание графика
        val lineChart = LineChart(xAxis, yAxis)

       lineChart.data.add(Series())

        val after = Series<Number, Number>()

        after.name = Calculation.polynomName
       Calculation.xValueNew.toList().forEachIndexed{ index, value ->
           after.data.add(XYChart.Data(value, Calculation.yValueNew[index]))
       }

        val before = Series<Number, Number>()
        before.name = "y = ${Calculation.funName}"
       Calculation.xValue.forEachIndexed{ index, value ->
           before.data.add(XYChart.Data(value, Calculation.yValue[index]))
       }
        lineChart.data.add(after)
        lineChart.data.add(before)


        // отображение графика
        val scene = Scene(lineChart, 800.0, 600.0)
        primaryStage.scene = scene
        primaryStage.show()
    }
}