import javafx.application.Application
import recognition.DrawingBoard
import recognition.Item
import recognition.dataset.*
import recognition.itm
import recognition.runCheckDefault
import smallestSquares.Charts
import smallestSquares.GenerateData

fun main() {
    /*   val isPaint = false
       if (isPaint) {
           Application.launch(DrawingBoard::class.java)
       } else {
           runCheckDefault(1000, true,true, 1, 0.25, 0.3, 0.2, 0.1, standardNumbersShadowsULTRA,standardNumbersShadowsULTRA)
       }*/
    GenerateData.create("sin",4,0.5,0.1,0.3,10)
    Application.launch(Charts::class.java)
}

//standardNumbers + noiseNumbers
//standardNumberShadows , 2 , 3