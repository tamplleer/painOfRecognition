package recognition.dataset

import recognition.Item
import recognition.itm

//еще ниче не сделала((
val NoiseOutliers: List<Item> = listOf(
    listOf(
        110,110, 115, 115, 115, 115,110,110,
        1, 115,110,2,2,110, 14,110,
        1, 1,2,2,3,2, 1,2,
        1,1,1,1,1, 1,1,1,
        110,33,110,56, 115,110,110,3,
        110,110,110, 17,110,110,9,110,
        2,110, 115,110,110,4,27,43,
        110, 4, 115, 115, 4, 115, 1,110,
    ) itm 2,
    listOf(
        110,110, 115, 115, 115, 115,110,110,
        110, 1,110,150,110,240, 115,110,
        7, 115,110,110,33,64,110,110,
        2, 115,2,110,1,2,110,110,
        110, 115, 115, 115, 2, 115,1,110,
        110, 148,110,11,1,150, 115,1,
        3, 1,1,2,1,12, 115,1,
        2,1, 1, 3, 1, 1,1,1,
    ) itm 6,
    listOf(
        110,110, 115, 115, 115, 115,31,110,
        21, 115,110,110,110,110, 115,110,
        255, 115,11,110,110,110,110,110,
        110, 115,66,110,110,110,55,110,
        0, 115, 1, 115, 115, 115,110,14,
        110, 115,110,110,110,110, 115,110,
        110, 115,110,110,55,110, 251,110,
        110,110, 115, 115, 99, 115,110,110,
    ) itm 6,
    listOf(
        115, 115, 115, 115, 115, 32, 115,110,
        110,110,110,110,110,2, 115,110,
        110,110,66,110,110,110, 115,110,
        110,14,110,110,115, 115,110,12,
        87,110,110,110,110, 115,110,110,
        110,110,11,110, 115,110,77,110,
        110,110,8,110, 115,110,10,110,
        10,110,110,110, 115,110,56,110,
    ) itm 7,
    listOf(
        180,110, 115, 115, 115, 115,110,110,
        110, 115,110,110,110,110, 115,110,
        110, 115,110,88,110,110, 12,110,
        110, 115,110,110,110,2, 115,110,
        110,110, 115, 115, 0, 115,110,110,
        110,110, 22,110,110, 115,110,110,
        110, 69,110,110,110,110, 115,110,
        110,110, 115, 115, 115, 115,110,110,
    ) itm 8,
    )