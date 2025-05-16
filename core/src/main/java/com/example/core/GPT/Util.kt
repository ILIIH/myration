package com.example.core.GPT

val maxLength = 150

fun padToLength(list: List<Int>, padValue: Int): LongArray {
    return (list.map { it.toLong() } + List(maxLength - list.size) { padValue.toLong() }).toLongArray()
}

fun padAndFlattenBBoxes(bboxList: List<List<Int>>, maxLength: Int): LongArray {
    val result = LongArray(maxLength * 4)
    for (i in 0 until maxLength) {
        val box = if (i < bboxList.size) bboxList[i] else listOf(0, 0, 0, 0)
        result[i * 4 + 0] = box[0].toLong()
        result[i * 4 + 1] = box[1].toLong()
        result[i * 4 + 2] = box[2].toLong()
        result[i * 4 + 3] = box[3].toLong()
    }
    return result
}
