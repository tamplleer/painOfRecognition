import javafx.application.Application
import recognition.DrawingBoard
import recognition.Item
import recognition.dataset.*
import recognition.itm
import recognition.runCheckDefault

fun main() {
    val isPaint = false
    if (isPaint) {
        Application.launch(DrawingBoard::class.java)
    } else {
        runCheckDefault(1000, true,true, 1, 0.25, 0.3, 0.2, 0.1, standardNumbersShadowsULTRA,standardNumbersShadowsULTRA)
    }
}

//standardNumbers + noiseNumbers
//standardNumberShadows , 2 , 3