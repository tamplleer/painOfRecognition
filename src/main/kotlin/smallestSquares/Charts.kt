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

       lineChart.data.add(Series<Number, Number>())

        val dataSeries = Series<Number, Number>()
        dataSeries.name = GenerateData.polenomName
       GenerateData.xValueNew.forEachIndexed{ index, value ->
           dataSeries.data.add(XYChart.Data(value, GenerateData.yValueNew[index]))
       }
       lineChart.data.add(dataSeries)


        val dataSeries1 = Series<Number, Number>()
        dataSeries1.name = "y = ${GenerateData.funName}"
       GenerateData.xValue.forEachIndexed{ index, value ->
           dataSeries1.data.add(XYChart.Data(value, GenerateData.yValue[index]))
       }

       lineChart.data.add(dataSeries1)
       dataSeries1.node.lookup(".chart-series-line").style= "-fx-stroke: transparent;"
       dataSeries1.node.lookupAll(".chart-line-symbol").forEach { symbol ->
           symbol.style = "-fx-fill: green;  -fx-background-radius: 15px;-fx-background-color: blue;"}

/*
       for (data in dataSeries1.data) {
           val node = Circle(15.0) // создание круговой точки
           node.style = "-fx-fill: green;  -fx-background-radius: 15px;-fx-background-color: blue;" // установка красного цвета точки
           data.node = node // установка точки для данных
       }*/

        // Создание сцены и отображение графика
        val scene = Scene(lineChart, 800.0, 600.0)
        primaryStage.scene = scene
        primaryStage.show()
    }
}