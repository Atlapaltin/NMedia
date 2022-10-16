package ru.netology.nmedia

object Calculations {
    fun postStatistics(count: Int): String {
        return when (count) {
            in 0..999 -> count.toString()
            in 1_000..9_999 -> if (count.toString()[1] == '0') "${count.toString()[0]} K" else "${count.toString()[0]}.${count.toString()[1]} K"
            in 10_000..999_999 -> "${count.toString().dropLast(3)} K"
            else -> if (count.toString()[1] == '0') "${count.toString()[0]} M" else "${count.toString()[0]}.${count.toString()[1]} M"
        }
    }
}