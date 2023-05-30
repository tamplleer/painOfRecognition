import javafx.application.Application
import recognition.DrawingBoard
import recognition.dataset.*
import recognition.dataset.NoiseNumbersShadows
import recognition.runCheckDefault
import recognition.searchOutliers


fun main() {
/*
    val isPaint = false
    if (isPaint) {
        Application.launch(DrawingBoard::class.java)
    } else {
        runCheckDefault(
            1000,
            true,
            true,
            1,
            0.25,
            0.3,
            0.2,
            0.1,
            standardNumbersShadows3,
            standardNumbersShadows3+NoiseNumbersShadows
        )
    }
*/

    searchOutliers(  standardNumbersShadows3,
        NoiseOutliers,1)
}


//standardNumbers + noiseNumbers
//standardNumberShadows , 2 , 3