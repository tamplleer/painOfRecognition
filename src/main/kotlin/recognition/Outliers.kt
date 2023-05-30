package recognition

fun searchOutliers(
    etalonDataset: List<Item>,
    noiseShadows: List<Item>,
    k: Int,
) {
    val dataset = etalonDataset + noiseShadows
        //   println("Выбросы 10-14")
    println("Распознанные KNN:")
    for (item in dataset) {
        val knn = KNearestNeighbours(k, etalonDataset+(noiseShadows-item))
        if (knn.search(item.data, ::manhattan) != item.number) {
            print("${dataset.indexOf(item)} ")
        }
    }
    println("\nРаспознанные ODIN:")
    val inputs = IntArray(dataset.size)
    for (item in dataset) {
        val knn = KNearestNeighbours(5, etalonDataset+(noiseShadows-item))
        val x = knn.neighborhood(item.data, ::manhattan)
        knn.neighborhood(item.data, ::manhattan).forEach {
            inputs[dataset.indexOf(it)]++
        }
    }
    val t = 2
    inputs.forEachIndexed { index, count ->
        if (count < t) print("$index ")
    }
}